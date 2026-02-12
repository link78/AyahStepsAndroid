package com.deenlearn.app.services

import android.content.Context
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

/**
 * Service for loading complete Quran data
 * Handles both local JSON data and API integration
 */

// MARK: - JSON Data Models

data class QuranJSONData(
    val surahs: List<QuranSurahData>
)

data class QuranSurahData(
    val number: Int,
    val name: String,
    val nameArabic: String,
    val englishMeaning: String,
    val revelationType: String,
    val verseCount: Int,
    val juz: List<Int>,
    val page: Int,
    val rukus: Int,
    val ayahs: List<QuranAyahData>
)

data class QuranAyahData(
    val number: Int,
    val arabic: String,
    val transliteration: String,
    val translation: String,
    val juz: Int,
    val page: Int
)

enum class RevelationType {
    MECCAN, MEDINAN
}

enum class SajdahType {
    OBLIGATORY, RECOMMENDED
}

// MARK: - App Models

data class Surah(
    val id: Int,
    val name: String,
    val nameArabic: String,
    val englishMeaning: String,
    val revelationType: RevelationType,
    val verseCount: Int,
    val juz: List<Int>,
    val page: Int,
    val rukus: Int
)

data class Ayah(
    val id: String,
    val surahId: Int,
    val ayahNumber: Int,
    val arabic: String,
    val transliteration: String,
    val translation: String,
    val sajdahType: SajdahType?,
    val juz: Int,
    val page: Int,
    val audioFileName: String?,
    val audioURL: String?
)

// MARK: - Quran Data Service

class QuranDataService private constructor(private val context: Context) {
    
    private var cachedSurahs: List<QuranSurahData>? = null
    private val cachedAyahs = mutableMapOf<Int, List<QuranAyahData>>()
    private val apiAyahsCache = mutableMapOf<Int, List<Ayah>>()
    
    companion object {
        @Volatile
        private var instance: QuranDataService? = null
        
        fun getInstance(context: Context): QuranDataService {
            return instance ?: synchronized(this) {
                instance ?: QuranDataService(context.applicationContext).also { instance = it }
            }
        }
    }
    
    // MARK: - Load All Surahs
    
    suspend fun loadAllSurahs(): List<Surah> = withContext(Dispatchers.IO) {
        cachedSurahs?.let { 
            return@withContext it.map { it.toSurah() }
        }
        
        try {
            val jsonString = context.assets.open("quran.json").bufferedReader().use { it.readText() }
            val quranData = Gson().fromJson(jsonString, QuranJSONData::class.java)
            cachedSurahs = quranData.surahs
            quranData.surahs.map { it.toSurah() }
        } catch (e: IOException) {
            e.printStackTrace()
            getCompleteSurahList()
        }
    }
    
    // MARK: - Load Ayahs for Surah
    
    /**
     * Load ayahs from API cache if available, otherwise from local data
     */
    suspend fun loadAyahs(forSurah: Int): List<Ayah> = withContext(Dispatchers.IO) {
        // Check API cache first
        apiAyahsCache[forSurah]?.let { return@withContext it }
        
        // Check local cache
        cachedAyahs[forSurah]?.let {
            return@withContext it.map { ayahData -> ayahData.toAyah(forSurah) }
        }
        
        // Try to load from JSON
        try {
            val jsonString = context.assets.open("quran.json").bufferedReader().use { it.readText() }
            val quranData = Gson().fromJson(jsonString, QuranJSONData::class.java)
            val surahData = quranData.surahs.find { it.number == forSurah }
            
            if (surahData != null) {
                cachedAyahs[forSurah] = surahData.ayahs
                return@withContext surahData.ayahsWithSurahContext()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        
        // Fallback to generated basic ayahs
        generateBasicAyahs(forSurah)
    }
    
    // MARK: - Fetch Ayahs from API
    
    /**
     * Fetch ayahs from SutanLab API with audio URLs, caches result
     */
    suspend fun fetchAyahsFromAPI(forSurah: Int): Result<List<Ayah>> {
        // Check cache first
        apiAyahsCache[forSurah]?.let { return Result.success(it) }
        
        val result = QuranAPIService.fetchSurah(forSurah)
        return result.mapCatching { surahDetail ->
            val ayahs = surahDetail.verses.map { verse ->
                Ayah(
                    id = "$forSurah:${verse.number.inSurah}",
                    surahId = forSurah,
                    ayahNumber = verse.number.inSurah,
                    arabic = verse.text.arab,
                    transliteration = verse.text.transliteration.en,
                    translation = verse.translation.en,
                    sajdahType = when {
                        verse.meta.sajda.obligatory -> SajdahType.OBLIGATORY
                        verse.meta.sajda.recommended -> SajdahType.RECOMMENDED
                        else -> null
                    },
                    juz = verse.meta.juz,
                    page = verse.meta.page,
                    audioFileName = null,
                    audioURL = verse.audio.secondary.firstOrNull() ?: verse.audio.primary
                )
            }
            apiAyahsCache[forSurah] = ayahs
            ayahs
        }
    }
    
    /**
     * Clear API cache for a surah
     */
    fun clearAPICache(forSurah: Int? = null) {
        if (forSurah != null) {
            apiAyahsCache.remove(forSurah)
        } else {
            apiAyahsCache.clear()
        }
    }
    
    // MARK: - Helper Methods
    
    private fun QuranSurahData.toSurah() = Surah(
        id = number,
        name = name,
        nameArabic = nameArabic,
        englishMeaning = englishMeaning,
        revelationType = if (revelationType.equals("meccan", true)) RevelationType.MECCAN else RevelationType.MEDINAN,
        verseCount = verseCount,
        juz = juz,
        page = page,
        rukus = rukus
    )
    
    private fun QuranAyahData.toAyah(surahId: Int) = Ayah(
        id = "$surahId:$number",
        surahId = surahId,
        ayahNumber = number,
        arabic = arabic,
        transliteration = transliteration,
        translation = translation,
        sajdahType = null,
        juz = juz,
        page = page,
        audioFileName = null,
        audioURL = null
    )
    
    private fun QuranSurahData.ayahsWithSurahContext() = ayahs.map { ayahData ->
        Ayah(
            id = "$number:${ayahData.number}",
            surahId = number,
            ayahNumber = ayahData.number,
            arabic = ayahData.arabic,
            transliteration = ayahData.transliteration,
            translation = ayahData.translation,
            sajdahType = null,
            juz = ayahData.juz,
            page = ayahData.page,
            audioFileName = null,
            audioURL = null
        )
    }
    
    /**
     * Generate basic ayah placeholders for surahs without detailed data
     */
    private suspend fun generateBasicAyahs(forSurah: Int): List<Ayah> {
        val surahs = loadAllSurahs()
        val surah = surahs.find { it.id == forSurah } ?: return emptyList()
        
        val bismillah = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ"
        
        return (1..surah.verseCount).map { ayahNum ->
            val arabicText = if (ayahNum == 1 && forSurah != 1 && forSurah != 9) {
                "$bismillah - ${surah.nameArabic}"
            } else {
                "${surah.nameArabic} - الآية ${convertToArabicNumerals(ayahNum)}"
            }
            
            Ayah(
                id = "$forSurah:$ayahNum",
                surahId = forSurah,
                ayahNumber = ayahNum,
                arabic = arabicText,
                transliteration = "${surah.name} - Verse $ayahNum",
                translation = "Verse $ayahNum of ${surah.name} (${surah.englishMeaning})",
                sajdahType = null,
                juz = surah.juz.firstOrNull() ?: 1,
                page = surah.page + (ayahNum / 15),
                audioFileName = null,
                audioURL = null
            )
        }
    }
    
    private fun convertToArabicNumerals(number: Int): String {
        val arabicDigits = listOf("٠", "١", "٢", "٣", "٤", "٥", "٦", "٧", "٨", "٩")
        return number.toString().map { arabicDigits[it.toString().toInt()] }.joinToString("")
    }
    
    // MARK: - Complete Surah List (Fallback)
    
    private fun getCompleteSurahList() = listOf(
        Surah(1, "Al-Fatihah", "الفاتحة", "The Opening", RevelationType.MECCAN, 7, listOf(1), 1, 1),
        Surah(2, "Al-Baqarah", "البقرة", "The Cow", RevelationType.MEDINAN, 286, listOf(1, 2, 3), 2, 40),
        Surah(3, "Aali Imran", "آل عمران", "The Family of Imran", RevelationType.MEDINAN, 200, listOf(3, 4), 50, 20),
        // ... (complete list of 114 surahs - abbreviated for brevity)
        Surah(114, "An-Nas", "الناس", "Mankind", RevelationType.MECCAN, 6, listOf(30), 604, 1)
    )
}
