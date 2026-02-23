package com.deenlearn.app.services

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

/**
 * AI Tutor Service
 * Provides AI-powered Islamic knowledge Q&A with age-appropriate responses
 */

data class ChatMessage(
    val id: String = UUID.randomUUID().toString(),
    val content: String,
    val isUser: Boolean,
    val timestamp: Date = Date()
)

enum class Topic(val displayName: String) {
    PILLARS("Pillars of Islam"),
    SALAH("Prayer (Salah)"),
    QURAN("Qur'an"),
    ARABIC("Arabic Language"),
    PROPHETS("Prophets & Stories"),
    MANNERS("Islamic Manners"),
    GENERAL("General")
}

object AITutorService {
    
    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> = _isProcessing.asStateFlow()
    
    private val _conversationHistory = MutableStateFlow<List<ChatMessage>>(emptyList())
    val conversationHistory: StateFlow<List<ChatMessage>> = _conversationHistory.asStateFlow()
    
    suspend fun getResponse(question: String, topic: Topic = Topic.GENERAL, isKidsMode: Boolean): String {
        _isProcessing.value = true
        val userMessage = ChatMessage(content = question, isUser = true)
        _conversationHistory.value = _conversationHistory.value + userMessage
        
        delay(1000) // Simulate AI processing
        
        val response = generateResponse(question, topic, isKidsMode)
        val aiMessage = ChatMessage(content = response, isUser = false)
        _conversationHistory.value = _conversationHistory.value + aiMessage
        
        _isProcessing.value = false
        return response
    }
    
    private fun generateResponse(question: String, topic: Topic, isKidsMode: Boolean): String {
        val lowercased = question.lowercase()
        
        return if (isKidsMode) {
            generateKidsResponse(lowercased)
        } else {
            generateAdultResponse(lowercased)
        }
    }
    
    private fun generateKidsResponse(question: String): String {
        return when {
            question.contains("shahada") -> 
                "🌟 The Shahada is like making a promise to Allah! We say: 'There is no god but Allah, and Muhammad is His messenger.' It's the most special words a Muslim says! 💫"
            question.contains("prayer") || question.contains("salah") -> 
                "🕌 Salah is when we talk to Allah 5 times every day! It's like having a special meeting with our best friend. We stand, bow, and put our head on the ground to show Allah how much we love Him! 🤲"
            question.contains("quran") -> 
                "📖 The Qur'an is Allah's special book! It has beautiful words that Allah sent to Prophet Muhammad ﷺ. When we read it, it's like getting a letter from Allah! 💌"
            question.contains("allah") || question.contains("god") -> 
                "✨ Allah is the One who made everything - the sky, the stars, you, and me! Allah loves us more than anyone else and always takes care of us. We can talk to Allah anytime through prayer! 🤲"
            else -> 
                "⭐ That's a great question! Islam teaches us to be kind, honest, and helpful. Allah loves children who ask questions and want to learn more! Keep being curious! 💫"
        }
    }
    
    private fun generateAdultResponse(question: String): String {
        return when {
            question.contains("shahada") -> """
                The Shahada (الشهادة) is the declaration of faith and the first pillar of Islam.
                
                It consists of two parts:
                1. "Lā ilāha illā Allāh" - There is no deity worthy of worship except Allah
                2. "Muḥammadun rasūlu Allāh" - Muhammad is the messenger of Allah
                
                This testimony affirms the oneness of Allah (Tawhid) and the prophethood of Muhammad ﷺ.
                
                Reference: "The Prophet ﷺ said: 'Islam is built upon five pillars...'" (Bukhari & Muslim)
            """.trimIndent()
            
            question.contains("prayer") || question.contains("salah") -> """
                Salah (الصلاة) is the second pillar of Islam consisting of five daily obligatory prayers:
                
                1. Fajr - Dawn prayer (2 rak'at)
                2. Dhuhr - Noon prayer (4 rak'at)
                3. Asr - Afternoon prayer (4 rak'at)
                4. Maghrib - Sunset prayer (3 rak'at)
                5. Isha - Night prayer (4 rak'at)
                
                Prerequisites: ritual purity (wudu), proper intention (niyyah), facing the qiblah.
                
                Reference: "Indeed, prayer has been decreed upon the believers at specified times." (Qur'an 4:103)
            """.trimIndent()
            
            else -> """
                Thank you for your question about Islamic knowledge.
                
                Islam is a comprehensive way of life based on:
                • The Qur'an - The final revelation from Allah
                • The Sunnah - The teachings and practices of Prophet Muhammad ﷺ
                
                The foundation rests on the Five Pillars:
                1. Shahada (Declaration of Faith)
                2. Salah (Prayer)
                3. Zakat (Obligatory Charity)
                4. Sawm (Fasting in Ramadan)
                5. Hajj (Pilgrimage)
            """.trimIndent()
        }
    }
    
    fun clearConversation() {
        _conversationHistory.value = emptyList()
    }
}
