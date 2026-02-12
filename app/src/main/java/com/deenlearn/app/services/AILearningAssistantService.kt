package com.deenlearn.app.services

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

/**
 * AI Learning Assistant Service
 * Provides personalized learning recommendations based on user progress
 */

data class LearningRecommendation(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val category: LearningCategory,
    val priority: Priority,
    val estimatedTime: Int,
    val icon: String
)

data class WeakArea(
    val id: String = UUID.randomUUID().toString(),
    val topic: String,
    val category: LearningCategory,
    val score: Int,
    val suggestion: String
)

enum class LearningCategory(val displayName: String, val color: String) {
    PILLARS("Pillars", "green"),
    SALAH("Salah", "blue"),
    QURAN("Qur'an", "purple"),
    ARABIC("Arabic", "orange"),
    GENERAL("General", "gray")
}

enum class Priority {
    HIGH, MEDIUM, LOW
}

data class UserProgress(
    var pillarsProgress: Int = 0,
    var salahProgress: Int = 0,
    var quranProgress: Int = 0,
    var arabicProgress: Int = 0,
    var lastActiveDate: Date = Date(),
    var totalMinutesLearned: Int = 0,
    var completedLessons: Int = 0
)

object AILearningAssistantService {
    
    private val _dailyRecommendations = MutableStateFlow<List<LearningRecommendation>>(emptyList())
    val dailyRecommendations: StateFlow<List<LearningRecommendation>> = _dailyRecommendations.asStateFlow()
    
    private val _weakAreas = MutableStateFlow<List<WeakArea>>(emptyList())
    val weakAreas: StateFlow<List<WeakArea>> = _weakAreas.asStateFlow()
    
    private val _streak = MutableStateFlow(0)
    val streak: StateFlow<Int> = _streak.asStateFlow()
    
    init {
        generateInitialRecommendations()
    }
    
    fun generateRecommendations(progress: UserProgress, isKidsMode: Boolean): List<LearningRecommendation> {
        val recommendations = mutableListOf<LearningRecommendation>()
        
        if (progress.pillarsProgress < 50) {
            recommendations.add(LearningRecommendation(
                title = if (isKidsMode) "🏝️ Explore Shahada Island!" else "Review: Five Pillars of Islam",
                description = if (isKidsMode) 
                    "Join Captain Iman on an adventure!"
                else 
                    "Strengthen your understanding of the pillars",
                category = LearningCategory.PILLARS,
                priority = Priority.HIGH,
                estimatedTime = 10,
                icon = "star"
            ))
        }
        
        if (progress.salahProgress < 60) {
            recommendations.add(LearningRecommendation(
                title = if (isKidsMode) "🕌 Prayer Time!" else "Salah Improvement",
                description = if (isKidsMode)
                    "Learn the fun prayer moves!"
                else
                    "Review prayer positions and recitations",
                category = LearningCategory.SALAH,
                priority = Priority.HIGH,
                estimatedTime = 15,
                icon = "prayer"
            ))
        }
        
        if (progress.quranProgress < 40) {
            recommendations.add(LearningRecommendation(
                title = if (isKidsMode) "📖 Story Time!" else "Qur'an Memorization",
                description = if (isKidsMode)
                    "Listen to beautiful Qur'an!"
                else
                    "Continue memorizing with tajweed",
                category = LearningCategory.QURAN,
                priority = Priority.MEDIUM,
                estimatedTime = 20,
                icon = "book"
            ))
        }
        
        if (progress.arabicProgress < 30) {
            recommendations.add(LearningRecommendation(
                title = if (isKidsMode) "🔤 Letter Adventure!" else "Arabic Letters Practice",
                description = if (isKidsMode)
                    "Meet the Arabic letters!"
                else
                    "Review letter forms and pronunciation",
                category = LearningCategory.ARABIC,
                priority = Priority.MEDIUM,
                estimatedTime = 10,
                icon = "text_format"
            ))
        }
        
        recommendations.add(LearningRecommendation(
            title = if (isKidsMode) "⭐ Daily Star Challenge!" else "Daily Review",
            description = if (isKidsMode)
                "Earn your daily stars!"
            else
                "Quick review of yesterday's content",
            category = LearningCategory.GENERAL,
            priority = Priority.LOW,
            estimatedTime = 5,
            icon = "stars"
        ))
        
        return recommendations.sortedByDescending { it.priority }
    }
    
    fun analyzeWeakAreas(progress: UserProgress): List<WeakArea> {
        val areas = mutableListOf<WeakArea>()
        
        if (progress.pillarsProgress < 50) {
            areas.add(WeakArea(
                topic = "Pillars of Islam",
                category = LearningCategory.PILLARS,
                score = progress.pillarsProgress,
                suggestion = "Focus on understanding each pillar through stories"
            ))
        }
        
        if (progress.salahProgress < 50) {
            areas.add(WeakArea(
                topic = "Prayer Movements",
                category = LearningCategory.SALAH,
                score = progress.salahProgress,
                suggestion = "Practice the physical movements step-by-step"
            ))
        }
        
        if (progress.quranProgress < 40) {
            areas.add(WeakArea(
                topic = "Qur'an Recitation",
                category = LearningCategory.QURAN,
                score = progress.quranProgress,
                suggestion = "Listen more frequently and practice with AI Coach"
            ))
        }
        
        if (progress.arabicProgress < 30) {
            areas.add(WeakArea(
                topic = "Arabic Alphabet",
                category = LearningCategory.ARABIC,
                score = progress.arabicProgress,
                suggestion = "Spend 10 minutes daily on letter recognition"
            ))
        }
        
        return areas.sortedBy { it.score }
    }
    
    fun getMotivationalMessage(streak: Int, isKidsMode: Boolean): String {
        return if (isKidsMode) {
            when {
                streak == 0 -> "🌟 Welcome back! Let's start a new adventure!"
                streak in 1..3 -> "🔥 $streak day streak! Keep it up!"
                streak in 4..7 -> "⭐ Wow! $streak days! You're a superstar!"
                streak in 8..14 -> "🏆 Amazing! $streak days! You're a champion!"
                streak in 15..30 -> "🎉 $streak days! MashaAllah! Unstoppable!"
                else -> "👑 $streak days! You're a true master!"
            }
        } else {
            when {
                streak == 0 -> "Welcome back. Consistency is key to learning."
                streak in 1..3 -> "$streak-day streak. Good start."
                streak in 4..7 -> "$streak consecutive days. Excellent commitment."
                streak in 8..14 -> "Impressive $streak-day streak."
                streak in 15..30 -> "$streak days! Your dedication is admirable."
                else -> "MashaAllah! $streak-day streak. Exemplary pursuit of knowledge."
            }
        }
    }
    
    fun generateStudyPlan(totalMinutes: Int, isKidsMode: Boolean): List<String> {
        return if (isKidsMode) {
            when {
                totalMinutes <= 15 -> listOf(
                    "🌟 5 min: Watch a story video",
                    "🎮 5 min: Play a matching game",
                    "⭐ 5 min: Earn daily stars!"
                )
                totalMinutes <= 30 -> listOf(
                    "🏝️ 10 min: Explore a Pillar World",
                    "📖 10 min: Listen to Qur'an",
                    "🔤 5 min: Letter practice",
                    "⭐ 5 min: Quiz time!"
                )
                else -> listOf(
                    "🏝️ 15 min: Complete world adventure",
                    "🕌 10 min: Prayer practice",
                    "📖 10 min: Qur'an listening",
                    "🔤 10 min: Arabic games",
                    "⭐ 5 min: Review and stars!"
                )
            }
        } else {
            when {
                totalMinutes <= 15 -> listOf(
                    "5 min: Review yesterday's content",
                    "10 min: Focus on weakest area"
                )
                totalMinutes <= 30 -> listOf(
                    "10 min: Qur'an recitation",
                    "10 min: Study lesson content",
                    "10 min: Practice quiz"
                )
                else -> listOf(
                    "15 min: Qur'an memorization/review",
                    "15 min: Deep study of topic",
                    "10 min: Arabic vocabulary",
                    "10 min: Assessment and review"
                )
            }
        }
    }
    
    private fun generateInitialRecommendations() {
        val defaultProgress = UserProgress(
            pillarsProgress = 30,
            salahProgress = 40,
            quranProgress = 25,
            arabicProgress = 20
        )
        _dailyRecommendations.value = generateRecommendations(defaultProgress, true)
        _weakAreas.value = analyzeWeakAreas(defaultProgress)
    }
}
