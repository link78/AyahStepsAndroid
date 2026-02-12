package com.deenlearn.app.services

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Quran API service using SutanLab/Quran API (api.quran.gading.dev)
 * Provides Quran text with Arabic, transliteration, English translation,
 * and audio recitation by Syekh Mishary Rashid Al-Afasy
 * No API key required - free and open
 */

// MARK: - SutanLab API Response Models
data class SutanLabSurahListResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: List<SutanLabSurahSummary>
)

data class SutanLabSurahSummary(
    val number: Int,
    val sequence: Int,
    val numberOfVerses: Int,
    val name: SutanLabName,
    val revelation: SutanLabRevelation,
    val tafsir: SutanLabTafsirSurah
)

data class SutanLabSurahResponse(
    val code: Int,
    val status: String,
    val message: String,
    val data: SutanLabSurahDetail
)

data class SutanLabSurahDetail(
    val number: Int,
    val sequence: Int,
    val numberOfVerses: Int,
    val name: SutanLabName,
    val revelation: SutanLabRevelation,
    val tafsir: SutanLabTafsirSurah,
    val preBismillah: SutanLabPreBismillah?,
    val verses: List<SutanLabVerse>
)

data class SutanLabName(
    val short: String,
    val long: String,
    val transliteration: SutanLabTransliteration,
    val translation: SutanLabTranslation
)

data class SutanLabTransliteration(
    val en: String,
    val id: String?
)

data class SutanLabTranslation(
    val en: String,
    val id: String?
)

data class SutanLabRevelation(
    val arab: String,
    val en: String,
    val id: String?
)

data class SutanLabTafsirSurah(
    val id: String?
)

data class SutanLabPreBismillah(
    val text: SutanLabVerseText?,
    val translation: SutanLabTranslation?,
    val audio: SutanLabAudio?
)

data class SutanLabVerse(
    val number: SutanLabVerseNumber,
    val meta: SutanLabVerseMeta,
    val text: SutanLabVerseText,
    val translation: SutanLabTranslation,
    val audio: SutanLabAudio,
    val tafsir: SutanLabTafsirVerse?
)

data class SutanLabVerseNumber(
    val inQuran: Int,
    val inSurah: Int
)

data class SutanLabVerseMeta(
    val juz: Int,
    val page: Int,
    val manzil: Int,
    val ruku: Int,
    val hizbQuarter: Int,
    val sajda: SutanLabSajda
)

data class SutanLabSajda(
    val recommended: Boolean,
    val obligatory: Boolean
)

data class SutanLabVerseText(
    val arab: String,
    val transliteration: SutanLabTransliterationText
)

data class SutanLabTransliterationText(
    val en: String
)

data class SutanLabAudio(
    val primary: String,
    val secondary: List<String>
)

data class SutanLabTafsirVerse(
    val id: SutanLabTafsirContent?
)

data class SutanLabTafsirContent(
    val short: String?,
    val long: String?
)

// MARK: - Retrofit API Interface
interface QuranAPI {
    @GET("surah")
    suspend fun getSurahList(): SutanLabSurahListResponse
    
    @GET("surah/{number}")
    suspend fun getSurah(@Path("number") number: Int): SutanLabSurahResponse
}

// MARK: - Quran API Service
object QuranAPIService {
    private const val BASE_URL = "https://api.quran.gading.dev/"
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _apiError = MutableStateFlow<String?>(null)
    val apiError: StateFlow<String?> = _apiError.asStateFlow()
    
    private val _isUsingAPI = MutableStateFlow(false)
    val isUsingAPI: StateFlow<Boolean> = _isUsingAPI.asStateFlow()
    
    // Cache for API responses
    private val surahCache = mutableMapOf<Int, SutanLabSurahDetail>()
    private var surahListCache: List<SutanLabSurahSummary>? = null
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    private val api = retrofit.create(QuranAPI::class.java)
    
    // MARK: - Fetch All Surahs List
    
    /**
     * Fetch list of all 114 surahs from API
     */
    suspend fun fetchSurahList(): Result<List<SutanLabSurahSummary>> {
        surahListCache?.let { return Result.success(it) }
        
        return try {
            _isLoading.value = true
            val response = api.getSurahList()
            _isLoading.value = false
            
            if (response.code == 200) {
                surahListCache = response.data
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
    
    // MARK: - Fetch Surah Detail
    
    /**
     * Fetch a specific surah with all verses, translations, and audio URLs
     */
    suspend fun fetchSurah(number: Int): Result<SutanLabSurahDetail> {
        surahCache[number]?.let { return Result.success(it) }
        
        return try {
            _isLoading.value = true
            val response = api.getSurah(number)
            _isLoading.value = false
            
            if (response.code == 200) {
                surahCache[number] = response.data
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
    
    // MARK: - Clear Cache
    
    /**
     * Clear all cached data
     */
    fun clearCache() {
        surahCache.clear()
        surahListCache = null
    }
}
