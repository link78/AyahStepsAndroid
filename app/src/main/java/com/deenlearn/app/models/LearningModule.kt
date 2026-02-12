package com.deenlearn.app.models

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable

@Serializable
data class LearningModule(
    val id: String,
    val title: String,
    val titleArabic: String,
    val description: String,
    val icon: String,
    val colorHex: String,
    val lessons: List<Lesson>,
    val forKids: Boolean,
    val forAdults: Boolean
)

@Serializable
data class Lesson(
    val id: String,
    val title: String,
    val description: String,
    val content: LessonContent,
    val duration: Int
)

@Serializable
sealed class LessonContent {
    @Serializable
    data class Text(val text: String) : LessonContent()
    
    @Serializable
    data class Steps(val steps: List<LessonStep>) : LessonContent()
    
    @Serializable
    data class Quran(val lesson: QuranLesson) : LessonContent()
}

@Serializable
data class LessonStep(
    val id: String,
    val stepNumber: Int,
    val title: String,
    val description: String,
    val imageName: String?
)

@Serializable
data class QuranLesson(
    val surahNumber: Int,
    val surahName: String,
    val surahNameArabic: String,
    val verses: List<QuranVerse>
)

@Serializable
data class QuranVerse(
    val id: Int,
    val arabic: String,
    val transliteration: String,
    val translation: String
)
