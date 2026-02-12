package com.deenlearn.app.models

import kotlinx.serialization.Serializable

@Serializable
data class Pillar(
    val id: String,
    val number: Int,
    val name: String,
    val nameArabic: String,
    val icon: String,
    val colorHex: String,
    val worldEmoji: String,
    val description: String,
    val storyEpisodes: List<StoryEpisode>,
    val miniGames: List<MiniGame>,
    val definition: String,
    val quranEvidence: List<Evidence>,
    val hadithEvidence: List<Evidence>,
    val wisdom: String,
    val practicalApplication: String,
    val scenarios: List<Scenario>,
    val fiqhDifferences: List<FiqhDifference>,
    val kidsHadiths: List<KidsHadith>
)

@Serializable
data class StoryEpisode(
    val id: String,
    val title: String,
    val narrator: String,
    val content: String,
    val emoji: String,
    val duration: Int
)

@Serializable
data class MiniGame(
    val id: String,
    val title: String,
    val type: MiniGameType,
    val description: String,
    val icon: String
)

@Serializable
enum class MiniGameType {
    MATCH_PILLAR_MEANING,
    FIX_BROKEN_PILLAR,
    ORDER_THE_PILLARS,
    QUIZ_TIME
}

@Serializable
data class Evidence(
    val id: String,
    val arabic: String,
    val translation: String,
    val reference: String
)

@Serializable
data class Scenario(
    val id: String,
    val question: String,
    val answer: String,
    val category: String
)

@Serializable
data class FiqhDifference(
    val id: String,
    val topic: String,
    val hanafi: String,
    val maliki: String,
    val shafii: String,
    val hanbali: String
)
