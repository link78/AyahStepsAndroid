package com.deenlearn.app.models

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
enum class UserMode {
    KIDS,
    ADULTS
}

@Serializable
enum class AppearanceMode(val icon: String) {
    SYSTEM("circle_half_filled"),
    LIGHT("sun_max_fill"),
    DARK("moon_fill")
}

@Serializable
enum class AgeGroup {
    EARLY_CHILDHOOD,
    CHILDREN,
    TWEENS,
    TEENS,
    ADULTS;
    
    val displayName: String
        get() = when (this) {
            EARLY_CHILDHOOD -> "Early Childhood"
            CHILDREN -> "Children"
            TWEENS -> "Tweens"
            TEENS -> "Teens"
            ADULTS -> "Adults"
        }
    
    val ageRange: String
        get() = when (this) {
            EARLY_CHILDHOOD -> "4-6"
            CHILDREN -> "7-9"
            TWEENS -> "10-12"
            TEENS -> "13-17"
            ADULTS -> "18+"
        }
    
    val minAge: Int
        get() = when (this) {
            EARLY_CHILDHOOD -> 4
            CHILDREN -> 7
            TWEENS -> 10
            TEENS -> 13
            ADULTS -> 18
        }
    
    val maxAge: Int
        get() = when (this) {
            EARLY_CHILDHOOD -> 6
            CHILDREN -> 9
            TWEENS -> 12
            TEENS -> 17
            ADULTS -> 99
        }
    
    val readingLevel: String
        get() = when (this) {
            EARLY_CHILDHOOD -> "Simple"
            CHILDREN -> "Easy"
            TWEENS -> "Moderate"
            TEENS -> "Advanced"
            ADULTS -> "Scholarly"
        }
    
    val vocabularyLevel: Int
        get() = when (this) {
            EARLY_CHILDHOOD -> 1
            CHILDREN -> 2
            TWEENS -> 3
            TEENS -> 4
            ADULTS -> 5
        }
    
    val recommendedSessionLength: Int
        get() = when (this) {
            EARLY_CHILDHOOD -> 5
            CHILDREN -> 10
            TWEENS -> 15
            TEENS -> 20
            ADULTS -> 30
        }
    
    val memorizationChunkSize: Int
        get() = when (this) {
            EARLY_CHILDHOOD -> 3
            CHILDREN -> 5
            TWEENS -> 7
            TEENS -> 10
            ADULTS -> 15
        }
    
    val fontSizeMultiplier: Float
        get() = when (this) {
            EARLY_CHILDHOOD -> 1.4f
            CHILDREN -> 1.2f
            TWEENS -> 1.1f
            TEENS -> 1.0f
            ADULTS -> 1.0f
        }
    
    val showScholarlyContent: Boolean
        get() = when (this) {
            EARLY_CHILDHOOD, CHILDREN, TWEENS -> false
            TEENS, ADULTS -> true
        }
    
    val greeting: String
        get() = when (this) {
            EARLY_CHILDHOOD -> "Little Explorer"
            CHILDREN -> "Young Learner"
            TWEENS -> "Star Student"
            TEENS -> "Knowledge Seeker"
            ADULTS -> "Dear Learner"
        }
    
    fun dailyGoalText(activity: String): String = when (this) {
        EARLY_CHILDHOOD -> when (activity) {
            "quran" -> "Listen to Quran! 📖"
            "salah" -> "Learn to pray! 🙏"
            "arabic" -> "Fun letters! ✨"
            else -> "Let's learn!"
        }
        CHILDREN -> when (activity) {
            "quran" -> "Read some Quran today"
            "salah" -> "Practice your salah"
            "arabic" -> "Learn a new letter"
            else -> "Keep learning!"
        }
        TWEENS -> when (activity) {
            "quran" -> "Continue your Quran lesson"
            "salah" -> "Practice salah steps"
            "arabic" -> "Learn Arabic vocabulary"
            else -> "Continue learning"
        }
        TEENS, ADULTS -> when (activity) {
            "quran" -> "Continue Quran memorization"
            "salah" -> "Review salah with tajweed"
            "arabic" -> "Study Arabic grammar"
            else -> "Resume your studies"
        }
    }
    
    val encouragementMessage: String
        get() = when (this) {
            EARLY_CHILDHOOD -> "You're doing amazing! ⭐🎉"
            CHILDREN -> "Great job! Keep going! 🌟"
            TWEENS -> "Excellent progress! You're learning so much!"
            TEENS -> "MashAllah! Your dedication is inspiring."
            ADULTS -> "May Allah bless your efforts in seeking knowledge."
        }
    
    val contentGuidelines: List<String>
        get() = when (this) {
            EARLY_CHILDHOOD -> listOf(
                "Use simple 1-2 syllable words",
                "Lots of pictures and animations",
                "Sessions under 5 minutes",
                "Repetition is key",
                "Heavy use of rewards and praise",
                "No complex concepts"
            )
            CHILDREN -> listOf(
                "Simple sentences",
                "Interactive elements",
                "Sessions 10-15 minutes",
                "Basic Islamic concepts",
                "Star and badge rewards",
                "Stories and characters"
            )
            TWEENS -> listOf(
                "More detailed explanations",
                "Some Arabic terminology",
                "Sessions 15-20 minutes",
                "Basic reasoning and wisdom",
                "Achievement tracking",
                "Peer comparisons okay"
            )
            TEENS -> listOf(
                "Scholarly references acceptable",
                "Critical thinking encouraged",
                "Sessions 20-30 minutes",
                "Fiqh basics introduced",
                "Independent study support",
                "Real-world applications"
            )
            ADULTS -> listOf(
                "Full scholarly content",
                "Detailed fiqh discussions",
                "Flexible session lengths",
                "Multiple scholarly opinions",
                "Self-directed learning",
                "Advanced tajweed"
            )
        }
    
    companion object {
        fun fromAge(age: Int): AgeGroup = when (age) {
            in 0..6 -> EARLY_CHILDHOOD
            in 7..9 -> CHILDREN
            in 10..12 -> TWEENS
            in 13..17 -> TEENS
            else -> ADULTS
        }
    }
}

@Serializable
data class AppState(
    val currentMode: UserMode = UserMode.KIDS,
    val selectedAge: Int? = null,
    val totalPoints: Int = 0,
    val currentStreak: Int = 0,
    val longestStreak: Int = 0,
    val starsEarned: Int = 0,
    val completedLessons: Set<String> = emptySet(),
    val earnedBadges: Set<Badge> = emptySet(),
    val journalEntries: List<JournalEntryData> = emptyList(),
    @Serializable(with = DateSerializer::class)
    val lastActivityDate: Date? = null,
    val dailyGoals: DailyGoals = DailyGoals()
) {
    // Nested enum for backward compatibility with AppState.UserMode
    @Serializable
    enum class UserMode {
        KIDS,
        ADULTS
    }
    
    // Nested Badge class for backward compatibility with AppState.Badge
    @Serializable
    data class Badge(
        val id: String,
        val title: String,
        val icon: String,
        @Serializable(with = DateSerializer::class)
        val earnedDate: Date?
    ) {
        val isEarned: Boolean
            get() = earnedDate != null
    }
    
    // Type alias for backward compatibility with AppState.DailyGoals
    @Serializable
    data class DailyGoals(
        var quranSessionCompleted: Boolean = false,
        var salahPractice: Boolean = false,
        var arabicPractice: Boolean = false,
        @Serializable(with = DateSerializer::class)
        var lastUpdated: Date = Date()
    )
}

@Serializable
data class LearningProgress(
    var salahMastery: Double = 0.0,
    var quranMemorization: Double = 0.0,
    var arabicLettersLearned: Int = 0,
    var pillarsCompleted: Int = 0,
    var starsEarned: Int = 0,
    var lastLessonId: String? = null,
    var lastLessonTitle: String? = null,
    var currentQuranSurah: Int = 1,
    var currentQuranAyah: Int = 1,
    var currentSalahStep: Int = 1,
    var currentArabicLetter: Int = 0
)

@Serializable
data class JournalEntryData(
    val id: String,
    val pillarId: String,
    val promptId: String?,
    val content: String,
    @Serializable(with = DateSerializer::class)
    val date: Date
)

object AppStateConstants {
    val arabicLetters = listOf(
        "ا", "ب", "ت", "ث", "ج", "ح", "خ", "د", "ذ", "ر", "ز", "س", 
        "ش", "ص", "ض", "ط", "ظ", "ع", "غ", "ف", "ق", "ك", "ل", "م", 
        "ن", "ه", "و", "ي"
    )
    
    fun createDefaultBadges(): List<AppState.Badge> = listOf(
        AppState.Badge("first_step", "First Step", "figure_walk", null),
        AppState.Badge("quran_starter", "Quran Starter", "book_fill", null),
        AppState.Badge("salah_learner", "Salah Learner", "person_fill", null),
        AppState.Badge("arabic_explorer", "Arabic Explorer", "character_textbox", null),
        AppState.Badge("streak_master", "7-Day Streak", "flame_fill", null),
        AppState.Badge("star_collector", "Star Collector", "star_fill", null),
        AppState.Badge("pillar_champion", "Pillar Champion", "building_columns_fill", null),
        AppState.Badge("dedication", "Dedicated Learner", "heart_fill", null)
    )
}
