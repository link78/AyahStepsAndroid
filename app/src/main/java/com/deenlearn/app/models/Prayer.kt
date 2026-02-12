package com.deenlearn.app.models

import kotlinx.serialization.Serializable

@Serializable
data class WuduStep(
    val id: String,
    val stepNumber: Int,
    val name: String,
    val nameArabic: String,
    val description: String,
    val kidsDescription: String,
    val kidsEmoji: String,
    val duration: Int,
    val repetitions: Int,
    val isSunnah: Boolean,
    val commonMistakes: List<String>,
    val fiqhNotes: String?
)

@Serializable
data class SalahStep(
    val id: String,
    val stepNumber: Int,
    val name: String,
    val nameArabic: String,
    val description: String,
    val kidsDescription: String,
    val kidsEmoji: String,
    val position: SalahPosition,
    val recitation: Recitation?,
    val duration: Int,
    val repetitions: Int,
    val commonMistakes: List<String>,
    val fiqhNotes: String?
)

@Serializable
enum class SalahPosition {
    STANDING,
    BOWING,
    PROSTRATING,
    SITTING,
    STANDING_FROM_SUJUD;
    
    val displayName: String
        get() = when (this) {
            STANDING -> "Standing"
            BOWING -> "Bowing (Ruku)"
            PROSTRATING -> "Prostrating (Sujud)"
            SITTING -> "Sitting"
            STANDING_FROM_SUJUD -> "Rising"
        }
}

@Serializable
data class Recitation(
    val id: String,
    val arabic: String,
    val transliteration: String,
    val translation: String,
    val audioFileName: String?
)

@Serializable
enum class DuaCategory {
    AFTER_PRAYER,
    MORNING_ADHKAR,
    EVENING_ADHKAR,
    PROTECTION,
    TRAVEL,
    HEALING,
    GENERAL;
    
    val displayName: String
        get() = when (this) {
            AFTER_PRAYER -> "After Prayer"
            MORNING_ADHKAR -> "Morning Adhkar"
            EVENING_ADHKAR -> "Evening Adhkar"
            PROTECTION -> "Protection"
            TRAVEL -> "Travel"
            HEALING -> "Healing & Sickness"
            GENERAL -> "General Duas"
        }
    
    val icon: String
        get() = when (this) {
            AFTER_PRAYER -> "hands_sparkles"
            MORNING_ADHKAR -> "sunrise_fill"
            EVENING_ADHKAR -> "sunset_fill"
            PROTECTION -> "shield_fill"
            TRAVEL -> "airplane"
            HEALING -> "heart_fill"
            GENERAL -> "star_fill"
        }
    
    val colorName: String
        get() = when (this) {
            AFTER_PRAYER -> "blue"
            MORNING_ADHKAR -> "orange"
            EVENING_ADHKAR -> "purple"
            PROTECTION -> "green"
            TRAVEL -> "indigo"
            HEALING -> "red"
            GENERAL -> "teal"
        }
}

@Serializable
data class DuaAfterPrayer(
    val id: String,
    val name: String,
    val arabic: String,
    val transliteration: String,
    val translation: String,
    val benefit: String,
    val timesToRecite: Int,
    val category: DuaCategory = DuaCategory.AFTER_PRAYER,
    val source: String = "Sunnah",
    val hadithCollection: String? = null,
    val hadithNumber: Int? = null
)

@Serializable
data class PrayerMistake(
    val id: String,
    val title: String,
    val description: String,
    val correction: String,
    val category: MistakeCategory
)

@Serializable
enum class MistakeCategory {
    WUDU,
    SALAH,
    RECITATION,
    POSTURE;
    
    val displayName: String
        get() = name.lowercase().replaceFirstChar { it.uppercase() }
}
