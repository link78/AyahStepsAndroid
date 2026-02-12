package com.deenlearn.app.services

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Hadith API service using Islamic Network Hadith API (api.hadith.gading.dev)
 * Provides hadith collections from Bukhari, Muslim, Tirmidzi, Abu Daud, Nasai,
 * Ibnu Majah, Ahmad, Darimi, and Malik
 * No API key required - free and open
 */

// MARK: - Hadith API Response Models

data class HadithBooksResponse(
    val code: Int,
    val message: String,
    val data: List<HadithBook>
)

data class HadithBook(
    val name: String,
    val id: String,
    val available: Int
)

data class HadithDetailResponse(
    val code: Int,
    val message: String,
    val data: HadithData
)

data class HadithRangeResponse(
    val code: Int,
    val message: String,
    val data: HadithRangeData
)

data class HadithRangeData(
    val name: String,
    val id: String,
    val available: Int,
    val requested: Int,
    val hadiths: List<HadithContent>
)

data class HadithData(
    val name: String,
    val id: String,
    val available: Int,
    val requested: Int,
    val hadiths: List<HadithContent>
)

data class HadithContent(
    val number: Int,
    val arab: String,
    val id: String
)

// MARK: - Hadith Collections
enum class HadithCollection(val displayName: String, val apiId: String) {
    BUKHARI("Sahih al-Bukhari", "bukhari"),
    MUSLIM("Sahih Muslim", "muslim"),
    TIRMIDHI("Jami' at-Tirmidhi", "tirmidhi"),
    NASAI("Sunan an-Nasa'i", "nasai"),
    ABU_DAUD("Sunan Abu Dawud", "abu-daud"),
    IBNU_MAJAH("Sunan Ibn Majah", "ibnu-majah"),
    AHMAD("Musnad Ahmad", "ahmad"),
    DARIMI("Sunan ad-Darimi", "darimi"),
    MALIK("Muwatta Malik", "malik")
}

// MARK: - Retrofit API Interface
interface HadithAPI {
    @GET("books")
    suspend fun getBooks(): HadithBooksResponse
    
    @GET("books/{collection}/{number}")
    suspend fun getHadith(
        @Path("collection") collection: String,
        @Path("number") number: Int
    ): HadithDetailResponse
    
    @GET("books/{collection}")
    suspend fun getHadithRange(
        @Path("collection") collection: String,
        @Query("range") range: String
    ): HadithRangeResponse
}

// MARK: - Hadith API Service
object HadithAPIService {
    private const val BASE_URL = "https://api.hadith.gading.dev/"
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _apiError = MutableStateFlow<String?>(null)
    val apiError: StateFlow<String?> = _apiError.asStateFlow()
    
    private val _isUsingAPI = MutableStateFlow(false)
    val isUsingAPI: StateFlow<Boolean> = _isUsingAPI.asStateFlow()
    
    // Cache for API responses
    private val hadithCache = mutableMapOf<String, HadithContent>()
    private var booksCache: List<HadithBook>? = null
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    private val api = retrofit.create(HadithAPI::class.java)
    
    // MARK: - Fetch Available Books
    
    suspend fun fetchBooks(): Result<List<HadithBook>> {
        booksCache?.let { return Result.success(it) }
        
        return try {
            _isLoading.value = true
            val response = api.getBooks()
            _isLoading.value = false
            
            if (response.code == 200) {
                booksCache = response.data
                _isUsingAPI.value = true
                _apiError.value = null
                Result.success(response.data)
            } else {
                _apiError.value = "Server error"
                Result.failure(Exception("Server error"))
            }
        } catch (e: Exception) {
            _isLoading.value = false
            _apiError.value = e.localizedMessage
            Result.failure(e)
        }
    }
    
    // MARK: - Fetch Specific Hadith
    
    /**
     * Fetch a specific hadith by collection and number
     */
    suspend fun fetchHadith(collection: String, number: Int): Result<HadithContent> {
        val cacheKey = "$collection-$number"
        hadithCache[cacheKey]?.let { return Result.success(it) }
        
        return try {
            _isLoading.value = true
            val response = api.getHadith(collection, number)
            _isLoading.value = false
            
            if (response.code == 200 && response.data.hadiths.isNotEmpty()) {
                val hadith = response.data.hadiths.first()
                hadithCache[cacheKey] = hadith
                _isUsingAPI.value = true
                _apiError.value = null
                Result.success(hadith)
            } else {
                _apiError.value = "Hadith not found"
                Result.failure(Exception("Hadith not found"))
            }
        } catch (e: Exception) {
            _isLoading.value = false
            _apiError.value = e.localizedMessage
            Result.failure(e)
        }
    }
    
    // MARK: - Fetch Hadith Range
    
    /**
     * Fetch a range of hadiths from a collection
     */
    suspend fun fetchHadithRange(collection: String, from: Int, to: Int): Result<List<HadithContent>> {
        if (to - from > 300) {
            _apiError.value = "Max range is 300 hadiths"
            return Result.failure(Exception("Max range is 300 hadiths"))
        }
        
        return try {
            _isLoading.value = true
            val response = api.getHadithRange(collection, "$from-$to")
            _isLoading.value = false
            
            if (response.code == 200) {
                // Cache each hadith
                response.data.hadiths.forEach { hadith ->
                    hadithCache["$collection-${hadith.number}"] = hadith
                }
                _isUsingAPI.value = true
                _apiError.value = null
                Result.success(response.data.hadiths)
            } else {
                _apiError.value = "Server error"
                Result.failure(Exception("Server error"))
            }
        } catch (e: Exception) {
            _isLoading.value = false
            _apiError.value = e.localizedMessage
            Result.failure(e)
        }
    }
    
    /**
     * Clear all cached data
     */
    fun clearCache() {
        hadithCache.clear()
        booksCache = null
    }
}
