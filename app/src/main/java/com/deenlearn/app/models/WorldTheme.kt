package com.deenlearn.app.models

import kotlinx.serialization.Serializable

/**
 * Represents a themed world for Kids Mode
 * Each pillar has its own story world with unique theme
 */
@Serializable
data class WorldTheme(
    val id: String,
    val pillarId: String,
    val name: String,
    val description: String,
    val emoji: String,
    val backgroundGradient: List<String>, // Color hex codes for gradient
    val mascotCharacter: String, // Character emoji or name
    val locationIcon: String, // Map location icon
    val isUnlocked: Boolean = false,
    val completionProgress: Int = 0 // 0-100
)

/**
 * Predefined world themes for the five pillars
 */
object WorldThemes {
    val shahadaIsland = WorldTheme(
        id = "shahada_island",
        pillarId = "1",
        name = "Shahada Island",
        description = "An island where faith blooms! Learn about believing in Allah and His Messenger ☝️",
        emoji = "🏝️",
        backgroundGradient = listOf("#4CAF50", "#81C784"),
        mascotCharacter = "🦜",
        locationIcon = "🏝️",
        isUnlocked = true // First world is always unlocked
    )
    
    val salahCity = WorldTheme(
        id = "salah_city",
        pillarId = "2",
        name = "Salah City",
        description = "A peaceful city where prayer times are special! Discover the beauty of Salah 🤲",
        emoji = "🌆",
        backgroundGradient = listOf("#2196F3", "#64B5F6"),
        mascotCharacter = "🕊️",
        locationIcon = "🕌",
        isUnlocked = false
    )
    
    val zakatVillage = WorldTheme(
        id = "zakat_village",
        pillarId = "3",
        name = "Zakat Village",
        description = "A caring village where everyone shares! Learn about giving and helping others 💝",
        emoji = "🏘️",
        backgroundGradient = listOf("#FF9800", "#FFB74D"),
        mascotCharacter = "🐑",
        locationIcon = "🏠",
        isUnlocked = false
    )
    
    val ramadanMountain = WorldTheme(
        id = "ramadan_mountain",
        pillarId = "4",
        name = "Ramadan Mountain",
        description = "Climb the mountain of patience! Explore the special month of fasting 🌙",
        emoji = "⛰️",
        backgroundGradient = listOf("#9C27B0", "#BA68C8"),
        mascotCharacter = "🦅",
        locationIcon = "⛰️",
        isUnlocked = false
    )
    
    val hajjDesert = WorldTheme(
        id = "hajj_desert",
        pillarId = "5",
        name = "Hajj Desert Journey",
        description = "Journey through the sacred desert! Experience the pilgrimage to Makkah 🕋",
        emoji = "🏜️",
        backgroundGradient = listOf("#F44336", "#E57373"),
        mascotCharacter = "🐪",
        locationIcon = "🕋",
        isUnlocked = false
    )
    
    val allWorlds = listOf(
        shahadaIsland,
        salahCity,
        zakatVillage,
        ramadanMountain,
        hajjDesert
    )
    
    fun getWorldByPillarId(pillarId: String): WorldTheme? {
        return allWorlds.find { it.pillarId == pillarId }
    }
    
    fun getWorldById(worldId: String): WorldTheme? {
        return allWorlds.find { it.id == worldId }
    }
}
