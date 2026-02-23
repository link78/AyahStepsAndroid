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
    val exampleIcon: String,
    val tracingStrokes: Int = 1,
    val tracingPoints: List<TracingPoint> = emptyList()
)

@Serializable
data class TracingPoint(
    val x: Float,
    val y: Float,
    val isControl: Boolean = false
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
    
    // Concept Maps for Visual Learning
    val salahConceptMap = ConceptMap(
        title = "Salah - Islamic Prayer",
        arabicTitle = "الصلاة",
        icon = "🕌",
        colorHex = "#4CAF50",
        nodes = listOf(
            ConceptNode("Salah", "صَلَاة", "🤲", "The five daily prayers", 0.5f, 0.1f),
            ConceptNode("Fajr", "فَجْر", "🌅", "Dawn prayer - 2 rakats", 0.2f, 0.3f),
            ConceptNode("Dhuhr", "ظُهْر", "☀️", "Noon prayer - 4 rakats", 0.5f, 0.3f),
            ConceptNode("Asr", "عَصْر", "🌤️", "Afternoon prayer - 4 rakats", 0.8f, 0.3f),
            ConceptNode("Maghrib", "مَغْرِب", "🌆", "Sunset prayer - 3 rakats", 0.35f, 0.5f),
            ConceptNode("Isha", "عِشَاء", "🌙", "Night prayer - 4 rakats", 0.65f, 0.5f),
            ConceptNode("Qiyam", "قِيَام", "🧍", "Standing position", 0.2f, 0.7f),
            ConceptNode("Ruku", "رُكُوع", "🙇", "Bowing position", 0.4f, 0.7f),
            ConceptNode("Sujud", "سُجُود", "🧎", "Prostration", 0.6f, 0.7f),
            ConceptNode("Tashahhud", "تَشَهُّد", "☝️", "Testimony position", 0.8f, 0.7f),
            ConceptNode("Takbir", "تَكْبِير", "🙌", "Allahu Akbar", 0.3f, 0.9f),
            ConceptNode("Taslim", "تَسْلِيم", "👋", "Peace greeting - Assalamu Alaikum", 0.7f, 0.9f)
        ),
        connections = listOf(
            ConceptConnection(0, 1, "includes"),
            ConceptConnection(0, 2, "includes"),
            ConceptConnection(0, 3, "includes"),
            ConceptConnection(0, 4, "includes"),
            ConceptConnection(0, 5, "includes"),
            ConceptConnection(1, 6, "starts with"),
            ConceptConnection(6, 7, "then"),
            ConceptConnection(7, 8, "then"),
            ConceptConnection(8, 9, "ends with"),
            ConceptConnection(6, 10, "begins with"),
            ConceptConnection(9, 11, "ends with")
        )
    )
    
    val wuduConceptMap = ConceptMap(
        title = "Wudu - Ablution",
        arabicTitle = "الوُضُوء",
        icon = "💧",
        colorHex = "#2196F3",
        nodes = listOf(
            ConceptNode("Wudu", "وُضُوء", "💧", "Ritual purification before prayer", 0.5f, 0.1f),
            ConceptNode("Intention", "نِيَّة", "💭", "Niyyah - Mental intention", 0.5f, 0.25f),
            ConceptNode("Bismillah", "بِسْمِ اللّٰهِ", "🤲", "Say: In the name of Allah", 0.5f, 0.35f),
            ConceptNode("Wash Hands", "غَسْلُ الْيَدَيْنِ", "✋", "Wash both hands 3 times", 0.25f, 0.5f),
            ConceptNode("Rinse Mouth", "مَضْمَضَة", "👄", "Rinse mouth 3 times", 0.4f, 0.55f),
            ConceptNode("Rinse Nose", "اسْتِنْشَاق", "👃", "Sniff water into nose 3 times", 0.6f, 0.55f),
            ConceptNode("Wash Face", "غَسْلُ الْوَجْهِ", "😊", "Wash face 3 times", 0.75f, 0.5f),
            ConceptNode("Wash Arms", "غَسْلُ الذِّرَاعَيْنِ", "💪", "Wash arms to elbows 3 times", 0.2f, 0.7f),
            ConceptNode("Wipe Head", "مَسْحُ الرَّأْسِ", "👨", "Wipe head once", 0.4f, 0.7f),
            ConceptNode("Wipe Ears", "مَسْحُ الْأُذُنَيْنِ", "👂", "Wipe inside and outside ears", 0.6f, 0.7f),
            ConceptNode("Wash Feet", "غَسْلُ الرِّجْلَيْنِ", "🦶", "Wash feet to ankles 3 times", 0.8f, 0.7f),
            ConceptNode("Dua", "دُعَاء", "🤲", "Supplication after wudu", 0.5f, 0.9f)
        ),
        connections = listOf(
            ConceptConnection(0, 1, "starts with"),
            ConceptConnection(1, 2, "then say"),
            ConceptConnection(2, 3, "step 1"),
            ConceptConnection(3, 4, "step 2"),
            ConceptConnection(4, 5, "step 3"),
            ConceptConnection(5, 6, "step 4"),
            ConceptConnection(6, 7, "step 5"),
            ConceptConnection(7, 8, "step 6"),
            ConceptConnection(8, 9, "step 7"),
            ConceptConnection(9, 10, "step 8"),
            ConceptConnection(10, 11, "ends with")
        )
    )
    
    val ramadanConceptMap = ConceptMap(
        title = "Ramadan - Holy Month",
        arabicTitle = "رَمَضَان",
        icon = "🌙",
        colorHex = "#9C27B0",
        nodes = listOf(
            ConceptNode("Ramadan", "رَمَضَان", "🌙", "The blessed month of fasting", 0.5f, 0.1f),
            ConceptNode("Sawm", "صَوْم", "🌙", "Fasting from dawn to sunset", 0.5f, 0.25f),
            ConceptNode("Suhoor", "سُحُور", "🍽️", "Pre-dawn meal before fasting", 0.2f, 0.4f),
            ConceptNode("Imsak", "إِمْسَاك", "🌅", "Time to stop eating - dawn", 0.35f, 0.5f),
            ConceptNode("Fasting", "صِيَام", "⏰", "Abstaining from food, drink, etc.", 0.5f, 0.5f),
            ConceptNode("Iftar", "إِفْطَار", "🥛", "Breaking fast at sunset", 0.65f, 0.5f),
            ConceptNode("Dates", "تَمْر", "🌴", "Sunnah to break fast with dates", 0.8f, 0.4f),
            ConceptNode("Taraweeh", "تَرَاوِيح", "🕌", "Night prayer in Ramadan", 0.2f, 0.7f),
            ConceptNode("Quran", "قُرْآن", "📖", "Month of Quran revelation", 0.4f, 0.7f),
            ConceptNode("Laylatul Qadr", "لَيْلَةُ الْقَدْرِ", "⭐", "Night of Power - better than 1000 months", 0.6f, 0.7f),
            ConceptNode("Zakat", "زَكَاة", "💝", "Charity - especially Zakat al-Fitr", 0.8f, 0.7f),
            ConceptNode("Eid", "عِيد", "🎉", "Celebration after Ramadan", 0.5f, 0.9f)
        ),
        connections = listOf(
            ConceptConnection(0, 1, "is about"),
            ConceptConnection(1, 2, "starts with"),
            ConceptConnection(2, 3, "until"),
            ConceptConnection(3, 4, "begins"),
            ConceptConnection(4, 5, "ends with"),
            ConceptConnection(5, 6, "with"),
            ConceptConnection(1, 7, "includes"),
            ConceptConnection(0, 8, "revealed in"),
            ConceptConnection(0, 9, "special night"),
            ConceptConnection(0, 10, "give"),
            ConceptConnection(0, 11, "ends with")
        )
    )
    
    // Mini-Game Data Sets
    val matchIconToWordGames = listOf(
        // Salah vocabulary matching
        MatchGameItem("🤲", "صَلَاة", "Prayer"),
        MatchGameItem("🌅", "فَجْر", "Fajr"),
        MatchGameItem("☀️", "ظُهْر", "Dhuhr"),
        MatchGameItem("🌤️", "عَصْر", "Asr"),
        MatchGameItem("🌆", "مَغْرِب", "Maghrib"),
        MatchGameItem("🌙", "عِشَاء", "Isha"),
        MatchGameItem("🙇", "رُكُوع", "Ruku"),
        MatchGameItem("🧎", "سُجُود", "Sujud"),
        // Animals
        MatchGameItem("🐪", "جَمَل", "Camel"),
        MatchGameItem("🦁", "أَسَد", "Lion"),
        MatchGameItem("🐱", "قِط", "Cat"),
        MatchGameItem("🐕", "كَلْب", "Dog"),
        MatchGameItem("🐴", "حِصَان", "Horse"),
        MatchGameItem("🐦", "طَائِر", "Bird"),
        // Family
        MatchGameItem("👨", "أَب", "Father"),
        MatchGameItem("👩", "أُم", "Mother"),
        MatchGameItem("👦", "أَخ", "Brother"),
        MatchGameItem("👧", "أُخْت", "Sister"),
        // Nature
        MatchGameItem("☀️", "شَمْس", "Sun"),
        MatchGameItem("🌙", "قَمَر", "Moon"),
        MatchGameItem("⭐", "نَجْمَة", "Star"),
        MatchGameItem("💧", "مَاء", "Water"),
        MatchGameItem("🌳", "شَجَرَة", "Tree"),
        MatchGameItem("🌸", "زَهْرَة", "Flower"),
        // Feelings
        MatchGameItem("😊", "سَعِيد", "Happy"),
        MatchGameItem("😢", "حَزِين", "Sad"),
        MatchGameItem("❤️", "حُب", "Love"),
        // Masjid objects
        MatchGameItem("🕌", "مَسْجِد", "Mosque"),
        MatchGameItem("📖", "مُصْحَف", "Quran"),
        MatchGameItem("📿", "سُبْحَة", "Prayer beads")
    )
    
    val sentenceBuildingGames = listOf(
        SentenceBuildGame(
            correctSentence = listOf("أَنَا", "مُسْلِم"),
            translation = "I am a Muslim",
            shuffledWords = listOf("مُسْلِم", "أَنَا")
        ),
        SentenceBuildGame(
            correctSentence = listOf("أَنَا", "أُحِبُّ", "اللهَ"),
            translation = "I love Allah",
            shuffledWords = listOf("اللهَ", "أَنَا", "أُحِبُّ")
        ),
        SentenceBuildGame(
            correctSentence = listOf("الصَّلَاةُ", "مُهِمَّة"),
            translation = "Prayer is important",
            shuffledWords = listOf("مُهِمَّة", "الصَّلَاةُ")
        ),
        SentenceBuildGame(
            correctSentence = listOf("أَنَا", "أَقْرَأُ", "الْقُرْآنَ"),
            translation = "I read the Quran",
            shuffledWords = listOf("الْقُرْآنَ", "أَقْرَأُ", "أَنَا")
        ),
        SentenceBuildGame(
            correctSentence = listOf("هٰذَا", "مَسْجِد"),
            translation = "This is a mosque",
            shuffledWords = listOf("مَسْجِد", "هٰذَا")
        ),
        SentenceBuildGame(
            correctSentence = listOf("الْحَمْدُ", "لِلّٰهِ"),
            translation = "Praise be to Allah",
            shuffledWords = listOf("لِلّٰهِ", "الْحَمْدُ")
        ),
        SentenceBuildGame(
            correctSentence = listOf("أُمِّي", "طَيِّبَة"),
            translation = "My mother is kind",
            shuffledWords = listOf("طَيِّبَة", "أُمِّي")
        ),
        SentenceBuildGame(
            correctSentence = listOf("أَبِي", "قَوِيّ"),
            translation = "My father is strong",
            shuffledWords = listOf("قَوِيّ", "أَبِي")
        ),
        SentenceBuildGame(
            correctSentence = listOf("الشَّمْسُ", "جَمِيلَة"),
            translation = "The sun is beautiful",
            shuffledWords = listOf("جَمِيلَة", "الشَّمْسُ")
        ),
        SentenceBuildGame(
            correctSentence = listOf("أَنَا", "أَكْتُبُ", "بِالْعَرَبِيَّةِ"),
            translation = "I write in Arabic",
            shuffledWords = listOf("بِالْعَرَبِيَّةِ", "أَنَا", "أَكْتُبُ")
        )
    )
    
    val soundRecognitionWords = listOf(
        // Start with basic letters
        VocabularyWord("أ", "Alif", "Letter Alif", "📝", VocabularyCategory.SALAH_WORDS),
        VocabularyWord("ب", "Ba", "Letter Ba", "📝", VocabularyCategory.SALAH_WORDS),
        VocabularyWord("ت", "Ta", "Letter Ta", "📝", VocabularyCategory.SALAH_WORDS),
        // Then simple words
        VocabularyWord("نَعَم", "Na'am", "Yes", "✅", VocabularyCategory.FEELINGS),
        VocabularyWord("لَا", "La", "No", "❌", VocabularyCategory.FEELINGS),
        VocabularyWord("شُكْرًا", "Shukran", "Thank you", "🙏", VocabularyCategory.FEELINGS),
        VocabularyWord("سَلَام", "Salam", "Peace", "👋", VocabularyCategory.SALAH_WORDS),
        VocabularyWord("مَاء", "Ma'", "Water", "💧", VocabularyCategory.NATURE)
    )
    
    fun getConceptMap(title: String): ConceptMap? = when (title.lowercase()) {
        "salah", "prayer" -> salahConceptMap
        "wudu", "ablution" -> wuduConceptMap
        "ramadan", "fasting" -> ramadanConceptMap
        else -> null
    }
    
    fun getAllConceptMaps() = listOf(salahConceptMap, wuduConceptMap, ramadanConceptMap)
    
    fun getGameItems(gameType: ArabicMiniGameType): List<Any> = when (gameType) {
        ArabicMiniGameType.MATCH_ICON_TO_WORD -> matchIconToWordGames
        ArabicMiniGameType.SOUND_RECOGNITION -> soundRecognitionWords
        ArabicMiniGameType.BUILD_SENTENCE -> sentenceBuildingGames
    }
}

// Adult Quranic Arabic Content

@Serializable
data class QuranicWord(
    val arabic: String,
    val transliteration: String,
    val englishMeaning: String,
    val root: String,
    val rootMeaning: String,
    val wordForm: ArabicWordForm,
    val occurrencesInQuran: Int,
    val exampleVerses: List<VerseExample>,
    val grammaticalNotes: String,
    val derivedWords: List<String>
)

@Serializable
enum class ArabicWordForm {
    FORM_I,    // فَعَلَ
    FORM_II,   // فَعَّلَ
    FORM_III,  // فَاعَلَ
    FORM_IV,   // أَفْعَلَ
    FORM_V,    // تَفَعَّلَ
    FORM_VI,   // تَفَاعَلَ
    FORM_VII,  // انْفَعَلَ
    FORM_VIII, // افْتَعَلَ
    FORM_IX,   // افْعَلَّ
    FORM_X;    // اسْتَفْعَلَ
    
    val displayName: String
        get() = when (this) {
            FORM_I -> "Form I (Basic)"
            FORM_II -> "Form II (Intensive)"
            FORM_III -> "Form III (Mutual)"
            FORM_IV -> "Form IV (Causative)"
            FORM_V -> "Form V (Reflexive Intensive)"
            FORM_VI -> "Form VI (Reflexive Mutual)"
            FORM_VII -> "Form VII (Passive)"
            FORM_VIII -> "Form VIII (Reflexive)"
            FORM_IX -> "Form IX (Colors/Defects)"
            FORM_X -> "Form X (Seeking)"
        }
    
    val pattern: String
        get() = when (this) {
            FORM_I -> "فَعَلَ"
            FORM_II -> "فَعَّلَ"
            FORM_III -> "فَاعَلَ"
            FORM_IV -> "أَفْعَلَ"
            FORM_V -> "تَفَعَّلَ"
            FORM_VI -> "تَفَاعَلَ"
            FORM_VII -> "انْفَعَلَ"
            FORM_VIII -> "افْتَعَلَ"
            FORM_IX -> "افْعَلَّ"
            FORM_X -> "اسْتَفْعَلَ"
        }
}

@Serializable
data class VerseExample(
    val surahNumber: Int,
    val surahName: String,
    val ayahNumber: Int,
    val arabicText: String,
    val translation: String
)

@Serializable
data class GrammarLesson(
    val id: String,
    val title: String,
    val titleArabic: String,
    val category: GrammarCategory,
    val level: LanguageLevel,
    val explanation: String,
    val rules: List<String>,
    val examples: List<GrammarExample>,
    val quranicApplications: List<String>,
    val commonMistakes: List<String>,
    val practiceExercises: List<String>
)

@Serializable
enum class GrammarCategory {
    NOUN_CASES,
    VERB_CONJUGATION,
    SENTENCE_STRUCTURE,
    PARTICLES,
    PRONOUNS,
    DUAL_PLURAL,
    GENDER,
    DEFINITENESS;
    
    val displayName: String
        get() = when (this) {
            NOUN_CASES -> "Noun Cases (I'rab)"
            VERB_CONJUGATION -> "Verb Conjugation"
            SENTENCE_STRUCTURE -> "Sentence Structure"
            PARTICLES -> "Particles (Huruf)"
            PRONOUNS -> "Pronouns"
            DUAL_PLURAL -> "Dual & Plural Forms"
            GENDER -> "Masculine & Feminine"
            DEFINITENESS -> "Definite & Indefinite"
        }
}

@Serializable
enum class LanguageLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED;
    
    val displayName: String
        get() = when (this) {
            BEGINNER -> "Beginner"
            INTERMEDIATE -> "Intermediate"
            ADVANCED -> "Advanced"
        }
}

@Serializable
data class GrammarExample(
    val arabic: String,
    val transliteration: String,
    val english: String,
    val breakdown: String
)

@Serializable
data class RootFamily(
    val root: String,
    val meaning: String,
    val pattern: String,
    val derivatives: List<Derivative>
)

@Serializable
data class Derivative(
    val word: String,
    val meaning: String,
    val wordType: String,
    val usage: String
)

object AdultArabicContent {
    
    // 200 Most Common Quranic Words (80% coverage)
    val mostCommonQuranicWords = listOf(
        QuranicWord(
            arabic = "اللَّهُ",
            transliteration = "Allah",
            englishMeaning = "Allah, God",
            root = "إله",
            rootMeaning = "deity, god",
            wordForm = ArabicWordForm.FORM_I,
            occurrencesInQuran = 2699,
            exampleVerses = listOf(
                VerseExample(
                    1, "Al-Fatiha", 2,
                    "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ",
                    "All praise is due to Allah, Lord of the worlds"
                )
            ),
            grammaticalNotes = "Proper noun, always definite. Subject of countless verses. The most frequently occurring word in the Quran.",
            derivedWords = listOf("إِلَهٌ (god)", "أَأَلَهَ (to worship)")
        ),
        QuranicWord(
            arabic = "قَالَ",
            transliteration = "Qala",
            englishMeaning = "He said",
            root = "قول",
            rootMeaning = "to say, speak",
            wordForm = ArabicWordForm.FORM_I,
            occurrencesInQuran = 1722,
            exampleVerses = listOf(
                VerseExample(
                    2, "Al-Baqarah", 30,
                    "وَإِذْ قَالَ رَبُّكَ لِلْمَلَائِكَةِ",
                    "And when your Lord said to the angels..."
                )
            ),
            grammaticalNotes = "Past tense verb, 3rd person masculine singular. Used extensively in narratives.",
            derivedWords = listOf("قَوْلٌ (statement)", "مَقَالٌ (saying)", "قِيلَ (it was said)")
        ),
        QuranicWord(
            arabic = "الَّذِي",
            transliteration = "Alladhi",
            englishMeaning = "The one who, that which",
            root = "ذو",
            rootMeaning = "possessor, owner",
            wordForm = ArabicWordForm.FORM_I,
            occurrencesInQuran = 1464,
            exampleVerses = listOf(
                VerseExample(
                    2, "Al-Baqarah", 21,
                    "يَا أَيُّهَا النَّاسُ اعْبُدُوا رَبَّكُمُ الَّذِي خَلَقَكُمْ",
                    "O mankind, worship your Lord, who created you"
                )
            ),
            grammaticalNotes = "Relative pronoun, masculine singular. Essential for relative clauses.",
            derivedWords = listOf("الَّتِي (feminine)", "الَّذِينَ (plural masculine)", "اللَّاتِي (plural feminine)")
        ),
        QuranicWord(
            arabic = "رَبّ",
            transliteration = "Rabb",
            englishMeaning = "Lord, Master, Sustainer",
            root = "ربب",
            rootMeaning = "to nurture, sustain, rule",
            wordForm = ArabicWordForm.FORM_I,
            occurrencesInQuran = 980,
            exampleVerses = listOf(
                VerseExample(
                    1, "Al-Fatiha", 2,
                    "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ",
                    "All praise is due to Allah, Lord of the worlds"
                )
            ),
            grammaticalNotes = "Noun, often in construct state (Idafa). One of Allah's primary attributes.",
            derivedWords = listOf("رَبَّانِيّ (divine)", "مُرَبِّي (nurturer)", "تَرْبِيَة (upbringing)")
        ),
        QuranicWord(
            arabic = "إِنَّ",
            transliteration = "Inna",
            englishMeaning = "Indeed, verily, truly",
            root = "أنن",
            rootMeaning = "emphasis particle",
            wordForm = ArabicWordForm.FORM_I,
            occurrencesInQuran = 4027,
            exampleVerses = listOf(
                VerseExample(
                    2, "Al-Baqarah", 2,
                    "ذَٰلِكَ الْكِتَابُ لَا رَيْبَ فِيهِ ۛ هُدًى لِّلْمُتَّقِينَ",
                    "That is the Book about which there is no doubt"
                )
            ),
            grammaticalNotes = "Emphasis particle that makes the following noun accusative (منصوب). Changes sentence structure.",
            derivedWords = listOf("أَنَّ (that, indeed)", "لَكِنَّ (but, however)", "كَأَنَّ (as if)")
        )
    )
    
    // Grammar Lessons
    val grammarLessons = listOf(
        GrammarLesson(
            id = "grammar_1",
            title = "The Three Cases (I'rab)",
            titleArabic = "الإعراب الثلاثة",
            category = GrammarCategory.NOUN_CASES,
            level = LanguageLevel.BEGINNER,
            explanation = """
                Arabic nouns have three grammatical cases that indicate their function in a sentence:
                
                1. **Nominative (Raf')**  - Subject of sentence
                2. **Accusative (Nasb)** - Object of verb or certain particles
                3. **Genitive (Jarr)** - After prepositions or in possessive constructions
                
                Each case has specific ending markers that change based on whether the noun is:
                - Definite or indefinite
                - Singular, dual, or plural
                - Masculine or feminine
                
                Understanding cases is crucial for reading the Quran correctly and understanding sentence relationships.
            """.trimIndent(),
            rules = listOf(
                "Nominative (Raf'): Ends with Dammah (ُ) for singular, Alif-Nun for dual, Waw-Nun for masculine plural",
                "Accusative (Nasb): Ends with Fathah (َ) for singular, Ya-Nun for dual, Ya-Nun for masculine plural",
                "Genitive (Jarr): Ends with Kasrah (ِ) for singular, Ya-Nun for dual, Ya-Nun for masculine plural",
                "Feminine sound plural: -aat (ات) in all three cases with different vowels",
                "Definite nouns use the case marker only, indefinite add Tanween (double vowels)"
            ),
            examples = listOf(
                GrammarExample(
                    "جَاءَ الرَّجُلُ",
                    "Ja'a ar-rajulu",
                    "The man came",
                    "ar-rajulu (الرَّجُلُ) is nominative because it's the subject"
                ),
                GrammarExample(
                    "رَأَيْتُ الرَّجُلَ",
                    "Ra'aytu ar-rajula",
                    "I saw the man",
                    "ar-rajula (الرَّجُلَ) is accusative because it's the object"
                ),
                GrammarExample(
                    "ذَهَبْتُ مَعَ الرَّجُلِ",
                    "Dhahabtu ma'a ar-rajuli",
                    "I went with the man",
                    "ar-rajuli (الرَّجُلِ) is genitive after the preposition ma'a (with)"
                )
            ),
            quranicApplications = listOf(
                "Al-Fatiha (1:2): الْحَمْدُ لِلَّهِ - Al-hamdu (nominative) is the subject",
                "Al-Baqarah (2:21): اعْبُدُوا رَبَّكُمُ - Rabbakum (accusative) is the object of worship",
                "An-Nas (114:3): إِلَٰهِ النَّاسِ - An-naas (genitive) after Idafa construction"
            ),
            commonMistakes = listOf(
                "Ignoring case endings when reciting Quran - changes meaning!",
                "Not recognizing when a noun is in construct state (Idafa)",
                "Confusing dual and plural endings",
                "Forgetting that prepositions always take genitive case"
            ),
            practiceExercises = listOf(
                "Identify the case of each noun in Al-Fatiha",
                "Change a simple sentence from nominative to accusative",
                "Find three examples of genitive case in Surah Al-Ikhlas",
                "Practice writing your name with different case endings"
            )
        ),
        GrammarLesson(
            id = "grammar_2",
            title = "Verb Forms (Patterns)",
            titleArabic = "أوزان الفعل",
            category = GrammarCategory.VERB_CONJUGATION,
            level = LanguageLevel.INTERMEDIATE,
            explanation = """
                Arabic verbs are built on root letters (usually 3) that carry core meaning, with patterns (forms) adding nuanced meanings:
                
                **Form I** (Basic): Core meaning - كَتَبَ (to write)
                **Form II** (Intensive): Intensification/causation - كَتَّبَ (to make write frequently)
                **Form III** (Mutual): Interaction - كَاتَبَ (to correspond)
                **Form IV** (Causative): Causing action - أَكْتَبَ (to dictate)
                **Form V** (Reflexive II): Self-intensive - تَكَتَّبَ (to register oneself)
                **Form VI** (Reflexive III): Reciprocal - تَكَاتَبَ (to correspond with each other)
                **Form VIII** (Reflexive): Self-action - اكْتَتَبَ (to subscribe)
                **Form X** (Seeking): Seeking/considering - اسْتَكْتَبَ (to ask someone to write)
                
                Understanding these patterns unlocks understanding of thousands of related words!
            """.trimIndent(),
            rules = listOf(
                "Each form has a predictable pattern applied to the root letters",
                "Form II doubles the middle letter: فَعَّلَ",
                "Form III extends with Alif: فَاعَلَ",
                "Form IV prefixes Hamza: أَفْعَلَ",
                "Form V adds Ta prefix to Form II: تَفَعَّلَ",
                "Form X adds Alif-Sin-Ta prefix: اسْتَفْعَلَ",
                "Each form has associated meaning patterns"
            ),
            examples = listOf(
                GrammarExample(
                    "عَلِمَ - عَلَّمَ - أَعْلَمَ",
                    "'Alima - 'Allama - A'lama",
                    "To know - To teach - To inform",
                    "Root ع-ل-م in Forms I, II, and IV showing progression of meaning"
                ),
                GrammarExample(
                    "سَجَدَ - تَسَاجَدَ",
                    "Sajada - Tasajada",
                    "To prostrate - To prostrate together",
                    "Form I (action) vs Form VI (reciprocal action)"
                ),
                GrammarExample(
                    "غَفَرَ - اسْتَغْفَرَ",
                    "Ghafara - Istaghfara",
                    "To forgive - To seek forgiveness",
                    "Form I vs Form X showing seeking/requesting"
                )
            ),
            quranicApplications = listOf(
                "بِسْمِ اللَّهِ - Root س-م-و (name) in Form I",
                "نَزَّلَ الْقُرْآنَ - Form II (intensive) - He sent down the Quran repeatedly",
                "اسْتَغْفِرْ - Form X - Seek forgiveness (Quran often uses this command)",
                "يَتَفَكَّرُونَ - Form V - They reflect deeply (reflexive intensive)"
            ),
            commonMistakes = listOf(
                "Assuming all verbs follow Form I pattern",
                "Not recognizing Form IV when Hamza is prefixed",
                "Confusing Forms V and VI which look similar",
                "Forgetting that meaning changes with each form"
            ),
            practiceExercises = listOf(
                "Identify the form of 10 verbs from Surah Yusuf",
                "Take the root ك-ت-ب and create Forms I-IV",
                "Find examples of Form X (seeking) in the Quran",
                "Memorize the patterns and meanings of all 10 forms"
            )
        )
    )
    
    // Root Families
    val rootFamilies = listOf(
        RootFamily(
            root = "ك-ت-ب",
            meaning = "writing, writing-related",
            pattern = "Three-letter root showing writing concept",
            derivatives = listOf(
                Derivative("كَتَبَ", "to write", "Verb (Form I)", "He wrote a letter"),
                Derivative("كِتَاب", "book", "Noun", "The Quran is called Al-Kitab"),
                Derivative("كَاتِب", "writer", "Active participle", "The scribe wrote it"),
                Derivative("مَكْتُوب", "written", "Passive participle", "It is written in the book"),
                Derivative("مَكْتَبَة", "library", "Noun (place)", "I go to the library"),
                Derivative("مَكْتَب", "office, desk", "Noun (place)", "He works at an office"),
                Derivative("كُتَّاب", "Quranic school", "Noun (place)", "Children learn at the kuttab")
            )
        ),
        RootFamily(
            root = "ص-ل-و",
            meaning = "prayer, connection, blessing",
            pattern = "Three-letter root related to prayer",
            derivatives = listOf(
                Derivative("صَلَّى", "to pray", "Verb (Form II)", "He prayed five times"),
                Derivative("صَلَاة", "prayer", "Noun", "Prayer is the pillar of religion"),
                Derivative("مُصَلِّي", "one who prays", "Active participle", "The praying person is focused"),
                Derivative("مُصَلَّى", "place of prayer", "Noun (place)", "The musalla was clean"),
                Derivative("صِلَة", "connection", "Noun", "Maintaining family ties (silat ar-rahim)")
            )
        ),
        RootFamily(
            root = "ر-ح-م",
            meaning = "mercy, compassion",
            pattern = "Three-letter root showing mercy concept",
            derivatives = listOf(
                Derivative("رَحْمَة", "mercy", "Noun", "Allah's mercy encompasses all"),
                Derivative("رَحِيم", "Most Merciful", "Intensive adjective", "Ar-Rahman Ar-Rahim"),
                Derivative("رَحْمَن", "The Most Merciful", "Intensive adjective", "Name of Allah"),
                Derivative("رَحِمَ", "to have mercy", "Verb (Form I)", "Allah has mercy on His servants"),
                Derivative("مَرْحُوم", "one shown mercy", "Passive participle", "May he be shown mercy"),
                Derivative("رَحِم", "womb", "Noun", "Related through common origin of care")
            )
        )
    )
    
    fun getWordsByFrequency(): List<QuranicWord> = mostCommonQuranicWords.sortedByDescending { it.occurrencesInQuran }
    
    fun getLessonsByLevel(level: LanguageLevel): List<GrammarLesson> {
        return grammarLessons.filter { it.level == level }
    }
    
    fun getLessonsByCategory(category: GrammarCategory): List<GrammarLesson> {
        return grammarLessons.filter { it.category == category }
    }
    
    fun getRootFamily(root: String): RootFamily? = rootFamilies.find { it.root == root }
}
