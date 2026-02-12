package com.deenlearn.app.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class ArabicLetter(
    val name: String,
    val transliteration: String,
    val isolated: String,
    val initial: String,
    val medial: String,
    val final: String,
    val pronunciation: String,
    val exampleWord: String,
    val exampleTranslation: String,
    val exampleIcon: String
)

@Serializable
data class VocabularyWord(
    val arabic: String,
    val transliteration: String,
    val english: String,
    val icon: String,
    val category: VocabularyCategory
)

@Serializable
enum class VocabularyCategory {
    SALAH_WORDS,
    MASJID_OBJECTS,
    FAMILY,
    ANIMALS,
    FEELINGS,
    NATURE;
    
    val displayName: String
        get() = when (this) {
            SALAH_WORDS -> "Salah Words"
            MASJID_OBJECTS -> "Masjid Objects"
            FAMILY -> "Family"
            ANIMALS -> "Animals"
            FEELINGS -> "Feelings"
            NATURE -> "Nature"
        }
    
    val icon: String
        get() = when (this) {
            SALAH_WORDS -> "🕌"
            MASJID_OBJECTS -> "📿"
            FAMILY -> "👨‍👩‍👧‍👦"
            ANIMALS -> "🐪"
            FEELINGS -> "😊"
            NATURE -> "🌿"
        }
}

@Serializable
data class ConceptMap(
    val title: String,
    val arabicTitle: String,
    val icon: String,
    val colorHex: String,
    val nodes: List<ConceptNode>,
    val connections: List<ConceptConnection>
)

@Serializable
data class ConceptNode(
    val title: String,
    val arabic: String,
    val icon: String,
    val description: String,
    val positionX: Float,
    val positionY: Float
)

@Serializable
data class ConceptConnection(
    val fromNodeIndex: Int,
    val toNodeIndex: Int,
    val label: String
)

@Serializable
enum class ArabicMiniGameType {
    MATCH_ICON_TO_WORD,
    SOUND_RECOGNITION,
    BUILD_SENTENCE;
    
    val displayName: String
        get() = when (this) {
            MATCH_ICON_TO_WORD -> "Match Icon → Word"
            SOUND_RECOGNITION -> "Sound Recognition"
            BUILD_SENTENCE -> "Build Sentence"
        }
    
    val icon: String
        get() = when (this) {
            MATCH_ICON_TO_WORD -> "🎯"
            SOUND_RECOGNITION -> "🔊"
            BUILD_SENTENCE -> "📝"
        }
    
    val description: String
        get() = when (this) {
            MATCH_ICON_TO_WORD -> "Match the picture to the correct Arabic word"
            SOUND_RECOGNITION -> "Listen and identify the Arabic letter or word"
            BUILD_SENTENCE -> "Arrange words to form a correct sentence"
        }
}

@Serializable
data class MatchGameItem(
    val icon: String,
    val arabic: String,
    val english: String,
    var isMatched: Boolean = false
)

@Serializable
data class SentenceBuildGame(
    val correctSentence: List<String>,
    val translation: String,
    val shuffledWords: List<String>
)

object ArabicData {
    val alphabet = listOf(
        ArabicLetter("Alif", "a", "ا", "ا", "ـا", "ـا", "Like 'a' in 'father'", "أَسَد", "Lion", "🦁"),
        ArabicLetter("Ba", "b", "ب", "بـ", "ـبـ", "ـب", "Like 'b' in 'book'", "بَيْت", "House", "🏠"),
        ArabicLetter("Ta", "t", "ت", "تـ", "ـتـ", "ـت", "Like 't' in 'table'", "تُفَّاحَة", "Apple", "🍎"),
        ArabicLetter("Tha", "th", "ث", "ثـ", "ـثـ", "ـث", "Like 'th' in 'think'", "ثَعْلَب", "Fox", "🦊"),
        ArabicLetter("Jim", "j", "ج", "جـ", "ـجـ", "ـج", "Like 'j' in 'jump'", "جَمَل", "Camel", "🐪"),
        ArabicLetter("Ha", "ḥ", "ح", "حـ", "ـحـ", "ـح", "Breathy 'h' from throat", "حُوت", "Whale", "🐋"),
        ArabicLetter("Kha", "kh", "خ", "خـ", "ـخـ", "ـخ", "Like 'ch' in Scottish 'loch'", "خُبْز", "Bread", "🍞"),
        ArabicLetter("Dal", "d", "د", "د", "ـد", "ـد", "Like 'd' in 'door'", "دُب", "Bear", "🐻"),
        ArabicLetter("Dhal", "dh", "ذ", "ذ", "ـذ", "ـذ", "Like 'th' in 'this'", "ذُبَابَة", "Fly", "🪰"),
        ArabicLetter("Ra", "r", "ر", "ر", "ـر", "ـر", "Rolled 'r'", "رُمَّان", "Pomegranate", "🍎"),
        ArabicLetter("Zay", "z", "ز", "ز", "ـز", "ـز", "Like 'z' in 'zoo'", "زَرَافَة", "Giraffe", "🦒"),
        ArabicLetter("Sin", "s", "س", "سـ", "ـسـ", "ـس", "Like 's' in 'sun'", "سَمَكَة", "Fish", "🐟"),
        ArabicLetter("Shin", "sh", "ش", "شـ", "ـشـ", "ـش", "Like 'sh' in 'ship'", "شَمْس", "Sun", "☀️"),
        ArabicLetter("Sad", "ṣ", "ص", "صـ", "ـصـ", "ـص", "Emphatic 's'", "صَقْر", "Falcon", "🦅"),
        ArabicLetter("Dad", "ḍ", "ض", "ضـ", "ـضـ", "ـض", "Emphatic 'd'", "ضِفْدَع", "Frog", "🐸"),
        ArabicLetter("Ta (emphatic)", "ṭ", "ط", "طـ", "ـطـ", "ـط", "Emphatic 't'", "طَائِر", "Bird", "🐦"),
        ArabicLetter("Dha (emphatic)", "ẓ", "ظ", "ظـ", "ـظـ", "ـظ", "Emphatic 'dh'", "ظَبْي", "Gazelle", "🦌"),
        ArabicLetter("Ayn", "'", "ع", "عـ", "ـعـ", "ـع", "Deep throat sound", "عَيْن", "Eye", "👁️"),
        ArabicLetter("Ghayn", "gh", "غ", "غـ", "ـغـ", "ـغ", "Like French 'r'", "غُرَاب", "Crow", "🐦‍⬛"),
        ArabicLetter("Fa", "f", "ف", "فـ", "ـفـ", "ـف", "Like 'f' in 'fish'", "فِيل", "Elephant", "🐘"),
        ArabicLetter("Qaf", "q", "ق", "قـ", "ـقـ", "ـق", "Deep 'k' from throat", "قَمَر", "Moon", "🌙"),
        ArabicLetter("Kaf", "k", "ك", "كـ", "ـكـ", "ـك", "Like 'k' in 'king'", "كَلْب", "Dog", "🐕"),
        ArabicLetter("Lam", "l", "ل", "لـ", "ـلـ", "ـل", "Like 'l' in 'lamp'", "لَيْمُون", "Lemon", "🍋"),
        ArabicLetter("Mim", "m", "م", "مـ", "ـمـ", "ـم", "Like 'm' in 'moon'", "مَسْجِد", "Mosque", "🕌"),
        ArabicLetter("Nun", "n", "ن", "نـ", "ـنـ", "ـن", "Like 'n' in 'noon'", "نَجْمَة", "Star", "⭐"),
        ArabicLetter("Ha", "h", "ه", "هـ", "ـهـ", "ـه", "Like 'h' in 'house'", "هِلَال", "Crescent", "🌙"),
        ArabicLetter("Waw", "w/ū", "و", "و", "ـو", "ـو", "Like 'w' in 'water' or 'oo' in 'moon'", "وَرْدَة", "Rose", "🌹"),
        ArabicLetter("Ya", "y/ī", "ي", "يـ", "ـيـ", "ـي", "Like 'y' in 'yes' or 'ee' in 'see'", "يَد", "Hand", "✋")
    )
    
    val salahWords = listOf(
        VocabularyWord("صَلَاة", "Salah", "Prayer", "🤲", VocabularyCategory.SALAH_WORDS),
        VocabularyWord("وُضُوء", "Wudu", "Ablution", "💧", VocabularyCategory.SALAH_WORDS),
        VocabularyWord("رُكُوع", "Ruku'", "Bowing", "🙇", VocabularyCategory.SALAH_WORDS),
        VocabularyWord("سُجُود", "Sujud", "Prostration", "🧎", VocabularyCategory.SALAH_WORDS),
        VocabularyWord("قِيَام", "Qiyam", "Standing", "🧍", VocabularyCategory.SALAH_WORDS),
        VocabularyWord("تَشَهُّد", "Tashahhud", "Testimony", "☝️", VocabularyCategory.SALAH_WORDS),
        VocabularyWord("سَلَام", "Salam", "Peace greeting", "👋", VocabularyCategory.SALAH_WORDS)
    )
    
    val masjidObjects = listOf(
        VocabularyWord("مَسْجِد", "Masjid", "Mosque", "🕌", VocabularyCategory.MASJID_OBJECTS),
        VocabularyWord("مِحْرَاب", "Mihrab", "Prayer niche", "🚪", VocabularyCategory.MASJID_OBJECTS),
        VocabularyWord("مِنْبَر", "Minbar", "Pulpit", "🎤", VocabularyCategory.MASJID_OBJECTS),
        VocabularyWord("سَجَّادَة", "Sajjadah", "Prayer mat", "🧶", VocabularyCategory.MASJID_OBJECTS),
        VocabularyWord("مُصْحَف", "Mushaf", "Quran copy", "📖", VocabularyCategory.MASJID_OBJECTS),
        VocabularyWord("سُبْحَة", "Subha", "Prayer beads", "📿", VocabularyCategory.MASJID_OBJECTS)
    )
    
    val familyWords = listOf(
        VocabularyWord("أَب", "Ab", "Father", "👨", VocabularyCategory.FAMILY),
        VocabularyWord("أُم", "Umm", "Mother", "👩", VocabularyCategory.FAMILY),
        VocabularyWord("أَخ", "Akh", "Brother", "👦", VocabularyCategory.FAMILY),
        VocabularyWord("أُخْت", "Ukht", "Sister", "👧", VocabularyCategory.FAMILY),
        VocabularyWord("جَد", "Jadd", "Grandfather", "👴", VocabularyCategory.FAMILY),
        VocabularyWord("جَدَّة", "Jaddah", "Grandmother", "👵", VocabularyCategory.FAMILY),
        VocabularyWord("عَائِلَة", "'A'ilah", "Family", "👨‍👩‍👧‍👦", VocabularyCategory.FAMILY)
    )
    
    val animalWords = listOf(
        VocabularyWord("جَمَل", "Jamal", "Camel", "🐪", VocabularyCategory.ANIMALS),
        VocabularyWord("أَسَد", "Asad", "Lion", "🦁", VocabularyCategory.ANIMALS),
        VocabularyWord("قِط", "Qitt", "Cat", "🐱", VocabularyCategory.ANIMALS),
        VocabularyWord("كَلْب", "Kalb", "Dog", "🐕", VocabularyCategory.ANIMALS),
        VocabularyWord("حِصَان", "Hisan", "Horse", "🐴", VocabularyCategory.ANIMALS),
        VocabularyWord("طَائِر", "Ta'ir", "Bird", "🐦", VocabularyCategory.ANIMALS)
    )
    
    val feelingWords = listOf(
        VocabularyWord("سَعِيد", "Sa'id", "Happy", "😊", VocabularyCategory.FEELINGS),
        VocabularyWord("حَزِين", "Hazin", "Sad", "😢", VocabularyCategory.FEELINGS),
        VocabularyWord("شُكْر", "Shukr", "Grateful", "🙏", VocabularyCategory.FEELINGS),
        VocabularyWord("حُب", "Hubb", "Love", "❤️", VocabularyCategory.FEELINGS),
        VocabularyWord("صَبْر", "Sabr", "Patience", "🧘", VocabularyCategory.FEELINGS)
    )
    
    val natureWords = listOf(
        VocabularyWord("شَمْس", "Shams", "Sun", "☀️", VocabularyCategory.NATURE),
        VocabularyWord("قَمَر", "Qamar", "Moon", "🌙", VocabularyCategory.NATURE),
        VocabularyWord("نَجْمَة", "Najmah", "Star", "⭐", VocabularyCategory.NATURE),
        VocabularyWord("مَاء", "Ma'", "Water", "💧", VocabularyCategory.NATURE),
        VocabularyWord("شَجَرَة", "Shajarah", "Tree", "🌳", VocabularyCategory.NATURE),
        VocabularyWord("زَهْرَة", "Zahrah", "Flower", "🌸", VocabularyCategory.NATURE)
    )
    
    fun wordsForCategory(category: VocabularyCategory): List<VocabularyWord> = when (category) {
        VocabularyCategory.SALAH_WORDS -> salahWords
        VocabularyCategory.MASJID_OBJECTS -> masjidObjects
        VocabularyCategory.FAMILY -> familyWords
        VocabularyCategory.ANIMALS -> animalWords
        VocabularyCategory.FEELINGS -> feelingWords
        VocabularyCategory.NATURE -> natureWords
    }
}
