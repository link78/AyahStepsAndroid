package com.deenlearn.app.models

import kotlinx.serialization.Serializable

/**
 * Represents a scheduled reminder
 */
@Serializable
data class Reminder(
    val id: String,
    val title: String,
    val message: String,
    val category: ReminderCategory,
    val time: String, // HH:mm format
    val daysOfWeek: List<DayOfWeek> = DayOfWeek.values().toList(),
    val isEnabled: Boolean = true,
    val icon: String,
    val relatedContent: String? = null // Link to specific pillar/lesson
)

@Serializable
enum class ReminderCategory {
    PRAYER_TIME,
    DAILY_LEARNING,
    REFLECTION,
    QURAN_RECITATION,
    DHIKR,
    CUSTOM
}

@Serializable
enum class DayOfWeek {
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY,
    SUNDAY
}

/**
 * Predefined reminder templates
 */
object ReminderTemplates {
    val templates = listOf(
        Reminder(
            id = "morning_dhikr",
            title = "Morning Dhikr",
            message = "Start your day with remembrance of Allah 🤲",
            category = ReminderCategory.DHIKR,
            time = "07:00",
            icon = "🌅"
        ),
        Reminder(
            id = "daily_learning",
            title = "Daily Islamic Learning",
            message = "Time for your daily lesson! 📖",
            category = ReminderCategory.DAILY_LEARNING,
            time = "20:00",
            icon = "📚"
        ),
        Reminder(
            id = "evening_reflection",
            title = "Evening Reflection",
            message = "Take a moment to reflect on your day and journal 📝",
            category = ReminderCategory.REFLECTION,
            time = "21:00",
            icon = "🌙"
        ),
        Reminder(
            id = "quran_recitation",
            title = "Quran Recitation",
            message = "Read a page of Quran today 📖",
            category = ReminderCategory.QURAN_RECITATION,
            time = "19:00",
            icon = "📗"
        ),
        Reminder(
            id = "friday_preparation",
            title = "Jumah Preparation",
            message = "Prepare for Friday prayer - read Surah Al-Kahf 🕌",
            category = ReminderCategory.CUSTOM,
            time = "09:00",
            daysOfWeek = listOf(DayOfWeek.FRIDAY),
            icon = "🕌"
        )
    )
}

/**
 * User's reminder preferences
 */
@Serializable
data class ReminderPreferences(
    val userId: String = "default",
    val enabledReminders: List<String> = emptyList(), // Reminder IDs
    val customReminders: List<Reminder> = emptyList(),
    val notificationsEnabled: Boolean = true,
    val soundEnabled: Boolean = true,
    val vibrationEnabled: Boolean = true
)
