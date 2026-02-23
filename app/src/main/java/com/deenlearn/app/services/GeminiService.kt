package com.deenlearn.app.services

import com.deenlearn.app.BuildConfig
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Google Gemini AI Service
 * 
 * Provides integration with Google's Gemini AI API for natural language processing.
 * 
 * Features:
 * - Text generation and completion
 * - Multi-turn conversations
 * - Safety settings
 * - Free tier available
 * 
 * API Documentation: https://ai.google.dev/
 */
class GeminiService private constructor() {
    
    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing
    
    private val _apiError = MutableStateFlow<String?>(null)
    val apiError: StateFlow<String?> = _apiError
    
    // Cache for responses
    private val responseCache = mutableMapOf<String, String>()
    
    companion object {
        @Volatile
        private var instance: GeminiService? = null
        
        fun getInstance(): GeminiService {
            return instance ?: synchronized(this) {
                instance ?: GeminiService().also { instance = it }
            }
        }
        
        private const val BASE_URL = "https://generativelanguage.googleapis.com/"
        private const val CACHE_EXPIRY_MS = 1000 * 60 * 60 // 1 hour
    }
    
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    private val api = retrofit.create(GeminiApi::class.java)
    
    /**
     * Generate content using Gemini Pro model
     * 
     * @param prompt The text prompt
     * @param conversationHistory Previous conversation messages
     * @return Result containing generated text or error
     */
    suspend fun generateContent(
        prompt: String,
        conversationHistory: List<GeminiContent> = emptyList()
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            _isProcessing.value = true
            _apiError.value = null
            
            // Check cache
            val cacheKey = "$prompt-${conversationHistory.hashCode()}"
            responseCache[cacheKey]?.let {
                _isProcessing.value = false
                return@withContext Result.success(it)
            }
            
            val apiKey = BuildConfig.GEMINI_API_KEY
            if (apiKey.isBlank()) {
                throw IllegalStateException("Gemini API key not configured. Add gemini.api.key to local.properties")
            }
            
            // Build request with conversation history
            val contents = conversationHistory + GeminiContent(
                parts = listOf(GeminiPart(text = prompt)),
                role = "user"
            )
            
            val request = GeminiRequest(
                contents = contents,
                safetySettings = getDefaultSafetySettings(),
                generationConfig = GeminiGenerationConfig(
                    temperature = 0.7,
                    topK = 40,
                    topP = 0.95,
                    maxOutputTokens = 2048
                )
            )
            
            val response = api.generateContent(
                apiKey = apiKey,
                request = request
            )
            
            val generatedText = response.candidates.firstOrNull()
                ?.content
                ?.parts
                ?.firstOrNull()
                ?.text
                ?: throw Exception("No content generated")
            
            // Cache the response
            responseCache[cacheKey] = generatedText
            
            _isProcessing.value = false
            Result.success(generatedText)
            
        } catch (e: Exception) {
            _isProcessing.value = false
            _apiError.value = e.message
            Result.failure(e)
        }
    }
    
    /**
     * Simple chat interface for single prompts
     */
    suspend fun simpleChat(prompt: String): Result<String> {
        return generateContent(prompt)
    }
    
    /**
     * Multi-turn conversation support
     */
    suspend fun continueConversation(
        userMessage: String,
        history: List<GeminiContent>
    ): Result<Pair<String, List<GeminiContent>>> {
        return generateContent(userMessage, history).map { response ->
            val newHistory = history + listOf(
                GeminiContent(
                    parts = listOf(GeminiPart(text = userMessage)),
                    role = "user"
                ),
                GeminiContent(
                    parts = listOf(GeminiPart(text = response)),
                    role = "model"
                )
            )
            response to newHistory
        }
    }
    
    /**
     * Clear response cache
     */
    fun clearCache() {
        responseCache.clear()
    }
    
    /**
     * Default safety settings for Islamic education content
     */
    private fun getDefaultSafetySettings(): List<GeminiSafetySetting> {
        return listOf(
            GeminiSafetySetting(
                category = "HARM_CATEGORY_HARASSMENT",
                threshold = "BLOCK_MEDIUM_AND_ABOVE"
            ),
            GeminiSafetySetting(
                category = "HARM_CATEGORY_HATE_SPEECH",
                threshold = "BLOCK_MEDIUM_AND_ABOVE"
            ),
            GeminiSafetySetting(
                category = "HARM_CATEGORY_SEXUALLY_EXPLICIT",
                threshold = "BLOCK_MEDIUM_AND_ABOVE"
            ),
            GeminiSafetySetting(
                category = "HARM_CATEGORY_DANGEROUS_CONTENT",
                threshold = "BLOCK_MEDIUM_AND_ABOVE"
            )
        )
    }
}

// Retrofit API Interface
interface GeminiApi {
    @POST("v1beta/models/gemini-pro:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}

// Data Models
data class GeminiRequest(
    val contents: List<GeminiContent>,
    val safetySettings: List<GeminiSafetySetting>? = null,
    val generationConfig: GeminiGenerationConfig? = null
)

data class GeminiContent(
    val parts: List<GeminiPart>,
    val role: String? = null // "user" or "model"
)

data class GeminiPart(
    val text: String
)

data class GeminiResponse(
    val candidates: List<GeminiCandidate>,
    val promptFeedback: GeminiPromptFeedback? = null
)

data class GeminiCandidate(
    val content: GeminiContent,
    val finishReason: String? = null,
    val safetyRatings: List<GeminiSafetyRating>? = null
)

data class GeminiSafetySetting(
    val category: String,
    val threshold: String
)

data class GeminiSafetyRating(
    val category: String,
    val probability: String
)

data class GeminiPromptFeedback(
    val safetyRatings: List<GeminiSafetyRating>? = null
)

data class GeminiGenerationConfig(
    val temperature: Double? = null,
    val topK: Int? = null,
    val topP: Double? = null,
    val maxOutputTokens: Int? = null,
    val stopSequences: List<String>? = null
)
