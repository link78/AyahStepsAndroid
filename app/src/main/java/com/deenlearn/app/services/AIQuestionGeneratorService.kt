package com.deenlearn.app.services

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*

/**
 * AI Question Generator Service
 * Generates dynamic quiz questions based on topic and difficulty
 */

data class QuizQuestion(
    val id: String = UUID.randomUUID().toString(),
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String,
    val category: QuestionCategory,
    val difficulty: QuestionDifficulty,
    val points: Int
)

enum class QuestionCategory(val displayName: String) {
    PILLARS("Pillars of Islam"),
    SALAH("Prayer"),
    QURAN("Qur'an"),
    ARABIC("Arabic"),
    PROPHETS("Prophets"),
    MANNERS("Islamic Manners")
}

enum class QuestionDifficulty(val points: Int) {
    EASY(10),
    MEDIUM(20),
    HARD(30)
}

object AIQuestionGeneratorService {
    
    private val _currentQuiz = MutableStateFlow<List<QuizQuestion>>(emptyList())
    val currentQuiz: StateFlow<List<QuizQuestion>> = _currentQuiz.asStateFlow()
    
    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating.asStateFlow()
    
    suspend fun generateQuiz(
        category: QuestionCategory,
        count: Int,
        difficulty: QuestionDifficulty,
        isKidsMode: Boolean
    ): List<QuizQuestion> {
        _isGenerating.value = true
        delay(500)
        
        val questions = getQuestionsForCategory(category, count, difficulty, isKidsMode)
        _currentQuiz.value = questions
        _isGenerating.value = false
        
        return questions
    }
    
    suspend fun generateRandomQuiz(count: Int, isKidsMode: Boolean): List<QuizQuestion> {
        _isGenerating.value = true
        delay(500)
        
        val allQuestions = QuestionCategory.values().flatMap { category ->
            if (isKidsMode) getKidsQuestions(category) else getAdultQuestions(category)
        }
        
        val questions = allQuestions.shuffled().take(count)
        _currentQuiz.value = questions
        _isGenerating.value = false
        
        return questions
    }
    
    private fun getQuestionsForCategory(
        category: QuestionCategory,
        count: Int,
        difficulty: QuestionDifficulty,
        isKidsMode: Boolean
    ): List<QuizQuestion> {
        val allQuestions = if (isKidsMode) getKidsQuestions(category) else getAdultQuestions(category)
        return allQuestions.shuffled().take(count)
    }
    
    private fun getKidsQuestions(category: QuestionCategory): List<QuizQuestion> {
        return when (category) {
            QuestionCategory.PILLARS -> listOf(
                QuizQuestion(
                    question = "🌟 How many pillars of Islam are there?",
                    options = listOf("3️⃣ Three", "5️⃣ Five", "7️⃣ Seven", "4️⃣ Four"),
                    correctAnswerIndex = 1,
                    explanation = "There are 5 pillars of Islam! They are like 5 strong pillars holding up a beautiful building! 🏛️",
                    category = QuestionCategory.PILLARS,
                    difficulty = QuestionDifficulty.EASY,
                    points = 10
                ),
                QuizQuestion(
                    question = "🕌 What is the first pillar of Islam?",
                    options = listOf("Prayer", "Shahada", "Fasting", "Hajj"),
                    correctAnswerIndex = 1,
                    explanation = "The Shahada is saying 'There is no god but Allah, and Muhammad is His messenger!' 💫",
                    category = QuestionCategory.PILLARS,
                    difficulty = QuestionDifficulty.EASY,
                    points = 10
                )
            )
            
            QuestionCategory.SALAH -> listOf(
                QuizQuestion(
                    question = "🌅 How many times do Muslims pray every day?",
                    options = listOf("3 times", "5 times", "7 times", "2 times"),
                    correctAnswerIndex = 1,
                    explanation = "Muslims pray 5 times a day! Fajr, Dhuhr, Asr, Maghrib, and Isha! 🕌",
                    category = QuestionCategory.SALAH,
                    difficulty = QuestionDifficulty.EASY,
                    points = 10
                )
            )
            
            QuestionCategory.QURAN -> listOf(
                QuizQuestion(
                    question = "📖 What is the holy book of Islam?",
                    options = listOf("Bible", "Qur'an", "Torah", "Vedas"),
                    correctAnswerIndex = 1,
                    explanation = "The Qur'an is Allah's special book sent to Prophet Muhammad ﷺ! 📖",
                    category = QuestionCategory.QURAN,
                    difficulty = QuestionDifficulty.EASY,
                    points = 10
                )
            )
            
            QuestionCategory.ARABIC -> listOf(
                QuizQuestion(
                    question = "🔤 How many letters are in the Arabic alphabet?",
                    options = listOf("26", "28", "30", "24"),
                    correctAnswerIndex = 1,
                    explanation = "The Arabic alphabet has 28 letters! Each one has a special sound! 🔤",
                    category = QuestionCategory.ARABIC,
                    difficulty = QuestionDifficulty.EASY,
                    points = 10
                )
            )
            
            QuestionCategory.PROPHETS -> listOf(
                QuizQuestion(
                    question = "🌟 Who is the last prophet in Islam?",
                    options = listOf("Prophet Isa", "Prophet Musa", "Prophet Muhammad ﷺ", "Prophet Ibrahim"),
                    correctAnswerIndex = 2,
                    explanation = "Prophet Muhammad ﷺ is the last and final prophet! 💚",
                    category = QuestionCategory.PROPHETS,
                    difficulty = QuestionDifficulty.EASY,
                    points = 10
                )
            )
            
            QuestionCategory.MANNERS -> listOf(
                QuizQuestion(
                    question = "🍽️ What do we say before eating?",
                    options = listOf("Alhamdulillah", "Bismillah", "SubhanAllah", "MashaAllah"),
                    correctAnswerIndex = 1,
                    explanation = "We say 'Bismillah' (In the name of Allah) before eating! 🍽️",
                    category = QuestionCategory.MANNERS,
                    difficulty = QuestionDifficulty.EASY,
                    points = 10
                )
            )
        }
    }
    
    private fun getAdultQuestions(category: QuestionCategory): List<QuizQuestion> {
        return when (category) {
            QuestionCategory.PILLARS -> listOf(
                QuizQuestion(
                    question = "What is the minimum amount of wealth (Nisab) required for Zakat on gold?",
                    options = listOf("75 grams", "85 grams", "95 grams", "100 grams"),
                    correctAnswerIndex = 1,
                    explanation = "The Nisab for gold is approximately 85 grams, after which 2.5% Zakat becomes obligatory.",
                    category = QuestionCategory.PILLARS,
                    difficulty = QuestionDifficulty.HARD,
                    points = 30
                )
            )
            
            QuestionCategory.SALAH -> listOf(
                QuizQuestion(
                    question = "What is the minimum number of rak'at for Witr prayer?",
                    options = listOf("1 rak'ah", "2 rak'at", "3 rak'at", "5 rak'at"),
                    correctAnswerIndex = 0,
                    explanation = "The minimum for Witr is 1 rak'ah, though 3 or more (odd number) is preferred.",
                    category = QuestionCategory.SALAH,
                    difficulty = QuestionDifficulty.MEDIUM,
                    points = 20
                )
            )
            
            QuestionCategory.QURAN -> listOf(
                QuizQuestion(
                    question = "How many years did the revelation of the Qur'an span?",
                    options = listOf("10 years", "13 years", "23 years", "25 years"),
                    correctAnswerIndex = 2,
                    explanation = "The Qur'an was revealed over approximately 23 years.",
                    category = QuestionCategory.QURAN,
                    difficulty = QuestionDifficulty.MEDIUM,
                    points = 20
                )
            )
            
            QuestionCategory.ARABIC -> listOf(
                QuizQuestion(
                    question = "What is the term for the short vowel marks in Arabic?",
                    options = listOf("Sukun", "Harakat", "Tanwin", "Shadda"),
                    correctAnswerIndex = 1,
                    explanation = "Harakat (حركات) refers to the short vowel marks: fatha, kasra, and damma.",
                    category = QuestionCategory.ARABIC,
                    difficulty = QuestionDifficulty.MEDIUM,
                    points = 20
                )
            )
            
            QuestionCategory.PROPHETS -> listOf(
                QuizQuestion(
                    question = "According to hadith, approximately how many prophets were sent?",
                    options = listOf("25", "124,000", "313", "1,000"),
                    correctAnswerIndex = 1,
                    explanation = "According to hadith, approximately 124,000 prophets were sent, with 313 being messengers.",
                    category = QuestionCategory.PROPHETS,
                    difficulty = QuestionDifficulty.HARD,
                    points = 30
                )
            )
            
            QuestionCategory.MANNERS -> listOf(
                QuizQuestion(
                    question = "What should a Muslim say when entering a home?",
                    options = listOf("Assalamu Alaikum", "Bismillah", "Alhamdulillah", "SubhanAllah"),
                    correctAnswerIndex = 0,
                    explanation = "Muslims should say 'Assalamu Alaikum' (Peace be upon you) when entering.",
                    category = QuestionCategory.MANNERS,
                    difficulty = QuestionDifficulty.EASY,
                    points = 10
                )
            )
        }
    }
}
