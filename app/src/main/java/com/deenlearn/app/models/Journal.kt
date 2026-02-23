package com.deenlearn.app.models

import kotlinx.serialization.Serializable

/**
 * Represents a journal entry for reflection and notes
 */
@Serializable
data class JournalEntry(
    val id: String,
    val timestamp: Long,
    val date: String, // Human-readable date
    val title: String,
    val content: String,
    val relatedPillarId: String? = null,
    val relatedModuleId: String? = null,
    val tags: List<String> = emptyList(),
    val mood: Mood? = null,
    val reflectionPromptId: String? = null
)

@Serializable
enum class Mood {
    GRATEFUL,
    PEACEFUL,
    MOTIVATED,
    THOUGHTFUL,
    CHALLENGED,
    INSPIRED
}

/**
 * Reflection prompts for adults
 */
@Serializable
data class ReflectionPrompt(
    val id: String,
    val category: ReflectionCategory,
    val question: String,
    val description: String,
    val relatedPillarId: String? = null,
    val relatedVerse: String? = null
)

@Serializable
enum class ReflectionCategory {
    DAILY_REFLECTION,
    PILLAR_UNDERSTANDING,
    PERSONAL_GROWTH,
    FAITH_PRACTICE,
    GRATITUDE,
    CHALLENGE
}

/**
 * Predefined reflection prompts
 */
object ReflectionPrompts {
    val prompts = listOf(
        ReflectionPrompt(
            id = "daily_shahada",
            category = ReflectionCategory.PILLAR_UNDERSTANDING,
            question = "How does your Shahada (declaration of faith) influence your daily decisions?",
            description = "Reflect on how believing in Allah and His Messenger guides your choices.",
            relatedPillarId = "1"
        ),
        ReflectionPrompt(
            id = "daily_salah",
            category = ReflectionCategory.FAITH_PRACTICE,
            question = "What helps you maintain focus (khushu') in your prayers?",
            description = "Think about strategies that help you connect with Allah during Salah.",
            relatedPillarId = "2"
        ),
        ReflectionPrompt(
            id = "daily_gratitude",
            category = ReflectionCategory.GRATITUDE,
            question = "What are three blessings from Allah you're grateful for today?",
            description = "Practice daily gratitude by recognizing Allah's favors.",
            relatedPillarId = null
        ),
        ReflectionPrompt(
            id = "zakat_reflection",
            category = ReflectionCategory.PILLAR_UNDERSTANDING,
            question = "How can you incorporate the spirit of Zakat in your everyday interactions?",
            description = "Reflect on generosity and helping others beyond financial giving.",
            relatedPillarId = "3"
        ),
        ReflectionPrompt(
            id = "sawm_discipline",
            category = ReflectionCategory.PERSONAL_GROWTH,
            question = "What lessons from fasting can you apply to other areas of self-discipline?",
            description = "Consider how the patience and control from Sawm benefits your character.",
            relatedPillarId = "4"
        ),
        ReflectionPrompt(
            id = "hajj_journey",
            category = ReflectionCategory.PILLAR_UNDERSTANDING,
            question = "What spiritual preparations can you make for your Hajj journey?",
            description = "Reflect on preparing your heart, even if Hajj is years away.",
            relatedPillarId = "5"
        ),
        ReflectionPrompt(
            id = "missed_prayer",
            category = ReflectionCategory.CHALLENGE,
            question = "When you miss Fajr, how do you make it up and prevent it from happening again?",
            description = "Develop strategies for consistency in early morning prayer.",
            relatedPillarId = "2"
        ),
        ReflectionPrompt(
            id = "faith_growth",
            category = ReflectionCategory.PERSONAL_GROWTH,
            question = "What aspect of your faith do you want to strengthen this month?",
            description = "Set intentional goals for your spiritual development.",
            relatedPillarId = null
        )
    )
    
    fun getPromptsByPillar(pillarId: String): List<ReflectionPrompt> {
        return prompts.filter { it.relatedPillarId == pillarId }
    }
    
    fun getDailyPrompt(): ReflectionPrompt {
        // Simple rotation based on day of month
        val dayIndex = (System.currentTimeMillis() / (1000 * 60 * 60 * 24)).toInt() % prompts.size
        return prompts[dayIndex]
    }
}
