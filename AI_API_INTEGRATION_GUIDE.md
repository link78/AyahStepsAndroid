# AI API Integration Guide

## Complete Guide for Integrating Real AI APIs into DeenLearn App

This comprehensive guide provides step-by-step instructions for integrating real AI APIs (OpenAI GPT-4, Google Gemini, Anthropic Claude) to enhance the DeenLearn app with advanced natural language processing capabilities.

---

## Table of Contents

1. [AI Provider Comparison](#ai-provider-comparison)
2. [OpenAI GPT-4 Integration](#openai-gpt-4-integration)
3. [Google Gemini Integration](#google-gemini-integration)
4. [Anthropic Claude Integration](#anthropic-claude-integration)
5. [Service Integration Examples](#service-integration-examples)
6. [Hybrid Approach (Recommended)](#hybrid-approach-recommended)
7. [Security Best Practices](#security-best-practices)
8. [Cost Optimization](#cost-optimization)
9. [Advanced Features](#advanced-features)
10. [Testing & Monitoring](#testing-monitoring)

---

## AI Provider Comparison

### OpenAI GPT-4
**Website**: https://platform.openai.com/

**Pros**:
- Most popular and widely adopted
- Excellent at following complex instructions
- Strong reasoning and analytical capabilities
- Multiple model sizes (GPT-4, GPT-4-32K, GPT-4-Turbo-128K)
- Function calling for structured data
- Extensive documentation and community

**Cons**:
- Most expensive option
- No free tier
- Rate limits on API calls
- Requires API key management

**Pricing**:
- GPT-4 (8K context): $0.03/1K input tokens, $0.06/1K output tokens
- GPT-4-32K: $0.06/1K input tokens, $0.12/1K output tokens
- GPT-4-Turbo: $0.01/1K input tokens, $0.03/1K output tokens

**Best For**:
- Complex Q&A and tutoring
- Detailed explanations
- Multi-turn conversations
- Function calling for structured tasks

---

### Google Gemini
**Website**: https://ai.google.dev/

**Pros**:
- Multimodal (text, images, audio, video)
- Free tier available (Gemini Pro)
- Integrated with Google ecosystem
- Native Android SDK
- Fast inference speed
- Safety filters built-in

**Cons**:
- Newer, less community support
- Limited function calling
- Regional availability
- Some features still in preview

**Pricing**:
- Gemini Pro: FREE (up to 60 requests/minute)
- Gemini Pro Vision: FREE
- Gemini Ultra: $0.50/1M tokens (coming soon)

**Best For**:
- Cost-effective solution
- Image analysis (Quran pages, Arabic text)
- Fast responses
- Kids mode (simple Q&A)

---

### Anthropic Claude
**Website**: https://www.anthropic.com/

**Pros**:
- Extended context (200K tokens)
- Constitutional AI for safety
- Excellent at following detailed guidelines
- Strong at analysis and summarization
- Tool use (function calling)
- Helpful, harmless, honest design

**Cons**:
- Most expensive for high usage
- Limited free tier
- Newer to market
- Fewer integrations

**Pricing**:
- Claude 3 Haiku: $0.25/1M input, $1.25/1M output
- Claude 3 Sonnet: $3/1M input, $15/1M output
- Claude 3 Opus: $15/1M input, $75/1M output

**Best For**:
- Long document analysis (full Quran tafsir)
- Detailed learning recommendations
- Extended conversations with context
- High-quality text generation

---

## OpenAI GPT-4 Integration

### 1. Setup

#### Dependencies (build.gradle)
```gradle
dependencies {
    // Retrofit for API calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}
```

#### API Key Configuration
Add to `local.properties` (DO NOT commit this file):
```properties
openai.api.key=sk-your-api-key-here
```

Add to `build.gradle`:
```gradle
android {
    defaultConfig {
        // Load from local.properties
        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())
        
        buildConfigField("String", "OPENAI_API_KEY", 
            "\"${properties.getProperty("openai.api.key", "")}\"")
    }
}
```

### 2. Implementation

#### OpenAIService.kt
```kotlin
package com.deenlearn.app.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.google.gson.annotations.SerializedName

// Data Models
data class ChatCompletionRequest(
    val model: String = "gpt-4-turbo-preview",
    val messages: List<ChatMessage>,
    val temperature: Double = 0.7,
    val max_tokens: Int? = null,
    val stream: Boolean = false
)

data class ChatMessage(
    val role: String, // "system", "user", "assistant"
    val content: String
)

data class ChatCompletionResponse(
    val id: String,
    val choices: List<Choice>,
    val usage: Usage
)

data class Choice(
    val message: ChatMessage,
    @SerializedName("finish_reason") val finishReason: String
)

data class Usage(
    @SerializedName("prompt_tokens") val promptTokens: Int,
    @SerializedName("completion_tokens") val completionTokens: Int,
    @SerializedName("total_tokens") val totalTokens: Int
)

// Retrofit Interface
interface OpenAIApi {
    @POST("v1/chat/completions")
    suspend fun chatCompletion(
        @Header("Authorization") authorization: String,
        @Body request: ChatCompletionRequest
    ): ChatCompletionResponse
}

// Service Class
class OpenAIService {
    private val apiKey = BuildConfig.OPENAI_API_KEY
    private val baseUrl = "https://api.openai.com/"
    
    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    
    private val api: OpenAIApi by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenAIApi::class.java)
    }
    
    suspend fun chatCompletion(
        messages: List<ChatMessage>,
        model: String = "gpt-4-turbo-preview",
        temperature: Double = 0.7,
        maxTokens: Int? = null
    ): Result<ChatCompletionResponse> {
        return try {
            _isProcessing.value = true
            _error.value = null
            
            val request = ChatCompletionRequest(
                model = model,
                messages = messages,
                temperature = temperature,
                max_tokens = maxTokens
            )
            
            val response = api.chatCompletion(
                authorization = "Bearer $apiKey",
                request = request
            )
            
            Result.success(response)
        } catch (e: Exception) {
            _error.value = e.message
            Result.failure(e)
        } finally {
            _isProcessing.value = false
        }
    }
    
    suspend fun simpleChat(
        prompt: String,
        systemPrompt: String? = null
    ): Result<String> {
        val messages = mutableListOf<ChatMessage>()
        
        if (systemPrompt != null) {
            messages.add(ChatMessage("system", systemPrompt))
        }
        messages.add(ChatMessage("user", prompt))
        
        val result = chatCompletion(messages)
        return result.map { response ->
            response.choices.firstOrNull()?.message?.content ?: ""
        }
    }
    
    companion object {
        @Volatile
        private var instance: OpenAIService? = null
        
        fun getInstance(): OpenAIService {
            return instance ?: synchronized(this) {
                instance ?: OpenAIService().also { instance = it }
            }
        }
    }
}
```

### 3. Usage Example

#### Integrate with AITutorService
```kotlin
class AITutorService {
    private val openAI = OpenAIService.getInstance()
    private val conversationHistory = mutableListOf<ChatMessage>()
    
    suspend fun getResponse(
        question: String,
        topic: Topic,
        isKidsMode: Boolean
    ): Result<String> {
        val systemPrompt = buildSystemPrompt(topic, isKidsMode)
        
        // Add system prompt if first message
        if (conversationHistory.isEmpty()) {
            conversationHistory.add(ChatMessage("system", systemPrompt))
        }
        
        // Add user question
        conversationHistory.add(ChatMessage("user", question))
        
        // Get AI response
        val result = openAI.chatCompletion(conversationHistory)
        
        return result.map { response ->
            val assistantMessage = response.choices.first().message.content
            conversationHistory.add(ChatMessage("assistant", assistantMessage))
            assistantMessage
        }
    }
    
    private fun buildSystemPrompt(topic: Topic, isKidsMode: Boolean): String {
        return if (isKidsMode) {
            """
            You are a friendly Islamic education AI assistant for children.
            You explain Islamic concepts in simple, fun ways using emojis.
            Keep answers short (2-3 sentences) and age-appropriate.
            Focus on $topic.
            Always be encouraging and positive.
            Use examples children can relate to.
            """.trimIndent()
        } else {
            """
            You are an Islamic education AI assistant for adults.
            Provide accurate information about Islam based on Quran and authentic Hadith.
            Include references when possible (Bukhari, Muslim, etc.).
            Be respectful and scholarly in tone.
            Focus on $topic.
            Provide detailed explanations when helpful.
            """.trimIndent()
        }
    }
    
    fun clearHistory() {
        conversationHistory.clear()
    }
}
```

---

## Google Gemini Integration

### 1. Setup

#### Dependencies (build.gradle)
```gradle
dependencies {
    // Google AI SDK
    implementation("com.google.ai.client.generativeai:generativeai:0.1.2")
    
    // Or use REST API with Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}
```

#### API Key Configuration
Add to `local.properties`:
```properties
gemini.api.key=your-gemini-api-key-here
```

### 2. Implementation (REST API Approach)

#### GeminiService.kt
```kotlin
package com.deenlearn.app.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// Data Models
data class GeminiRequest(
    val contents: List<GeminiContent>,
    val generationConfig: GenerationConfig? = null,
    val safetySettings: List<SafetySetting>? = null
)

data class GeminiContent(
    val parts: List<GeminiPart>,
    val role: String? = null
)

data class GeminiPart(
    val text: String
)

data class GenerationConfig(
    val temperature: Double = 0.7,
    val topK: Int = 40,
    val topP: Double = 0.95,
    val maxOutputTokens: Int = 1024
)

data class SafetySetting(
    val category: String,
    val threshold: String
)

data class GeminiResponse(
    val candidates: List<GeminiCandidate>,
    val usageMetadata: UsageMetadata?
)

data class GeminiCandidate(
    val content: GeminiContent,
    val finishReason: String?,
    val safetyRatings: List<SafetyRating>?
)

data class SafetyRating(
    val category: String,
    val probability: String
)

data class UsageMetadata(
    val promptTokenCount: Int,
    val candidatesTokenCount: Int,
    val totalTokenCount: Int
)

// Retrofit Interface
interface GeminiApi {
    @POST("v1beta/models/gemini-pro:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiRequest
    ): GeminiResponse
}

// Service Class
class GeminiService {
    private val apiKey = BuildConfig.GEMINI_API_KEY
    private val baseUrl = "https://generativelanguage.googleapis.com/"
    
    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    
    private val api: GeminiApi by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GeminiApi::class.java)
    }
    
    // Default safety settings
    private val defaultSafetySettings = listOf(
        SafetySetting("HARM_CATEGORY_HARASSMENT", "BLOCK_MEDIUM_AND_ABOVE"),
        SafetySetting("HARM_CATEGORY_HATE_SPEECH", "BLOCK_MEDIUM_AND_ABOVE"),
        SafetySetting("HARM_CATEGORY_SEXUALLY_EXPLICIT", "BLOCK_MEDIUM_AND_ABOVE"),
        SafetySetting("HARM_CATEGORY_DANGEROUS_CONTENT", "BLOCK_MEDIUM_AND_ABOVE")
    )
    
    suspend fun generateContent(
        prompt: String,
        conversationHistory: List<GeminiContent> = emptyList()
    ): Result<String> {
        return try {
            _isProcessing.value = true
            _error.value = null
            
            val contents = conversationHistory + GeminiContent(
                parts = listOf(GeminiPart(prompt)),
                role = "user"
            )
            
            val request = GeminiRequest(
                contents = contents,
                generationConfig = GenerationConfig(),
                safetySettings = defaultSafetySettings
            )
            
            val response = api.generateContent(apiKey, request)
            val text = response.candidates.firstOrNull()?.content?.parts?.firstOrNull()?.text
                ?: throw Exception("No response generated")
            
            Result.success(text)
        } catch (e: Exception) {
            _error.value = e.message
            Result.failure(e)
        } finally {
            _isProcessing.value = false
        }
    }
    
    companion object {
        @Volatile
        private var instance: GeminiService? = null
        
        fun getInstance(): GeminiService {
            return instance ?: synchronized(this) {
                instance ?: GeminiService().also { instance = it }
            }
        }
    }
}
```

### 3. Usage Example

```kotlin
class AIQuestionGeneratorService {
    private val gemini = GeminiService.getInstance()
    
    suspend fun generateQuiz(
        category: QuestionCategory,
        count: Int,
        difficulty: QuestionDifficulty,
        isKidsMode: Boolean
    ): Result<Quiz> {
        val prompt = buildQuizPrompt(category, count, difficulty, isKidsMode)
        
        val result = gemini.generateContent(prompt)
        
        return result.map { response ->
            parseQuizFromResponse(response, category, difficulty)
        }
    }
    
    private fun buildQuizPrompt(
        category: QuestionCategory,
        count: Int,
        difficulty: QuestionDifficulty,
        isKidsMode: Boolean
    ): String {
        val audience = if (isKidsMode) "children (ages 6-12)" else "adults"
        
        return """
        Generate $count multiple choice questions about $category in Islam.
        
        Difficulty level: $difficulty
        Target audience: $audience
        
        For each question, provide:
        1. Question text
        2. Four answer options (A, B, C, D)
        3. Correct answer letter
        4. Brief explanation
        
        Format the output as JSON with this structure:
        {
          "questions": [
            {
              "question": "...",
              "options": ["...", "...", "...", "..."],
              "correctAnswer": "A",
              "explanation": "..."
            }
          ]
        }
        
        Make questions engaging and educational.
        ${if (isKidsMode) "Use simple language and include emojis." else "Include references to Quran or Hadith where appropriate."}
        """.trimIndent()
    }
    
    private fun parseQuizFromResponse(
        response: String,
        category: QuestionCategory,
        difficulty: QuestionDifficulty
    ): Quiz {
        // Parse JSON response and convert to Quiz object
        // Implementation depends on your Quiz data structure
        // Use Gson or kotlinx.serialization
        val gson = Gson()
        val jsonResponse = gson.fromJson(response, JsonObject::class.java)
        
        // Extract and convert questions
        // ...
        
        return Quiz(/* parsed questions */)
    }
}
```

---

## Anthropic Claude Integration

### 1. Setup

#### Dependencies (build.gradle)
```gradle
dependencies {
    // Retrofit for Anthropic API
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
}
```

#### API Key Configuration
Add to `local.properties`:
```properties
claude.api.key=your-anthropic-api-key-here
```

### 2. Implementation

#### ClaudeService.kt
```kotlin
package com.deenlearn.app.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.google.gson.annotations.SerializedName

// Data Models
data class ClaudeRequest(
    val model: String = "claude-3-sonnet-20240229",
    val messages: List<ClaudeMessage>,
    @SerializedName("max_tokens") val maxTokens: Int = 1024,
    val temperature: Double = 0.7,
    val system: String? = null
)

data class ClaudeMessage(
    val role: String, // "user" or "assistant"
    val content: String
)

data class ClaudeResponse(
    val id: String,
    val content: List<ClaudeContent>,
    val model: String,
    val role: String,
    @SerializedName("stop_reason") val stopReason: String?,
    val usage: ClaudeUsage
)

data class ClaudeContent(
    val type: String, // "text"
    val text: String
)

data class ClaudeUsage(
    @SerializedName("input_tokens") val inputTokens: Int,
    @SerializedName("output_tokens") val outputTokens: Int
)

// Retrofit Interface
interface ClaudeApi {
    @POST("v1/messages")
    suspend fun createMessage(
        @Header("x-api-key") apiKey: String,
        @Header("anthropic-version") version: String = "2023-06-01",
        @Body request: ClaudeRequest
    ): ClaudeResponse
}

// Service Class
class ClaudeService {
    private val apiKey = BuildConfig.CLAUDE_API_KEY
    private val baseUrl = "https://api.anthropic.com/"
    
    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error
    
    private val api: ClaudeApi by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ClaudeApi::class.java)
    }
    
    suspend fun createMessage(
        messages: List<ClaudeMessage>,
        systemPrompt: String? = null,
        model: String = "claude-3-sonnet-20240229",
        maxTokens: Int = 1024,
        temperature: Double = 0.7
    ): Result<ClaudeResponse> {
        return try {
            _isProcessing.value = true
            _error.value = null
            
            val request = ClaudeRequest(
                model = model,
                messages = messages,
                maxTokens = maxTokens,
                temperature = temperature,
                system = systemPrompt
            )
            
            val response = api.createMessage(apiKey, request = request)
            
            Result.success(response)
        } catch (e: Exception) {
            _error.value = e.message
            Result.failure(e)
        } finally {
            _isProcessing.value = false
        }
    }
    
    suspend fun simpleChat(
        prompt: String,
        systemPrompt: String? = null
    ): Result<String> {
        val messages = listOf(ClaudeMessage("user", prompt))
        
        val result = createMessage(messages, systemPrompt)
        return result.map { response ->
            response.content.firstOrNull()?.text ?: ""
        }
    }
    
    companion object {
        @Volatile
        private var instance: ClaudeService? = null
        
        fun getInstance(): ClaudeService {
            return instance ?: synchronized(this) {
                instance ?: ClaudeService().also { instance = it }
            }
        }
    }
}
```

### 3. Usage Example

```kotlin
class AILearningAssistantService {
    private val claude = ClaudeService.getInstance()
    
    suspend fun generateRecommendations(
        userProgress: UserProgress,
        isKidsMode: Boolean
    ): Result<List<LearningRecommendation>> {
        val prompt = buildRecommendationPrompt(userProgress, isKidsMode)
        val systemPrompt = buildSystemPrompt(isKidsMode)
        
        val result = claude.simpleChat(prompt, systemPrompt)
        
        return result.map { response ->
            parseRecommendationsFromResponse(response)
        }
    }
    
    private fun buildSystemPrompt(isKidsMode: Boolean): String {
        return if (isKidsMode) {
            """
            You are a friendly Islamic learning assistant for children.
            Create fun, engaging learning recommendations.
            Keep activities short (5-15 minutes).
            Use emojis and encouraging language.
            Focus on making learning enjoyable.
            """.trimIndent()
        } else {
            """
            You are an Islamic education learning assistant for adults.
            Provide structured, comprehensive learning recommendations.
            Include time estimates and difficulty levels.
            Reference authentic Islamic sources.
            Create balanced study plans across topics.
            """.trimIndent()
        }
    }
    
    private fun buildRecommendationPrompt(
        userProgress: UserProgress,
        isKidsMode: Boolean
    ): String {
        return """
        Analyze this user's learning progress and create personalized recommendations:
        
        Current Progress:
        - Pillars: ${userProgress.pillarsProgress}%
        - Salah: ${userProgress.salahProgress}%
        - Quran: ${userProgress.quranProgress}%
        - Arabic: ${userProgress.arabicProgress}%
        
        Learning streak: ${userProgress.streak} days
        
        Generate 5 specific learning recommendations that:
        1. Focus on weak areas (below 50% progress)
        2. Build on existing knowledge
        3. Include estimated time
        4. Are appropriate for ${if (isKidsMode) "children" else "adults"}
        
        Format each recommendation as:
        - Title: [short title]
        - Description: [what to do]
        - Category: [Pillars/Salah/Quran/Arabic]
        - Time: [minutes needed]
        - Priority: [High/Medium/Low]
        """.trimIndent()
    }
}
```

---

## Service Integration Examples

### AITutorService with Real AI

```kotlin
class AITutorService {
    private val openAI = OpenAIService.getInstance()
    private val gemini = GeminiService.getInstance()
    private val conversationHistory = mutableListOf<ChatMessage>()
    
    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing
    
    suspend fun getResponse(
        question: String,
        topic: Topic,
        isKidsMode: Boolean
    ): Result<ChatMessage> {
        _isProcessing.value = true
        
        val systemPrompt = buildSystemPrompt(topic, isKidsMode)
        
        // Try OpenAI first for best quality
        val result = openAI.simpleChat(question, systemPrompt)
            .recoverCatching {
                // Fallback to Gemini if OpenAI fails
                gemini.generateContent(question).getOrThrow()
            }
            .map { responseText ->
                val message = ChatMessage(
                    id = UUID.randomUUID().toString(),
                    role = MessageRole.ASSISTANT,
                    content = responseText,
                    timestamp = System.currentTimeMillis(),
                    topic = topic
                )
                conversationHistory.add(message)
                message
            }
        
        _isProcessing.value = false
        return result
    }
    
    fun clearHistory() {
        conversationHistory.clear()
    }
}
```

---

## Hybrid Approach (Recommended)

### Multi-Tier Fallback Strategy

```kotlin
class HybridAIService {
    private val openAI = OpenAIService.getInstance()
    private val gemini = GeminiService.getInstance()
    private val claude = ClaudeService.getInstance()
    private val simulatedAI = AITutorService() // Your existing simulated AI
    
    suspend fun getResponse(
        prompt: String,
        preferredProvider: AIProvider = AIProvider.AUTO
    ): Result<String> {
        return when (preferredProvider) {
            AIProvider.OPENAI -> tryOpenAI(prompt)
            AIProvider.GEMINI -> tryGemini(prompt)
            AIProvider.CLAUDE -> tryClaude(prompt)
            AIProvider.AUTO -> tryWithFallback(prompt)
        }
    }
    
    private suspend fun tryWithFallback(prompt: String): Result<String> {
        // Try OpenAI first (best quality)
        val openAIResult = tryOpenAI(prompt)
        if (openAIResult.isSuccess) return openAIResult
        
        // Fallback to Gemini (free tier)
        val geminiResult = tryGemini(prompt)
        if (geminiResult.isSuccess) return geminiResult
        
        // Fallback to Claude
        val claudeResult = tryClaude(prompt)
        if (claudeResult.isSuccess) return claudeResult
        
        // Final fallback to simulated AI (offline)
        return Result.success(simulatedAI.getResponse(prompt))
    }
    
    private suspend fun tryOpenAI(prompt: String): Result<String> {
        return try {
            openAI.simpleChat(prompt)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private suspend fun tryGemini(prompt: String): Result<String> {
        return try {
            gemini.generateContent(prompt)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private suspend fun tryClaude(prompt: String): Result<String> {
        return try {
            claude.simpleChat(prompt)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

enum class AIProvider {
    OPENAI,
    GEMINI,
    CLAUDE,
    AUTO
}
```

---

## Security Best Practices

### 1. API Key Storage

**DO NOT commit API keys to git!**

#### Using local.properties
```properties
# local.properties (add to .gitignore)
openai.api.key=sk-...
gemini.api.key=...
claude.api.key=...
```

#### Using Encrypted SharedPreferences
```kotlin
class SecureAPIKeyStorage(private val context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()
    
    private val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "ai_keys",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    
    fun saveAPIKey(provider: String, key: String) {
        sharedPreferences.edit()
            .putString("${provider}_api_key", key)
            .apply()
    }
    
    fun getAPIKey(provider: String): String? {
        return sharedPreferences.getString("${provider}_api_key", null)
    }
}
```

### 2. ProGuard Rules

Add to `proguard-rules.pro`:
```proguard
# Keep AI service classes
-keep class com.deenlearn.app.services.OpenAIService { *; }
-keep class com.deenlearn.app.services.GeminiService { *; }
-keep class com.deenlearn.app.services.ClaudeService { *; }

# Keep data models for Retrofit
-keep class com.deenlearn.app.models.** { *; }

# Keep Retrofit and Gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.** { *; }
-keep class retrofit2.** { *; }
```

### 3. Network Security Config

Create `res/xml/network_security_config.xml`:
```xml
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <domain-config cleartextTrafficPermitted="false">
        <domain includeSubdomains="true">api.openai.com</domain>
        <domain includeSubdomains="true">generativelanguage.googleapis.com</domain>
        <domain includeSubdomains="true">api.anthropic.com</domain>
        
        <!-- Certificate pinning (optional but recommended) -->
        <pin-set expiration="2025-12-31">
            <pin digest="SHA-256">base64-encoded-pin</pin>
        </pin-set>
    </domain-config>
</network-security-config>
```

---

## Cost Optimization

### 1. Caching Strategy

```kotlin
class AIResponseCache(private val context: Context) {
    private val cache = mutableMapOf<String, CachedResponse>()
    
    data class CachedResponse(
        val response: String,
        val timestamp: Long,
        val expiryMinutes: Int = 60
    )
    
    fun get(key: String): String? {
        val cached = cache[key] ?: return null
        
        // Check if expired
        val age = System.currentTimeMillis() - cached.timestamp
        if (age > cached.expiryMinutes * 60 * 1000) {
            cache.remove(key)
            return null
        }
        
        return cached.response
    }
    
    fun put(key: String, response: String, expiryMinutes: Int = 60) {
        cache[key] = CachedResponse(
            response = response,
            timestamp = System.currentTimeMillis(),
            expiryMinutes = expiryMinutes
        )
    }
    
    fun clear() {
        cache.clear()
    }
}

// Usage in service
class AITutorService {
    private val cache = AIResponseCache(context)
    
    suspend fun getResponse(question: String): Result<String> {
        // Generate cache key
        val cacheKey = "question_${question.hashCode()}"
        
        // Check cache first
        cache.get(cacheKey)?.let { cachedResponse ->
            return Result.success(cachedResponse)
        }
        
        // If not cached, call AI API
        val result = openAI.simpleChat(question)
        
        // Cache successful response
        result.onSuccess { response ->
            cache.put(cacheKey, response, expiryMinutes = 120)
        }
        
        return result
    }
}
```

### 2. Token Management

```kotlin
class TokenManager {
    fun truncateConversation(
        messages: List<ChatMessage>,
        maxTokens: Int = 4000
    ): List<ChatMessage> {
        var totalTokens = 0
        val result = mutableListOf<ChatMessage>()
        
        // Keep system message if present
        val systemMessage = messages.firstOrNull { it.role == "system" }
        if (systemMessage != null) {
            result.add(systemMessage)
            totalTokens += estimateTokens(systemMessage.content)
        }
        
        // Add messages from end (most recent)
        for (message in messages.reversed()) {
            if (message.role == "system") continue
            
            val messageTokens = estimateTokens(message.content)
            if (totalTokens + messageTokens > maxTokens) break
            
            result.add(0, message)
            totalTokens += messageTokens
        }
        
        return result
    }
    
    private fun estimateTokens(text: String): Int {
        // Rough estimate: ~4 characters per token
        return (text.length / 4).coerceAtLeast(1)
    }
}
```

### 3. Cost Monitoring

```kotlin
class AIUsageTracker {
    data class UsageMetrics(
        val provider: String,
        val model: String,
        val inputTokens: Int,
        val outputTokens: Int,
        val cost: Double,
        val timestamp: Long
    )
    
    private val usageHistory = mutableListOf<UsageMetrics>()
    
    fun trackUsage(
        provider: String,
        model: String,
        inputTokens: Int,
        outputTokens: Int
    ) {
        val cost = calculateCost(provider, model, inputTokens, outputTokens)
        
        usageHistory.add(
            UsageMetrics(
                provider = provider,
                model = model,
                inputTokens = inputTokens,
                outputTokens = outputTokens,
                cost = cost,
                timestamp = System.currentTimeMillis()
            )
        )
    }
    
    fun getTotalCost(days: Int = 30): Double {
        val cutoff = System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000)
        return usageHistory
            .filter { it.timestamp > cutoff }
            .sumOf { it.cost }
    }
    
    private fun calculateCost(
        provider: String,
        model: String,
        inputTokens: Int,
        outputTokens: Int
    ): Double {
        // Pricing per 1K tokens
        return when (provider) {
            "openai" -> when (model) {
                "gpt-4" -> (inputTokens * 0.03 + outputTokens * 0.06) / 1000
                "gpt-4-turbo" -> (inputTokens * 0.01 + outputTokens * 0.03) / 1000
                else -> 0.0
            }
            "gemini" -> 0.0 // Free tier
            "claude" -> when (model) {
                "claude-3-opus" -> (inputTokens * 15 + outputTokens * 75) / 1000000
                "claude-3-sonnet" -> (inputTokens * 3 + outputTokens * 15) / 1000000
                "claude-3-haiku" -> (inputTokens * 0.25 + outputTokens * 1.25) / 1000000
                else -> 0.0
            }
            else -> 0.0
        }
    }
}
```

---

## Advanced Features

### 1. Streaming Responses

```kotlin
// OpenAI Streaming
suspend fun streamResponse(prompt: String): Flow<String> = flow {
    val messages = listOf(ChatMessage("user", prompt))
    
    // Enable streaming in request
    val request = ChatCompletionRequest(
        model = "gpt-4-turbo",
        messages = messages,
        stream = true
    )
    
    // Process Server-Sent Events
    val response = api.streamChatCompletion("Bearer $apiKey", request)
    
    response.body()?.use { responseBody ->
        responseBody.charStream().useLines { lines ->
            lines.forEach { line ->
                if (line.startsWith("data: ")) {
                    val json = line.substring(6)
                    if (json == "[DONE]") return@forEach
                    
                    val chunk = gson.fromJson(json, StreamChunk::class.java)
                    val content = chunk.choices.firstOrNull()?.delta?.content
                    if (content != null) {
                        emit(content)
                    }
                }
            }
        }
    }
}

// Usage in UI
LaunchedEffect(prompt) {
    viewModel.streamResponse(prompt).collect { chunk ->
        // Update UI with each chunk
        responseText.value += chunk
    }
}
```

### 2. Function Calling (OpenAI)

```kotlin
data class Function(
    val name: String,
    val description: String,
    val parameters: Map<String, Any>
)

val functions = listOf(
    Function(
        name = "get_prayer_times",
        description = "Get prayer times for a specific location and date",
        parameters = mapOf(
            "type" to "object",
            "properties" to mapOf(
                "city" to mapOf(
                    "type" to "string",
                    "description" to "The city name"
                ),
                "date" to mapOf(
                    "type" to "string",
                    "description" to "Date in YYYY-MM-DD format"
                )
            ),
            "required" to listOf("city", "date")
        )
    )
)

// Include functions in request
val request = ChatCompletionRequest(
    model = "gpt-4-turbo",
    messages = messages,
    functions = functions
)

// Handle function call in response
response.choices.firstOrNull()?.let { choice ->
    choice.message.functionCall?.let { functionCall ->
        when (functionCall.name) {
            "get_prayer_times" -> {
                val args = gson.fromJson(functionCall.arguments, PrayerTimeArgs::class.java)
                val prayerTimes = getPrayerTimes(args.city, args.date)
                // Return function result to AI
            }
        }
    }
}
```

### 3. Image Analysis (Gemini Pro Vision)

```kotlin
suspend fun analyzeQuranPage(imageUri: Uri): Result<String> {
    // Convert image to base64
    val bitmap = context.contentResolver.openInputStream(imageUri)?.use {
        BitmapFactory.decodeStream(it)
    } ?: return Result.failure(Exception("Failed to load image"))
    
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream)
    val imageBytes = byteArrayOutputStream.toByteArray()
    val base64Image = Base64.encodeToString(imageBytes, Base64.NO_WRAP)
    
    // Send to Gemini Pro Vision
    val request = GeminiRequest(
        contents = listOf(
            GeminiContent(
                parts = listOf(
                    GeminiPart(text = "Analyze this Quran page and extract the Arabic text. Identify the Surah and Ayah numbers."),
                    GeminiPart(inlineData = InlineData(
                        mimeType = "image/jpeg",
                        data = base64Image
                    ))
                )
            )
        )
    )
    
    return gemini.generateContent(request)
}
```

---

## Testing & Monitoring

### 1. Unit Tests

```kotlin
@Test
fun testOpenAIIntegration() = runTest {
    val service = OpenAIService()
    
    val result = service.simpleChat(
        prompt = "What is Shahada?",
        systemPrompt = "You are an Islamic education assistant."
    )
    
    assertTrue(result.isSuccess)
    val response = result.getOrNull()
    assertNotNull(response)
    assertTrue(response!!.contains("Shahada") || response.contains("declaration"))
}

@Test
fun testAIFallback() = runTest {
    val hybridService = HybridAIService()
    
    // Should fallback gracefully if primary AI fails
    val result = hybridService.getResponse("Test question")
    
    assertTrue(result.isSuccess)
    assertNotNull(result.getOrNull())
}
```

### 2. Performance Monitoring

```kotlin
class AIPerformanceMonitor {
    data class PerformanceMetrics(
        val provider: String,
        val operation: String,
        val duration: Long,
        val success: Boolean,
        val errorMessage: String?
    )
    
    suspend fun <T> measurePerformance(
        provider: String,
        operation: String,
        block: suspend () -> Result<T>
    ): Result<T> {
        val startTime = System.currentTimeMillis()
        val result = block()
        val duration = System.currentTimeMillis() - startTime
        
        val metrics = PerformanceMetrics(
            provider = provider,
            operation = operation,
            duration = duration,
            success = result.isSuccess,
            errorMessage = result.exceptionOrNull()?.message
        )
        
        logMetrics(metrics)
        
        return result
    }
    
    private fun logMetrics(metrics: PerformanceMetrics) {
        // Log to Firebase Analytics, Crashlytics, etc.
        Firebase.analytics.logEvent("ai_api_call") {
            param("provider", metrics.provider)
            param("operation", metrics.operation)
            param("duration_ms", metrics.duration)
            param("success", if (metrics.success) 1 else 0)
        }
    }
}
```

---

## Implementation Checklist

### Phase 1: Setup (Day 1)
- [ ] Choose AI provider (recommend starting with Gemini - free)
- [ ] Get API keys from providers
- [ ] Add dependencies to build.gradle
- [ ] Configure API keys in local.properties
- [ ] Add BuildConfig fields
- [ ] Set up ProGuard rules

### Phase 2: Basic Integration (Day 2)
- [ ] Create OpenAIService or GeminiService
- [ ] Implement basic chat completion
- [ ] Add error handling
- [ ] Test with simple prompts
- [ ] Verify API calls work

### Phase 3: Service Integration (Day 3)
- [ ] Update AITutorService with real AI
- [ ] Test Q&A functionality
- [ ] Add conversation history
- [ ] Implement kids vs adults modes
- [ ] Verify responses are appropriate

### Phase 4: Advanced Features (Day 4-5)
- [ ] Add caching system
- [ ] Implement fallback logic
- [ ] Add cost tracking
- [ ] Implement streaming (optional)
- [ ] Add function calling (optional)

### Phase 5: Testing & Optimization (Day 6-7)
- [ ] Unit tests for AI services
- [ ] Integration tests
- [ ] Performance monitoring
- [ ] Cost analysis
- [ ] User testing
- [ ] Islamic content verification

---

## Best Practices Summary

### Do's ✅
- Store API keys securely
- Implement caching to reduce costs
- Add fallback mechanisms
- Monitor usage and costs
- Test thoroughly with Islamic content
- Use system prompts for context
- Implement rate limiting
- Add loading states
- Handle errors gracefully
- Verify AI responses for accuracy

### Don'ts ❌
- Don't commit API keys to git
- Don't trust AI responses blindly
- Don't ignore token limits
- Don't skip error handling
- Don't forget Islamic content verification
- Don't ignore user privacy
- Don't use AI for Islamic rulings (fatwa)
- Don't skip cost monitoring
- Don't forget offline mode
- Don't rush deployment

---

## Conclusion

This guide provides a complete roadmap for integrating real AI APIs into the DeenLearn app. Start with one provider (recommend Gemini for cost-effectiveness), test thoroughly, and gradually expand to multiple providers with a hybrid fallback approach.

Remember:
- **Security first**: Protect API keys
- **Cost awareness**: Monitor and optimize usage
- **Islamic integrity**: Verify all AI-generated content
- **User experience**: Fast, reliable, appropriate responses
- **Fallback strategy**: Always have offline mode

Good luck with your AI integration! 🤖📚🕌
