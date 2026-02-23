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
 * AlQuran Cloud API Service
 * Base URL: https://api.alquran.cloud/v1/
 * 
 * Provides comprehensive Quran data with:
 * - Multiple translations (100+ editions in 40+ languages)
 * - Multiple reciters for audio
 * - Word-by-word analysis
 * - Tafsir (commentary) support
 * - Kids-friendly simple translations
 * - Scholarly editions for adults
 * 
 * No API key required - free and open source
 */

// MARK: - AlQuran Cloud Response Models

/**
 * Standard API response wrapper
 */
data class AlQuranCloudResponse<T>(
    val code: Int,
    val status: String,
    val data: T
)

/**
 * Surah list response
 */
data class AlQuranCloudSurahList(
    val surahs: List<AlQuranCloudSurahReference>,
    val edition: AlQuranCloudEditionInfo
)

/**
 * Surah reference (in list)
 */
data class AlQuranCloudSurahReference(
    val number: Int,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val numberOfAyahs: Int,
    val revelationType: String
)

/**
 * Full surah with all ayahs
 */
data class AlQuranCloudSurah(
    val number: Int,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val revelationType: String,
    val numberOfAyahs: Int,
    val ayahs: List<AlQuranCloudAyah>,
    val edition: AlQuranCloudEditionInfo
)

/**
 * Multiple editions of same surah
 */
data class AlQuranCloudMultiEditionSurah(
    val surahs: List<AlQuranCloudSurah>
)

/**
 * Single ayah
 */
data class AlQuranCloudAyah(
    val number: Int,           // Ayah number in entire Quran
    val numberInSurah: Int,    // Ayah number in surah
    val text: String,          // Arabic text or translation depending on edition
    val surah: AlQuranCloudSurahInfo,
    val juz: Int,
    val manzil: Int,
    val page: Int,
    val ruku: Int,
    val hizbQuarter: Int,
    val sajda: AlQuranCloudSajda? = null,
    val audio: String? = null,  // Audio URL if available
    val audioSecondary: List<String>? = null
)

/**
 * Surah info in ayah response
 */
data class AlQuranCloudSurahInfo(
    val number: Int,
    val name: String,
    val englishName: String,
    val englishNameTranslation: String,
    val revelationType: String,
    val numberOfAyahs: Int
)

/**
 * Sajda (prostration) information
 */
data class AlQuranCloudSajda(
    val id: Int,
    val recommended: Boolean,
    val obligatory: Boolean
)

/**
 * Edition information
 */
data class AlQuranCloudEditionInfo(
    val identifier: String,
    val language: String,
    val name: String,
    val englishName: String,
    val format: String,         // "text" or "audio"
    val type: String,          // "translation", "transliteration", "tafsir", "quran"
    val direction: String? = null  // "rtl" or "ltr"
)

/**
 * Available editions list
 */
data class AlQuranCloudEditionsList(
    val editions: List<AlQuranCloudEditionInfo>
)

/**
 * Juz information
 */
data class AlQuranCloudJuz(
    val number: Int,
    val ayahs: List<AlQuranCloudAyah>,
    val surahs: Map<Int, AlQuranCloudSurahInfo>,
    val edition: AlQuranCloudEditionInfo
)

// MARK: - Retrofit API Interface

interface AlQuranCloudAPI {
    /**
     * Get list of all 114 surahs (Arabic Quran)
     */
    @GET("surah")
    suspend fun getSurahList(): AlQuranCloudResponse<AlQuranCloudSurahList>
    
    /**
     * Get specific surah with Arabic text
     */
    @GET("surah/{number}")
    suspend fun getSurah(@Path("number") number: Int): AlQuranCloudResponse<AlQuranCloudSurah>
    
    /**
     * Get specific surah with specific edition (translation, transliteration, etc.)
     */
    @GET("surah/{number}/{edition}")
    suspend fun getSurahWithEdition(
        @Path("number") number: Int,
        @Path("edition") edition: String
    ): AlQuranCloudResponse<AlQuranCloudSurah>
    
    /**
     * Get surah with multiple editions at once
     * Example: editions = "en.asad,en.transliteration,ar.alafasy"
     */
    @GET("surah/{number}/editions/{editions}")
    suspend fun getSurahMultipleEditions(
        @Path("number") number: Int,
        @Path("editions") editions: String
    ): AlQuranCloudResponse<AlQuranCloudMultiEditionSurah>
    
    /**
     * Get specific ayah with edition
     */
    @GET("ayah/{reference}/{edition}")
    suspend fun getAyah(
        @Path("reference") reference: Int,
        @Path("edition") edition: String
    ): AlQuranCloudResponse<AlQuranCloudAyah>
    
    /**
     * Get specific juz (30 parts of Quran)
     */
    @GET("juz/{number}/{edition}")
    suspend fun getJuz(
        @Path("number") number: Int,
        @Path("edition") edition: String
    ): AlQuranCloudResponse<AlQuranCloudJuz>
    
    /**
     * Get list of available editions
     */
    @GET("edition")
    suspend fun getEditions(): AlQuranCloudResponse<List<AlQuranCloudEditionInfo>>
    
    /**
     * Get editions filtered by format
     */
    @GET("edition/format/{format}")
    suspend fun getEditionsByFormat(
        @Path("format") format: String  // "text" or "audio"
    ): AlQuranCloudResponse<List<AlQuranCloudEditionInfo>>
    
    /**
     * Get editions filtered by language
     */
    @GET("edition/language/{language}")
    suspend fun getEditionsByLanguage(
        @Path("language") language: String  // "en", "ar", "ur", etc.
    ): AlQuranCloudResponse<List<AlQuranCloudEditionInfo>>
    
    /**
     * Get editions filtered by type
     */
    @GET("edition/type/{type}")
    suspend fun getEditionsByType(
        @Path("type") type: String  // "translation", "transliteration", "tafsir", "quran"
    ): AlQuranCloudResponse<List<AlQuranCloudEditionInfo>>
}

// MARK: - AlQuran Cloud API Service

object AlQuranCloudAPIService {
    private const val BASE_URL = "https://api.alquran.cloud/v1/"
    
    // Recommended editions for different modes
    object Editions {
        // Kids Mode - Simple, clear translations
        const val KIDS_TRANSLATION = "en.asad"              // Simple English by Muhammad Asad
        const val KIDS_TRANSLITERATION = "en.transliteration"  // Pronunciation guide
        const val KIDS_ARABIC = "quran-simple"              // Simplified Arabic text
        
        // Adults Mode - Scholarly translations
        const val ADULT_TRANSLATION = "en.sahih"            // Sahih International
        const val ADULT_TRANSLATION_ALT = "en.pickthall"    // Classical translation
        const val ADULT_ARABIC = "quran-unicode"            // Full Arabic with tajweed
        const val ADULT_TAFSIR = "en.maududi"               // English Tafsir
        
        // Audio
        const val AUDIO_ALAFASY = "ar.alafasy"              // Mishary Rashid Alafasy
        const val AUDIO_HUSARY = "ar.husary"                // Mahmoud Khalil Al-Husary
        const val AUDIO_MINSHAWI = "ar.minshawi"            // Mohamed Siddiq El-Minshawi
    }
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _apiError = MutableStateFlow<String?>(null)
    val apiError: StateFlow<String?> = _apiError.asStateFlow()
    
    private val _isUsingAPI = MutableStateFlow(false)
    val isUsingAPI: StateFlow<Boolean> = _isUsingAPI.asStateFlow()
    
    // Cache for API responses
    private val surahCache = mutableMapOf<String, AlQuranCloudSurah>()  // Key: "number-edition"
    private val multiEditionCache = mutableMapOf<String, AlQuranCloudMultiEditionSurah>()
    private var surahListCache: AlQuranCloudSurahList? = null
    private var editionsCache: List<AlQuranCloudEditionInfo>? = null
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    
    private val api = retrofit.create(AlQuranCloudAPI::class.java)
    
    // MARK: - Fetch Surah List
    
    /**
     * Fetch list of all 114 surahs
     */
    suspend fun fetchSurahList(): Result<AlQuranCloudSurahList> {
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
                _apiError.value = "API Error: ${response.status}"
                Result.failure(Exception(response.status))
            }
        } catch (e: Exception) {
            _isLoading.value = false
            _apiError.value = e.localizedMessage
            Result.failure(e)
        }
    }
    
    // MARK: - Fetch Surah with Edition
    
    /**
     * Fetch specific surah with chosen edition
     * For kids mode, use Editions.KIDS_TRANSLATION
     * For adult mode, use Editions.ADULT_TRANSLATION
     */
    suspend fun fetchSurahWithEdition(
        number: Int,
        edition: String = Editions.ADULT_TRANSLATION
    ): Result<AlQuranCloudSurah> {
        val cacheKey = "$number-$edition"
        surahCache[cacheKey]?.let { return Result.success(it) }
        
        return try {
            _isLoading.value = true
            val response = api.getSurahWithEdition(number, edition)
            _isLoading.value = false
            
            if (response.code == 200) {
                surahCache[cacheKey] = response.data
                _isUsingAPI.value = true
                _apiError.value = null
                Result.success(response.data)
            } else {
                _apiError.value = "API Error: ${response.status}"
                Result.failure(Exception(response.status))
            }
        } catch (e: Exception) {
            _isLoading.value = false
            _apiError.value = e.localizedMessage
            Result.failure(e)
        }
    }
    
    // MARK: - Fetch Surah for Kids Mode
    
    /**
     * Convenience method to fetch surah for kids with simple translation
     */
    suspend fun fetchSurahForKids(number: Int): Result<AlQuranCloudSurah> {
        return fetchSurahWithEdition(number, Editions.KIDS_TRANSLATION)
    }
    
    // MARK: - Fetch Surah for Adults Mode
    
    /**
     * Convenience method to fetch surah for adults with scholarly translation
     */
    suspend fun fetchSurahForAdults(number: Int): Result<AlQuranCloudSurah> {
        return fetchSurahWithEdition(number, Editions.ADULT_TRANSLATION)
    }
    
    // MARK: - Fetch Multiple Editions
    
    /**
     * Fetch surah with multiple editions at once
     * Example: For kids - Arabic + Translation + Transliteration
     */
    suspend fun fetchSurahMultipleEditions(
        number: Int,
        editions: List<String>
    ): Result<AlQuranCloudMultiEditionSurah> {
        val editionsStr = editions.joinToString(",")
        val cacheKey = "$number-$editionsStr"
        multiEditionCache[cacheKey]?.let { return Result.success(it) }
        
        return try {
            _isLoading.value = true
            val response = api.getSurahMultipleEditions(number, editionsStr)
            _isLoading.value = false
            
            if (response.code == 200) {
                multiEditionCache[cacheKey] = response.data
                _isUsingAPI.value = true
                _apiError.value = null
                Result.success(response.data)
            } else {
                _apiError.value = "API Error: ${response.status}"
                Result.failure(Exception(response.status))
            }
        } catch (e: Exception) {
            _isLoading.value = false
            _apiError.value = e.localizedMessage
            Result.failure(e)
        }
    }
    
    // MARK: - Fetch Kids Bundle
    
    /**
     * Fetch complete kids bundle: Translation + Transliteration
     */
    suspend fun fetchKidsBundle(number: Int): Result<AlQuranCloudMultiEditionSurah> {
        return fetchSurahMultipleEditions(
            number,
            listOf(Editions.KIDS_TRANSLATION, Editions.KIDS_TRANSLITERATION)
        )
    }
    
    // MARK: - Fetch Adults Bundle
    
    /**
     * Fetch complete adults bundle: Arabic + Translation
     */
    suspend fun fetchAdultsBundle(number: Int): Result<AlQuranCloudMultiEditionSurah> {
        return fetchSurahMultipleEditions(
            number,
            listOf(Editions.ADULT_ARABIC, Editions.ADULT_TRANSLATION)
        )
    }
    
    // MARK: - Fetch Ayah
    
    /**
     * Fetch specific ayah with edition
     * Reference is the ayah number in entire Quran (1-6236)
     */
    suspend fun fetchAyah(
        reference: Int,
        edition: String = Editions.ADULT_TRANSLATION
    ): Result<AlQuranCloudAyah> {
        return try {
            _isLoading.value = true
            val response = api.getAyah(reference, edition)
            _isLoading.value = false
            
            if (response.code == 200) {
                _isUsingAPI.value = true
                _apiError.value = null
                Result.success(response.data)
            } else {
                _apiError.value = "API Error: ${response.status}"
                Result.failure(Exception(response.status))
            }
        } catch (e: Exception) {
            _isLoading.value = false
            _apiError.value = e.localizedMessage
            Result.failure(e)
        }
    }
    
    // MARK: - Fetch Available Editions
    
    /**
     * Fetch list of all available editions
     */
    suspend fun fetchEditions(): Result<List<AlQuranCloudEditionInfo>> {
        editionsCache?.let { return Result.success(it) }
        
        return try {
            _isLoading.value = true
            val response = api.getEditions()
            _isLoading.value = false
            
            if (response.code == 200) {
                editionsCache = response.data
                _isUsingAPI.value = true
                _apiError.value = null
                Result.success(response.data)
            } else {
                _apiError.value = "API Error: ${response.status}"
                Result.failure(Exception(response.status))
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
        multiEditionCache.clear()
        surahListCache = null
        editionsCache = null
    }
    
    /**
     * Clear specific surah from cache
     */
    fun clearSurahCache(number: Int, edition: String) {
        surahCache.remove("$number-$edition")
    }
}
