package com.deenlearn.app.models

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
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
) {
    val kidsEmoji: String
        get() = when (id) {
            1 -> "📖"
            112 -> "☝️"
            113 -> "🌅"
            114 -> "👥"
            108 -> "💧"
            110 -> "🏆"
            111 -> "🔥"
            109 -> "🚫"
            107 -> "🤲"
            105 -> "🐘"
            106 -> "❄️"
            103 -> "⏰"
            102 -> "💰"
            101 -> "⚖️"
            100 -> "🐎"
            else -> "📜"
        }
}

@Serializable
enum class RevelationType {
    MECCAN,
    MEDINAN;
    
    val displayName: String
        get() = when (this) {
            MECCAN -> "Meccan"
            MEDINAN -> "Medinan"
        }
}

@Serializable
data class Ayah(
    val id: String,
    val surahId: Int,
    val ayahNumber: Int,
    val arabic: String,
    val transliteration: String,
    val translation: String,
    val words: List<QuranWord>,
    val sajdahType: SajdahType?,
    val juz: Int,
    val page: Int,
    val audioFileName: String?,
    val audioURL: String? = null
)

@Serializable
enum class SajdahType {
    RECOMMENDED,
    OBLIGATORY;
    
    val displayName: String
        get() = when (this) {
            RECOMMENDED -> "Recommended"
            OBLIGATORY -> "Obligatory"
        }
}

@Serializable
data class QuranWord(
    val id: String,
    val arabic: String,
    val transliteration: String,
    val translation: String,
    val rootWord: String?,
    val rootMeaning: String?,
    val tajweedRules: List<TajweedRule>,
    val audioFileName: String?
)

@Serializable
data class TajweedRule(
    val id: String,
    val name: String,
    val nameArabic: String,
    val colorHex: String,
    val description: String,
    val example: String,
    val audioFileName: String?
)

@Serializable
data class MemorizationProgress(
    val id: String,
    val surahId: Int,
    val totalAyahs: Int,
    val ayahsMemorized: Set<Int>,
    @Serializable(with = DateSerializer::class)
    val lastPracticed: Date,
    val strength: Double,
    val totalReviews: Int
) {
    val percentComplete: Double
        get() = if (totalAyahs > 0) {
            ayahsMemorized.size.toDouble() / totalAyahs.toDouble()
        } else {
            0.0
        }
}

@Serializable
data class QuranBookmark(
    val id: String,
    val surahId: Int,
    val ayahNumber: Int,
    @Serializable(with = DateSerializer::class)
    val createdAt: Date,
    val note: String?,
    val colorHex: String
)

@Serializable
data class JuzAmmaAdventure(
    val id: Int,
    val surahName: String,
    val surahNameArabic: String,
    val emoji: String,
    val storyTheme: String,
    val difficulty: DifficultyLevel,
    val reward: String,
    val isUnlocked: Boolean,
    val isCompleted: Boolean,
    val starsEarned: Int
)

@Serializable
enum class DifficultyLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED;
    
    val displayName: String
        get() = when (this) {
            BEGINNER -> "Beginner"
            INTERMEDIATE -> "Intermediate"
            ADVANCED -> "Advanced"
        }
}

object QuranData {
    val juzAmmaSurahs = listOf(
        Surah(78, "An-Naba", "النبأ", "The Tidings", RevelationType.MECCAN, 40, listOf(30), 582, 2),
        Surah(79, "An-Nazi'at", "النازعات", "Those Who Pull Out", RevelationType.MECCAN, 46, listOf(30), 583, 2),
        Surah(80, "Abasa", "عبس", "He Frowned", RevelationType.MECCAN, 42, listOf(30), 585, 1),
        Surah(81, "At-Takwir", "التكوير", "The Overthrowing", RevelationType.MECCAN, 29, listOf(30), 586, 1),
        Surah(82, "Al-Infitar", "الإنفطار", "The Cleaving", RevelationType.MECCAN, 19, listOf(30), 587, 1),
        Surah(83, "Al-Mutaffifin", "المطففين", "The Defrauders", RevelationType.MECCAN, 36, listOf(30), 587, 1),
        Surah(84, "Al-Inshiqaq", "الإنشقاق", "The Splitting Open", RevelationType.MECCAN, 25, listOf(30), 589, 1),
        Surah(85, "Al-Buruj", "البروج", "The Mansions of the Stars", RevelationType.MECCAN, 22, listOf(30), 590, 1),
        Surah(86, "At-Tariq", "الطارق", "The Nightcomer", RevelationType.MECCAN, 17, listOf(30), 591, 1),
        Surah(87, "Al-A'la", "الأعلى", "The Most High", RevelationType.MECCAN, 19, listOf(30), 591, 1),
        Surah(88, "Al-Ghashiyah", "الغاشية", "The Overwhelming", RevelationType.MECCAN, 26, listOf(30), 592, 1),
        Surah(89, "Al-Fajr", "الفجر", "The Dawn", RevelationType.MECCAN, 30, listOf(30), 593, 1),
        Surah(90, "Al-Balad", "البلد", "The City", RevelationType.MECCAN, 20, listOf(30), 594, 1),
        Surah(91, "Ash-Shams", "الشمس", "The Sun", RevelationType.MECCAN, 15, listOf(30), 595, 1),
        Surah(92, "Al-Layl", "الليل", "The Night", RevelationType.MECCAN, 21, listOf(30), 595, 1),
        Surah(93, "Ad-Dhuha", "الضحى", "The Morning Hours", RevelationType.MECCAN, 11, listOf(30), 596, 1),
        Surah(94, "Ash-Sharh", "الشرح", "The Relief", RevelationType.MECCAN, 8, listOf(30), 596, 1),
        Surah(95, "At-Tin", "التين", "The Fig", RevelationType.MECCAN, 8, listOf(30), 597, 1),
        Surah(96, "Al-Alaq", "العلق", "The Clot", RevelationType.MECCAN, 19, listOf(30), 597, 1),
        Surah(97, "Al-Qadr", "القدر", "The Power", RevelationType.MECCAN, 5, listOf(30), 598, 1),
        Surah(98, "Al-Bayyinah", "البينة", "The Clear Proof", RevelationType.MEDINAN, 8, listOf(30), 598, 1),
        Surah(99, "Az-Zalzalah", "الزلزلة", "The Earthquake", RevelationType.MEDINAN, 8, listOf(30), 599, 1),
        Surah(100, "Al-Adiyat", "العاديات", "The Courser", RevelationType.MECCAN, 11, listOf(30), 599, 1),
        Surah(101, "Al-Qari'ah", "القارعة", "The Calamity", RevelationType.MECCAN, 11, listOf(30), 600, 1),
        Surah(102, "At-Takathur", "التكاثر", "The Rivalry in Worldly Increase", RevelationType.MECCAN, 8, listOf(30), 600, 1),
        Surah(103, "Al-Asr", "العصر", "The Declining Day", RevelationType.MECCAN, 3, listOf(30), 601, 1),
        Surah(104, "Al-Humazah", "الهمزة", "The Traducer", RevelationType.MECCAN, 9, listOf(30), 601, 1),
        Surah(105, "Al-Fil", "الفيل", "The Elephant", RevelationType.MECCAN, 5, listOf(30), 601, 1),
        Surah(106, "Quraysh", "قريش", "Quraysh", RevelationType.MECCAN, 4, listOf(30), 602, 1),
        Surah(107, "Al-Ma'un", "الماعون", "The Small Kindnesses", RevelationType.MECCAN, 7, listOf(30), 602, 1),
        Surah(108, "Al-Kawthar", "الكوثر", "The Abundance", RevelationType.MECCAN, 3, listOf(30), 602, 1),
        Surah(109, "Al-Kafirun", "الكافرون", "The Disbelievers", RevelationType.MECCAN, 6, listOf(30), 603, 1),
        Surah(110, "An-Nasr", "النصر", "The Divine Support", RevelationType.MEDINAN, 3, listOf(30), 603, 1),
        Surah(111, "Al-Masad", "المسد", "The Palm Fiber", RevelationType.MECCAN, 5, listOf(30), 603, 1),
        Surah(112, "Al-Ikhlas", "الإخلاص", "The Sincerity", RevelationType.MECCAN, 4, listOf(30), 604, 1),
        Surah(113, "Al-Falaq", "الفلق", "The Daybreak", RevelationType.MECCAN, 5, listOf(30), 604, 1),
        Surah(114, "An-Nas", "الناس", "Mankind", RevelationType.MECCAN, 6, listOf(30), 604, 1),
        Surah(1, "Al-Fatiha", "الفاتحة", "The Opening", RevelationType.MECCAN, 7, listOf(1), 1, 1)
    )
}
