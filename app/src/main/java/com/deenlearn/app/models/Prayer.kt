package com.deenlearn.app.models

import kotlinx.serialization.Serializable

@Serializable
data class WuduStep(
    val id: String,
    val stepNumber: Int,
    val name: String,
    val nameArabic: String,
    val description: String,
    val kidsDescription: String,
    val kidsEmoji: String,
    val duration: Int,
    val repetitions: Int,
    val isSunnah: Boolean,
    val commonMistakes: List<String>,
    val fiqhNotes: String?
)

@Serializable
data class SalahStep(
    val id: String,
    val stepNumber: Int,
    val name: String,
    val nameArabic: String,
    val description: String,
    val kidsDescription: String,
    val kidsEmoji: String,
    val position: SalahPosition,
    val recitation: Recitation?,
    val duration: Int,
    val repetitions: Int,
    val commonMistakes: List<String>,
    val fiqhNotes: String?
)

@Serializable
enum class SalahPosition {
    STANDING,
    BOWING,
    PROSTRATING,
    SITTING,
    STANDING_FROM_SUJUD;
    
    val displayName: String
        get() = when (this) {
            STANDING -> "Standing"
            BOWING -> "Bowing (Ruku)"
            PROSTRATING -> "Prostrating (Sujud)"
            SITTING -> "Sitting"
            STANDING_FROM_SUJUD -> "Rising"
        }
}

@Serializable
data class Recitation(
    val id: String,
    val arabic: String,
    val transliteration: String,
    val translation: String,
    val audioFileName: String?
)

@Serializable
enum class DuaCategory {
    AFTER_PRAYER,
    MORNING_ADHKAR,
    EVENING_ADHKAR,
    PROTECTION,
    TRAVEL,
    HEALING,
    GENERAL;
    
    val displayName: String
        get() = when (this) {
            AFTER_PRAYER -> "After Prayer"
            MORNING_ADHKAR -> "Morning Adhkar"
            EVENING_ADHKAR -> "Evening Adhkar"
            PROTECTION -> "Protection"
            TRAVEL -> "Travel"
            HEALING -> "Healing & Sickness"
            GENERAL -> "General Duas"
        }
    
    val icon: String
        get() = when (this) {
            AFTER_PRAYER -> "hands_sparkles"
            MORNING_ADHKAR -> "sunrise_fill"
            EVENING_ADHKAR -> "sunset_fill"
            PROTECTION -> "shield_fill"
            TRAVEL -> "airplane"
            HEALING -> "heart_fill"
            GENERAL -> "star_fill"
        }
    
    val colorName: String
        get() = when (this) {
            AFTER_PRAYER -> "blue"
            MORNING_ADHKAR -> "orange"
            EVENING_ADHKAR -> "purple"
            PROTECTION -> "green"
            TRAVEL -> "indigo"
            HEALING -> "red"
            GENERAL -> "teal"
        }
}

@Serializable
data class DuaAfterPrayer(
    val id: String,
    val name: String,
    val arabic: String,
    val transliteration: String,
    val translation: String,
    val benefit: String,
    val timesToRecite: Int,
    val category: DuaCategory = DuaCategory.AFTER_PRAYER,
    val source: String = "Sunnah",
    val hadithCollection: String? = null,
    val hadithNumber: Int? = null
)

@Serializable
data class PrayerMistake(
    val id: String,
    val title: String,
    val description: String,
    val correction: String,
    val category: MistakeCategory
)

@Serializable
enum class MistakeCategory {
    WUDU,
    SALAH,
    RECITATION,
    POSTURE;
    
    val displayName: String
        get() = name.lowercase().replaceFirstChar { it.uppercase() }
}

object PrayerData {
    val wuduSteps = listOf(
        WuduStep(
            id = "wudu_1",
            stepNumber = 1,
            name = "Intention (Niyyah)",
            nameArabic = "النية",
            description = "Make the intention in your heart to perform wudu for the sake of Allah.",
            kidsDescription = "Think in your heart: 'I'm doing wudu to become clean for Allah!'",
            kidsEmoji = "💭",
            duration = 5,
            repetitions = 1,
            isSunnah = true,
            commonMistakes = listOf("Saying intention out loud - it should be in the heart only"),
            fiqhNotes = "Intention should be made before washing hands"
        ),
        WuduStep(
            id = "wudu_2",
            stepNumber = 2,
            name = "Wash Hands",
            nameArabic = "غسل اليدين",
            description = "Wash both hands up to the wrists three times.",
            kidsDescription = "Wash your hands like you're washing them before eating - but do it 3 times!",
            kidsEmoji = "🙌",
            duration = 15,
            repetitions = 3,
            isSunnah = true,
            commonMistakes = listOf("Not washing between the fingers", "Not washing up to the wrists"),
            fiqhNotes = "Start with right hand, then left"
        ),
        WuduStep(
            id = "wudu_3",
            stepNumber = 3,
            name = "Rinse Mouth",
            nameArabic = "المضمضة",
            description = "Put water in your mouth, swish it around, and spit it out. Do this three times.",
            kidsDescription = "Take water in your mouth, swoosh it around like mouthwash, then spit! Do it 3 times!",
            kidsEmoji = "👄",
            duration = 10,
            repetitions = 3,
            isSunnah = true,
            commonMistakes = listOf("Not rinsing thoroughly", "Swallowing the water"),
            fiqhNotes = "Use right hand to bring water to mouth"
        ),
        WuduStep(
            id = "wudu_4",
            stepNumber = 4,
            name = "Sniff Water in Nose",
            nameArabic = "الاستنشاق",
            description = "Sniff water into your nostrils gently, then blow it out. Do this three times.",
            kidsDescription = "Gently sniff water into your nose (not too hard!), then blow it out like you have a sneeze!",
            kidsEmoji = "👃",
            duration = 10,
            repetitions = 3,
            isSunnah = true,
            commonMistakes = listOf("Sniffing too hard (can hurt!)", "Not blowing out completely"),
            fiqhNotes = "If fasting, be extra gentle"
        ),
        WuduStep(
            id = "wudu_5",
            stepNumber = 5,
            name = "Wash Face",
            nameArabic = "غسل الوجه",
            description = "Wash your face from the hairline to the chin and from ear to ear, three times.",
            kidsDescription = "Splash water on your whole face - from your forehead down to your chin, and from ear to ear!",
            kidsEmoji = "😊",
            duration = 15,
            repetitions = 3,
            isSunnah = false,
            commonMistakes = listOf("Missing parts of the face", "Not washing the entire area"),
            fiqhNotes = "This is obligatory (fard)"
        ),
        WuduStep(
            id = "wudu_6",
            stepNumber = 6,
            name = "Wash Arms",
            nameArabic = "غسل الذراعين",
            description = "Wash your right arm from fingertips to elbow three times, then the left arm.",
            kidsDescription = "Wash your right arm from your fingers all the way up to your elbow. Then do your left arm!",
            kidsEmoji = "💪",
            duration = 20,
            repetitions = 3,
            isSunnah = false,
            commonMistakes = listOf("Not including elbows", "Forgetting to wash entire arm"),
            fiqhNotes = "Must include the elbows - this is obligatory"
        ),
        WuduStep(
            id = "wudu_7",
            stepNumber = 7,
            name = "Wipe Head",
            nameArabic = "مسح الرأس",
            description = "Wet your hands and wipe over your head from front to back once.",
            kidsDescription = "Wet your hands and slide them over your head from front to back, like combing with your fingers!",
            kidsEmoji = "🙇",
            duration = 10,
            repetitions = 1,
            isSunnah = false,
            commonMistakes = listOf("Wiping multiple times (once is enough)", "Not covering full area"),
            fiqhNotes = "Wipe at least quarter of the head - this is obligatory"
        ),
        WuduStep(
            id = "wudu_8",
            stepNumber = 8,
            name = "Wipe Ears",
            nameArabic = "مسح الأذنين",
            description = "Using the same water from wiping head, wipe inside and outside of both ears.",
            kidsDescription = "Use your wet fingers to wipe your ears - inside with your pointer finger, outside with your thumb!",
            kidsEmoji = "👂",
            duration = 10,
            repetitions = 1,
            isSunnah = true,
            commonMistakes = listOf("Using new water (should use same water from head)", "Not wiping both inside and outside"),
            fiqhNotes = "Part of wiping the head in most schools"
        ),
        WuduStep(
            id = "wudu_9",
            stepNumber = 9,
            name = "Wash Feet",
            nameArabic = "غسل الرجلين",
            description = "Wash your right foot up to the ankle three times, then the left foot, making sure water reaches between the toes.",
            kidsDescription = "Wash your right foot up to your ankle (make sure to get between your toes!), then do your left foot!",
            kidsEmoji = "🦶",
            duration = 20,
            repetitions = 3,
            isSunnah = false,
            commonMistakes = listOf("Not washing between toes", "Not including ankles", "Not washing thoroughly"),
            fiqhNotes = "Must wash up to and including ankles - this is obligatory"
        )
    )
    
    val wuduDua = Recitation(
        id = "wudu_dua",
        arabic = "أَشْهَدُ أَنْ لاَ إِلَهَ إِلاَّ اللَّهُ وَحْدَهُ لاَ شَرِيكَ لَهُ، وَأَشْهَدُ أَنَّ مُحَمَّدًا عَبْدُهُ وَرَسُولُهُ",
        transliteration = "Ashhadu an la ilaha illallahu wahdahu la shareeka lahu, wa ashhadu anna Muhammadan 'abduhu wa rasuluhu",
        translation = "I bear witness that there is no god but Allah alone, without any partner, and I bear witness that Muhammad is His slave and Messenger.",
        audioFileName = "wudu_dua.mp3"
    )
    
    val kidsWuduTips = listOf(
        "Don't waste water! The Prophet ﷺ used only a little bit of water for wudu",
        "Say 'Bismillah' (In the name of Allah) before starting!",
        "Always start with your right side - right hand, right arm, right foot!",
        "Make sure to wash between your fingers and toes - don't miss the gaps!",
        "When you finish wudu, say the special dua to get extra rewards!",
        "If you break your wudu (go to bathroom, pass gas), you need to do it again before prayer",
        "Your wudu is like invisible armor - it keeps you spiritually clean!"
    )
}
