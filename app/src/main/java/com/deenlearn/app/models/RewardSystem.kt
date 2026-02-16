package com.deenlearn.app.models

import kotlinx.serialization.Serializable

/**
 * Reward types for Kids Mode gamification
 */
@Serializable
enum class RewardType {
    STAR,
    BADGE,
    TROPHY,
    CHARACTER_UPGRADE
}

/**
 * Represents a reward that can be earned
 */
@Serializable
data class Reward(
    val id: String,
    val name: String,
    val description: String,
    val type: RewardType,
    val icon: String, // Emoji or resource name
    val pointsRequired: Int = 0,
    val relatedPillarId: String? = null,
    val rarity: RewardRarity = RewardRarity.COMMON
)

@Serializable
enum class RewardRarity {
    COMMON,
    RARE,
    EPIC,
    LEGENDARY
}

/**
 * Achievement/Badge that can be unlocked
 */
@Serializable
data class Achievement(
    val id: String,
    val name: String,
    val description: String,
    val icon: String,
    val unlockedIcon: String,
    val category: AchievementCategory,
    val requirement: String, // Description of what's needed
    val points: Int,
    val isSecret: Boolean = false
)

@Serializable
enum class AchievementCategory {
    STORY_COMPLETION,
    GAME_MASTER,
    KNOWLEDGE_SEEKER,
    DAILY_LEARNER,
    PILLAR_EXPERT
}

/**
 * User's progress and earned rewards
 */
@Serializable
data class UserProgress(
    val userId: String = "default",
    val totalStars: Int = 0,
    val totalPoints: Int = 0,
    val level: Int = 1,
    val earnedRewards: List<String> = emptyList(), // Reward IDs
    val unlockedAchievements: List<String> = emptyList(), // Achievement IDs
    val worldProgress: Map<String, Int> = emptyMap(), // worldId -> progress percentage
    val completedStories: List<String> = emptyList(), // Story IDs
    val completedGames: List<String> = emptyList(), // Game IDs
    val currentStreak: Int = 0, // Days in a row
    val longestStreak: Int = 0,
    val lastActivityDate: Long = 0L // Timestamp
)

/**
 * Predefined rewards and achievements
 */
object RewardData {
    
    // Stars earned for completing activities
    const val STARS_PER_STORY = 3
    const val STARS_PER_GAME = 5
    const val STARS_PER_LESSON = 2
    
    // Points calculation
    const val POINTS_PER_STAR = 10
    const val POINTS_FOR_STREAK_DAY = 20
    
    // Level progression
    fun calculateLevel(totalPoints: Int): Int {
        return (totalPoints / 100) + 1
    }
    
    // Predefined achievements
    val achievements = listOf(
        Achievement(
            id = "first_story",
            name = "Story Starter",
            description = "Complete your first story!",
            icon = "📖",
            unlockedIcon = "📘",
            category = AchievementCategory.STORY_COMPLETION,
            requirement = "Complete 1 story",
            points = 50
        ),
        Achievement(
            id = "all_pillars",
            name = "Five Pillar Master",
            description = "Learn about all five pillars of Islam",
            icon = "🕌",
            unlockedIcon = "✨🕌",
            category = AchievementCategory.PILLAR_EXPERT,
            requirement = "Visit all 5 worlds",
            points = 200
        ),
        Achievement(
            id = "week_streak",
            name = "Weekly Warrior",
            description = "Learn every day for a week!",
            icon = "🔥",
            unlockedIcon = "🔥✨",
            category = AchievementCategory.DAILY_LEARNER,
            requirement = "7 day streak",
            points = 150
        ),
        Achievement(
            id = "game_champion",
            name = "Game Champion",
            description = "Complete all mini-games!",
            icon = "🎮",
            unlockedIcon = "🏆🎮",
            category = AchievementCategory.GAME_MASTER,
            requirement = "Complete all games",
            points = 300
        ),
        Achievement(
            id = "knowledge_100",
            name = "Century Scholar",
            description = "Earn 100 stars!",
            icon = "⭐",
            unlockedIcon = "💯⭐",
            category = AchievementCategory.KNOWLEDGE_SEEKER,
            requirement = "Collect 100 stars",
            points = 500
        )
    )
    
    // Character upgrades (cosmetic rewards)
    val characterUpgrades = listOf(
        Reward(
            id = "mascot_shahada",
            name = "Island Parrot",
            description = "Unlock the Shahada Island mascot!",
            type = RewardType.CHARACTER_UPGRADE,
            icon = "🦜",
            pointsRequired = 100,
            relatedPillarId = "1"
        ),
        Reward(
            id = "mascot_salah",
            name = "Peace Dove",
            description = "Unlock the Salah City mascot!",
            type = RewardType.CHARACTER_UPGRADE,
            icon = "🕊️",
            pointsRequired = 200,
            relatedPillarId = "2"
        ),
        Reward(
            id = "mascot_zakat",
            name = "Caring Sheep",
            description = "Unlock the Zakat Village mascot!",
            type = RewardType.CHARACTER_UPGRADE,
            icon = "🐑",
            pointsRequired = 300,
            relatedPillarId = "3"
        ),
        Reward(
            id = "mascot_sawm",
            name = "Mountain Eagle",
            description = "Unlock the Ramadan Mountain mascot!",
            type = RewardType.CHARACTER_UPGRADE,
            icon = "🦅",
            pointsRequired = 400,
            relatedPillarId = "4"
        ),
        Reward(
            id = "mascot_hajj",
            name = "Desert Camel",
            description = "Unlock the Hajj Desert mascot!",
            type = RewardType.CHARACTER_UPGRADE,
            icon = "🐪",
            pointsRequired = 500,
            relatedPillarId = "5"
        )
    )
}
