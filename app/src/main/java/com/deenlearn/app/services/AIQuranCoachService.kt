package com.deenlearn.app.services

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*
import kotlin.random.Random

/**
 * AI Quran Coach Service
 * Provides AI-powered Quran recitation analysis and feedback
 */

data class RecitationFeedback(
    val id: String = UUID.randomUUID().toString(),
    val overallScore: Int,
    val pronunciationScore: Int,
    val tajweedScore: Int,
    val fluencyScore: Int,
    val feedback: List<FeedbackItem>,
    val encouragement: String,
    val practiceRecommendations: List<String>
)

data class FeedbackItem(
    val id: String = UUID.randomUUID().toString(),
    val type: FeedbackType,
    val word: String,
    val issue: String,
    val correction: String
)

enum class FeedbackType {
    TAJWEED, PRONUNCIATION, TIMING, MELODY
}

enum class TajweedRule(val displayName: String, val description: String, val letters: String) {
    GHUNNAH("Ghunnah", "Nasal sound held for 2 counts", "ن، م"),
    IKHFA("Ikhfa", "Hiding the noon sound", "ت، ث، ج، د، ذ، ز، س، ش، ص، ض، ط، ظ، ف، ق، ك"),
    IDGHAM("Idgham", "Merging two letters", "ي، ر، م، ل، و، ن"),
    IQLAB("Iqlab", "Changing noon to meem", "ب"),
    IZHAR("Izhar", "Clear pronunciation", "ء، ه، ع، ح، غ، خ"),
    QALQALAH("Qalqalah", "Echo sound on specific letters", "ق، ط، ب، ج، د"),
    MADD("Madd", "Elongation of vowel sounds", "ا، و، ي")
}

object AIQuranCoachService {
    
    private val _isAnalyzing = MutableStateFlow(false)
    val isAnalyzing: StateFlow<Boolean> = _isAnalyzing.asStateFlow()
    
    private val _lastFeedback = MutableStateFlow<RecitationFeedback?>(null)
    val lastFeedback: StateFlow<RecitationFeedback?> = _lastFeedback.asStateFlow()
    
    suspend fun analyzeRecitation(surah: String, ayah: Int, isKidsMode: Boolean): RecitationFeedback {
        _isAnalyzing.value = true
        delay(2000) // Simulate AI analysis
        
        val feedback = generateFeedback(surah, ayah, isKidsMode)
        _lastFeedback.value = feedback
        _isAnalyzing.value = false
        
        return feedback
    }
    
    private fun generateFeedback(surah: String, ayah: Int, isKidsMode: Boolean): RecitationFeedback {
        val pronunciationScore = Random.nextInt(70, 96)
        val tajweedScore = Random.nextInt(65, 91)
        val fluencyScore = Random.nextInt(75, 96)
        val overallScore = (pronunciationScore + tajweedScore + fluencyScore) / 3
        
        val feedbackItems = mutableListOf<FeedbackItem>()
        val encouragement: String
        val recommendations: List<String>
        
        if (isKidsMode) {
            if (tajweedScore < 80) {
                feedbackItems.add(FeedbackItem(
                    type = FeedbackType.TAJWEED,
                    word = "بِسْمِ",
                    issue = "Hold the 'meem' sound a little longer 🎵",
                    correction = "Try saying 'Bismiii' with a longer 'i' sound!"
                ))
            }
            
            encouragement = if (overallScore >= 80) {
                "🌟 MashaAllah! You did amazing! Keep practicing and you'll be even better! 🎉"
            } else {
                "💪 Great effort! Practice makes perfect! You're getting better every day! ⭐"
            }
            
            recommendations = listOf(
                "🎧 Listen to the surah 3 times before bed",
                "🗣️ Practice saying each word slowly",
                "🔄 Try the 'repeat after me' game",
                "⭐ Collect stars by practicing every day!"
            )
        } else {
            if (tajweedScore < 80) {
                feedbackItems.add(FeedbackItem(
                    type = FeedbackType.TAJWEED,
                    word = "بِسْمِ",
                    issue = "Ghunnah duration insufficient on the meem",
                    correction = "Hold the nasal sound (ghunnah) for 2 counts (harakatayn)"
                ))
            }
            
            if (pronunciationScore < 85) {
                feedbackItems.add(FeedbackItem(
                    type = FeedbackType.PRONUNCIATION,
                    word = "الرَّحْمَٰنِ",
                    issue = "Ra (ر) articulation point needs attention",
                    correction = "Ensure the tongue tip touches the gum ridge with appropriate tafkheem"
                ))
            }
            
            encouragement = if (overallScore >= 80) {
                "Excellent recitation! Your tajweed application shows good understanding."
            } else {
                "Good effort. Focus on the specific tajweed rules mentioned above."
            }
            
            recommendations = listOf(
                "Review tajweed rules for ghunnah and ikhfa",
                "Practice with audio of a renowned reciter",
                "Record yourself and compare with the original",
                "Focus on makhaarij (articulation points) exercises"
            )
        }
        
        return RecitationFeedback(
            overallScore = overallScore,
            pronunciationScore = pronunciationScore,
            tajweedScore = tajweedScore,
            fluencyScore = fluencyScore,
            feedback = feedbackItems,
            encouragement = encouragement,
            practiceRecommendations = recommendations
        )
    }
    
    fun explainTajweedRule(rule: TajweedRule, isKidsMode: Boolean): String {
        return if (isKidsMode) {
            when (rule) {
                TajweedRule.GHUNNAH -> "🎵 Ghunnah is a humming sound! When you see certain letters, make a sound through your nose like 'mmm' or 'nnn'. Hold it for 2 seconds!"
                TajweedRule.IKHFA -> "🤫 Ikhfa means 'hiding'! We hide the 'n' sound by making it softer and mixing it with the next letter. It's like whispering!"
                TajweedRule.IDGHAM -> "🤝 Idgham means 'merging'! Two letters become friends and join together to make one sound!"
                TajweedRule.IQLAB -> "🔄 Iqlab means 'changing'! The 'n' sound changes to an 'm' sound before the letter 'ba'. It's like magic!"
                TajweedRule.IZHAR -> "📢 Izhar means 'clear'! We say the 'n' sound clearly and strongly before certain letters!"
                TajweedRule.QALQALAH -> "🔔 Qalqalah is an echo sound! Some letters bounce like a ball when they stop - ق ط ب ج د!"
                TajweedRule.MADD -> "➡️ Madd means 'stretching'! We stretch certain sounds longer, like singing a note! It can be 2, 4, or 6 counts!"
            }
        } else {
            """
            ${rule.displayName} (تجويد)
            
            Definition: ${rule.description}
            
            Applicable Letters: ${rule.letters}
            
            This rule is essential for proper Qur'anic recitation.
            """.trimIndent()
        }
    }
}
