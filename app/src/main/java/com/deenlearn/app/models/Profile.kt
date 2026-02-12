package com.deenlearn.app.models

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable
import java.util.Date
import java.util.UUID

@Serializable
data class UserProfile(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    var name: String,
    var displayName: String,
    var avatarEmoji: String,
    var email: String?,
    var isParent: Boolean,
    var preferredLanguage: AppLanguage,
    @Serializable(with = DateSerializer::class)
    var createdAt: Date,
    var profileImagePath: String?,
    var showArabicScript: Boolean,
    var showTransliteration: Boolean,
    var showTranslation: Boolean,
    var preferredTranslation: String,
    var dailyGoalMinutes: Int,
    @Serializable(with = DateSerializer::class)
    var reminderTime: Date?,
    var remindersEnabled: Boolean
)

@Serializable
enum class AppLanguage {
    ENGLISH,
    ARABIC,
    URDU,
    FRENCH,
    TURKISH,
    INDONESIAN,
    MALAY;
    
    val displayName: String
        get() = when (this) {
            ENGLISH -> "English"
            ARABIC -> "العربية"
            URDU -> "اردو"
            FRENCH -> "Français"
            TURKISH -> "Türkçe"
            INDONESIAN -> "Bahasa Indonesia"
            MALAY -> "Bahasa Melayu"
        }
    
    val flag: String
        get() = when (this) {
            ENGLISH -> "🇺🇸"
            ARABIC -> "🇸🇦"
            URDU -> "🇵🇰"
            FRENCH -> "🇫🇷"
            TURKISH -> "🇹🇷"
            INDONESIAN -> "🇮🇩"
            MALAY -> "🇲🇾"
        }
}

@Serializable
data class ChildProfile(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    var name: String,
    var avatarEmoji: String,
    var age: Int,
    @Serializable(with = DateSerializer::class)
    var createdAt: Date,
    var totalLearningMinutes: Int,
    var currentStreak: Int,
    var longestStreak: Int,
    var surahsMemorized: Int,
    var arabicLettersLearned: Int,
    var pillarsCompleted: Int,
    var prayerStepsLearned: Int,
    var dailyGoalMinutes: Int,
    var weeklyGoalMinutes: Int,
    var todayMinutes: Int,
    var weekMinutes: Int,
    var screenTimeLimit: Int,
    var allowedCategories: Set<ContentCategory>,
    var parentalControlsEnabled: Boolean,
    var totalStars: Int,
    var totalBadges: Int,
    var achievements: List<Achievement>
) {
    val dailyGoalProgress: Double
        get() = if (dailyGoalMinutes > 0) {
            (todayMinutes.toDouble() / dailyGoalMinutes.toDouble()).coerceAtMost(1.0)
        } else 0.0
    
    val weeklyGoalProgress: Double
        get() = if (weeklyGoalMinutes > 0) {
            (weekMinutes.toDouble() / weeklyGoalMinutes.toDouble()).coerceAtMost(1.0)
        } else 0.0
}

@Serializable
enum class ContentCategory {
    QURAN,
    ARABIC,
    PILLARS,
    PRAYER,
    ADVANCED;
    
    val displayName: String
        get() = when (this) {
            QURAN -> "Qur'an"
            ARABIC -> "Arabic"
            PILLARS -> "Pillars"
            PRAYER -> "Prayer"
            ADVANCED -> "Advanced Topics"
        }
    
    val icon: String
        get() = when (this) {
            QURAN -> "📖"
            ARABIC -> "أ"
            PILLARS -> "🕌"
            PRAYER -> "🤲"
            ADVANCED -> "📚"
        }
}

@Serializable
data class LearningGoal(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    var title: String,
    var category: GoalCategory,
    var targetValue: Int,
    var currentValue: Int,
    var unit: String,
    @Serializable(with = DateSerializer::class)
    var deadline: Date?,
    var isCompleted: Boolean
) {
    val progress: Double
        get() = if (targetValue > 0) {
            (currentValue.toDouble() / targetValue.toDouble()).coerceAtMost(1.0)
        } else 0.0
}

@Serializable
enum class GoalCategory {
    QURAN,
    ARABIC,
    PRAYER,
    PILLARS,
    DAILY;
    
    val displayName: String
        get() = when (this) {
            QURAN -> "Qur'an"
            ARABIC -> "Arabic"
            PRAYER -> "Prayer"
            PILLARS -> "Pillars"
            DAILY -> "Daily Practice"
        }
    
    val icon: String
        get() = when (this) {
            QURAN -> "book_fill"
            ARABIC -> "character_textbox"
            PRAYER -> "hands_sparkles_fill"
            PILLARS -> "building_columns_fill"
            DAILY -> "calendar"
        }
}

@Serializable
data class Achievement(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    var title: String,
    var description: String,
    var icon: String,
    var category: AchievementCategory,
    @Serializable(with = DateSerializer::class)
    var earnedAt: Date?,
    var isEarned: Boolean,
    var requirement: String
)

@Serializable
enum class AchievementCategory {
    QURAN,
    PRAYER,
    ARABIC,
    PILLARS,
    STREAK;
    
    val displayName: String
        get() = when (this) {
            QURAN -> "Qur'an"
            PRAYER -> "Prayer"
            ARABIC -> "Arabic"
            PILLARS -> "Pillars"
            STREAK -> "Streaks"
        }
}

@Serializable
data class Bookmark(
    @Serializable(with = UUIDSerializer::class)
    val id: UUID,
    var title: String,
    var subtitle: String,
    var category: BookmarkCategory,
    var reference: String,
    @Serializable(with = DateSerializer::class)
    var createdAt: Date,
    var note: String?
)

@Serializable
enum class BookmarkCategory {
    SURAH,
    AYAH,
    LESSON,
    DUA;
    
    val displayName: String
        get() = when (this) {
            SURAH -> "Surah"
            AYAH -> "Ayah"
            LESSON -> "Lesson"
            DUA -> "Dua"
        }
    
    val icon: String
        get() = when (this) {
            SURAH -> "book_fill"
            AYAH -> "text_quote"
            LESSON -> "graduationcap_fill"
            DUA -> "hands_sparkles_fill"
        }
}

@Serializable
data class ProgressAnalytics(
    var totalMinutesThisWeek: Int,
    var totalMinutesThisMonth: Int,
    var averageMinutesPerDay: Double,
    var currentStreak: Int,
    var longestStreak: Int,
    var lessonsCompleted: Int,
    var quizzesPassed: Int,
    var dailyMinutes: List<DailyProgress>,
    var categoryProgress: List<CategoryProgress>
)

@Serializable
data class DailyProgress(
    var day: String,
    var minutes: Int,
    @Serializable(with = DateSerializer::class)
    var date: Date
)

@Serializable
data class CategoryProgress(
    var category: GoalCategory,
    var percentComplete: Double,
    var lessonsCompleted: Int,
    var totalLessons: Int
)
