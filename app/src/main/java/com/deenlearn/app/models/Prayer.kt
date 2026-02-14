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

// Adult Prayer Fiqh and Voluntary Prayers

@Serializable
data class PrayerFiqh(
    val category: String,
    val title: String,
    val description: String,
    val evidences: List<Evidence>,
    val ruling: FiqhRuling,
    val madhabDifferences: List<MadhabOpinion>?,
    val practicalGuidance: String
)

@Serializable
data class MadhabOpinion(
    val madhab: String,
    val opinion: String,
    val reasoning: String
)

@Serializable
enum class FiqhRuling {
    FARD,      // Obligatory
    WAJIB,     // Necessary
    SUNNAH_MUAKKADAH,  // Confirmed Sunnah
    SUNNAH,    // Recommended
    MUSTAHABB, // Preferred
    MUBAH,     // Permissible
    MAKRUH,    // Disliked
    HARAM;     // Forbidden
    
    val displayName: String
        get() = when (this) {
            FARD -> "Fard (Obligatory)"
            WAJIB -> "Wajib (Necessary)"
            SUNNAH_MUAKKADAH -> "Sunnah Mu'akkadah (Confirmed Sunnah)"
            SUNNAH -> "Sunnah (Recommended)"
            MUSTAHABB -> "Mustahabb (Preferred)"
            MUBAH -> "Mubah (Permissible)"
            MAKRUH -> "Makruh (Disliked)"
            HARAM -> "Haram (Forbidden)"
        }
}

@Serializable
data class VoluntaryPrayer(
    val id: String,
    val name: String,
    val nameArabic: String,
    val category: VoluntaryPrayerCategory,
    val description: String,
    val virtues: String,
    val howToPerform: String,
    val rakaat: Int,
    val timing: String,
    val frequency: String,
    val evidences: List<Evidence>,
    val specialFeatures: List<String>
)

@Serializable
enum class VoluntaryPrayerCategory {
    RAWATIB,      // Regular Sunnah prayers
    NIGHT_PRAYER, // Tahajjud, Qiyam
    DAYTIME,      // Duha, etc.
    SPECIAL_OCCASION, // Istikhara, etc.
    TARAWEEH;     // Ramadan specific
    
    val displayName: String
        get() = when (this) {
            RAWATIB -> "Rawatib (Regular Sunnah)"
            NIGHT_PRAYER -> "Night Prayers"
            DAYTIME -> "Daytime Voluntary"
            SPECIAL_OCCASION -> "Special Occasion"
            TARAWEEH -> "Taraweeh (Ramadan)"
        }
}

object AdultPrayerContent {
    
    // Prayer Fiqh - Conditions, Pillars, and Obligations
    val prayerFiqhTopics = listOf(
        PrayerFiqh(
            category = "Conditions of Prayer",
            title = "Prerequisites for Valid Salah",
            description = """
                There are 9 conditions that must be met before prayer is valid:
                1. Islam (being Muslim)
                2. Sanity and consciousness
                3. Reaching the age of obligation (puberty, though children should practice)
                4. Purity from impurities (body, clothes, place)
                5. Wudu (ablution) or Ghusl when needed
                6. Covering the awrah (private parts)
                7. Facing the Qiblah
                8. Entering the time of prayer
                9. Having the intention (niyyah)
                
                If any condition is missing, the prayer is invalid and must be repeated.
            """.trimIndent(),
            evidences = listOf(
                Evidence(
                    id = "prayer_cond_1",
                    arabic = "وَمَا مَنَعَهُمْ أَن تُقْبَلَ مِنْهُمْ نَفَقَاتُهُمْ إِلَّا أَنَّهُمْ كَفَرُوا بِاللَّهِ وَبِرَسُولِهِ",
                    translation = "And nothing prevents their spending from being accepted from them except that they disbelieve in Allah and His Messenger.",
                    reference = "Quran 9:54"
                ),
                Evidence(
                    id = "prayer_cond_2",
                    arabic = "لَا صَلَاةَ لِمَنْ لَا وُضُوءَ لَهُ",
                    translation = "There is no prayer for one who has no wudu.",
                    reference = "Hadith - Ibn Majah 272"
                )
            ),
            ruling = FiqhRuling.FARD,
            madhabDifferences = listOf(
                MadhabOpinion(
                    madhab = "Hanafi",
                    opinion = "Distinguishes between 'fard' (obligatory) and 'wajib' (necessary) more strictly",
                    reasoning = "Fard is established by definitive proof, wajib by speculative proof"
                ),
                MadhabOpinion(
                    madhab = "Other Three",
                    opinion = "Generally treat fard and wajib as same or very similar",
                    reasoning = "Both are obligatory, leaving either is sinful"
                )
            ),
            practicalGuidance = """
                Before every prayer, make a mental checklist:
                - Clean body, clothes, and place
                - Wudu is valid
                - Awrah is covered (men: navel to knees minimum; women: all except face and hands)
                - Qiblah direction confirmed (use compass or app if unsure)
                - Prayer time has entered (check reliable prayer times)
                - Clear intention in heart (don't need to say it aloud)
            """.trimIndent()
        ),
        PrayerFiqh(
            category = "Pillars of Prayer",
            title = "14 Essential Actions (Arkan)",
            description = """
                These actions are absolutely essential in every prayer. If missed intentionally or forgotten without prostration of forgetfulness, the prayer is invalid:
                
                1. Standing (Qiyam) if able
                2. Opening Takbir (Allahu Akbar)
                3. Reciting Al-Fatiha
                4. Bowing (Ruku')
                5. Rising from Ruku'
                6. Standing upright after Ruku'
                7. Prostrating (Sujud) - twice
                8. Sitting between two Sujud
                9. Calmness in all positions
                10. Final sitting (Tashahhud)
                11. Reciting the Tashahhud
                12. Sending blessings on the Prophet ﷺ
                13. First Taslim (Assalamu alaikum)
                14. Performing actions in order
            """.trimIndent(),
            evidences = listOf(
                Evidence(
                    id = "prayer_pillar_1",
                    arabic = "صَلُّوا كَمَا رَأَيْتُمُونِي أُصَلِّي",
                    translation = "Pray as you have seen me praying.",
                    reference = "Hadith - Sahih Bukhari 631"
                ),
                Evidence(
                    id = "prayer_pillar_2",
                    arabic = "لَا تَتِمُّ صَلَاةُ أَحَدِكُمْ حَتَّى يَتَوَضَّأَ... ثُمَّ يُكَبِّرَ",
                    translation = "The prayer of any one of you is not complete until he performs wudu...then says Takbir.",
                    reference = "Hadith - Sahih Bukhari 757"
                )
            ),
            ruling = FiqhRuling.FARD,
            madhabDifferences = listOf(
                MadhabOpinion(
                    madhab = "Maliki",
                    opinion = "Lists 14 specific pillars as mentioned",
                    reasoning = "Based on direct actions of the Prophet ﷺ that he never omitted"
                ),
                MadhabOpinion(
                    madhab = "Hanafi",
                    opinion = "Has 6 pillars (Fard) and considers rest as wajib",
                    reasoning = "Differentiates between established by definitive vs. speculative proof"
                )
            ),
            practicalGuidance = """
                Learn these by heart and practice them in order. If you forget a pillar:
                - If remembered before moving to the next pillar: Go back and do it
                - If remembered after moving far ahead: That rak'ah is invalid, do it again
                - If remembered after completing prayer: Make up the missed rak'ah
                
                The key is calmness (Tuma'ninah) - don't rush. The Prophet ﷺ told a man who prayed quickly: "Go back and pray, for you have not prayed."
            """.trimIndent()
        ),
        PrayerFiqh(
            category = "Khushu in Prayer",
            title = "Developing Consciousness and Presence",
            description = """
                Khushu' is the heart and soul of prayer. It means being fully present mentally, emotionally, and spiritually while praying. Without it, prayer becomes empty movements.
                
                Signs of Khushu':
                - Understanding what you're reciting
                - Feeling the meanings touch your heart
                - Fear and hope before Allah
                - Not being distracted by worldly thoughts
                - Feeling that you're standing before Allah
                - Time passes quickly in prayer
                
                Imam Ibn Al-Qayyim said: "Prayer without presence of heart is like a body without soul."
            """.trimIndent(),
            evidences = listOf(
                Evidence(
                    id = "khushu_1",
                    arabic = "قَدْ أَفْلَحَ الْمُؤْمِنُونَ الَّذِينَ هُمْ فِي صَلَاتِهِمْ خَاشِعُونَ",
                    translation = "Certainly will the believers have succeeded: They who are during their prayer humbly submissive.",
                    reference = "Quran 23:1-2"
                ),
                Evidence(
                    id = "khushu_2",
                    arabic = "مَا لِي أَرَاكُمْ رَافِعِي أَيْدِيكُمْ كَأَنَّهَا أَذْنَابُ خَيْلٍ شُمْسٍ اسْكُنُوا فِي الصَّلَاةِ",
                    translation = "What is the matter with you that you raise your hands as if they were the tails of wild horses? Be still in prayer.",
                    reference = "Hadith - Sahih Muslim 430"
                )
            ),
            ruling = FiqhRuling.WAJIB,
            madhabDifferences = null,
            practicalGuidance = """
                How to develop Khushu':
                
                Before Prayer:
                - Prepare early - don't rush to prayer last minute
                - Make fresh wudu mindfully
                - Choose a clean, quiet place
                - Remove distractions (phone, people talking)
                - Remember you're about to stand before Allah
                
                During Prayer:
                - Look at place of prostration (or sajdah spot)
                - Understand what you're reciting - learn translations
                - Visualize meanings as you recite
                - Feel you're in conversation with Allah (He responds to Al-Fatiha!)
                - Vary your recitations to avoid monotony
                - Slow down - quality over speed
                
                After Prayer:
                - Reflect on how the prayer went
                - Make dua to improve next time
                - Don't leave the place immediately
            """.trimIndent()
        )
    )
    
    // Voluntary Prayers
    val voluntaryPrayers = listOf(
        VoluntaryPrayer(
            id = "tahajjud",
            name = "Tahajjud (Night Prayer)",
            nameArabic = "صلاة التهجد",
            category = VoluntaryPrayerCategory.NIGHT_PRAYER,
            description = """
                Tahajjud is the voluntary prayer performed in the last third of the night after waking from sleep. It's one of the most virtuous acts of worship and was the practice of the Prophet ﷺ and all the pious predecessors.
                
                The last third of night is when Allah descends to the lowest heaven and asks: "Is there anyone calling upon Me that I may answer him? Is there anyone asking of Me that I may give him? Is there anyone seeking My forgiveness that I may forgive him?"
            """.trimIndent(),
            virtues = """
                1. Closest time to Allah - special mercy and acceptance
                2. Distinguishing quality of the righteous
                3. Expiates sins and prevents wrongdoing
                4. Best prayer after obligatory ones
                5. Time when duas are most accepted
                6. Increases provision and brings blessings
                7. Illuminates the face and heart
            """.trimIndent(),
            howToPerform = """
                1. Wake up in last third of night (calculated by dividing night into three)
                2. Make wudu and pray 2 rak'at as "prayer of awakening"
                3. Pray 2 rak'at at a time (can pray 2, 4, 6, 8, or more)
                4. Recommended to pray at least 11 rak'at (Prophet's usual practice)
                5. Make the prayer long with slow, deliberate recitation
                6. Focus on humility and supplication
                7. End with Witr prayer (odd number)
                8. Make long dua in prostration and after prayer
                
                The Prophet ﷺ would pray 2 light rak'at, then 2 longer ones, continuing until he prayed Witr.
            """.trimIndent(),
            rakaat = 11,
            timing = "Last third of night (best) or anytime between Isha and Fajr after sleeping",
            frequency = "Daily (highly recommended)",
            evidences = listOf(
                Evidence(
                    id = "tahajjud_1",
                    arabic = "وَمِنَ اللَّيْلِ فَتَهَجَّدْ بِهِ نَافِلَةً لَكَ عَسَىٰ أَن يَبْعَثَكَ رَبُّكَ مَقَامًا مَّحْمُودًا",
                    translation = "And from [part of] the night, pray with it as additional [worship] for you; it is expected that your Lord will resurrect you to a praised station.",
                    reference = "Quran 17:79"
                ),
                Evidence(
                    id = "tahajjud_2",
                    arabic = "أَفْضَلُ الصَّلَاةِ بَعْدَ الْفَرِيضَةِ صَلَاةُ اللَّيْلِ",
                    translation = "The best prayer after the obligatory prayers is the night prayer.",
                    reference = "Hadith - Sahih Muslim 1163"
                ),
                Evidence(
                    id = "tahajjud_3",
                    arabic = "يَنْزِلُ رَبُّنَا تَبَارَكَ وَتَعَالَى كُلَّ لَيْلَةٍ إِلَى السَّمَاءِ الدُّنْيَا",
                    translation = "Our Lord descends every night to the lowest heaven...",
                    reference = "Hadith - Sahih Bukhari 1145"
                )
            ),
            specialFeatures = listOf(
                "Best performed after midnight rest",
                "Can pray alone or in congregation (both allowed)",
                "Recite lengthy portions of Quran",
                "Prolonged prostrations and supplications",
                "Best to make duas related to Hereafter",
                "If can't wake up, pray before sleeping with intention of Qiyam",
                "Even 2 rak'at is better than nothing"
            )
        ),
        VoluntaryPrayer(
            id = "duha",
            name = "Duha (Forenoon Prayer)",
            nameArabic = "صلاة الضحى",
            category = VoluntaryPrayerCategory.DAYTIME,
            description = """
                Salat ad-Duha is prayed after sunrise and before noon (Dhuhr). The Prophet ﷺ advised his companions to maintain this prayer regularly. It's a source of daily provision, protection, and blessings.
                
                Abu Darda reported that the Prophet ﷺ said: "Allah said: 'O son of Adam, pray for Me four rak'at at the beginning of the day; I will suffice you for what comes later.'"
            """.trimIndent(),
            virtues = """
                1. Equivalent to charity for every joint (360 charities!)
                2. Brings daily provision and blessings
                3. Allah takes care of your affairs for the day
                4. Counted among the ever-prayerful (Awwabeen)
                5. Strengthens the body and increases energy
                6. Easy to maintain as it's done when most people are busy
            """.trimIndent(),
            howToPerform = """
                1. Time begins when sun rises about spear's length (15-20 min after sunrise)
                2. Time ends about 10-15 minutes before Dhuhr
                3. Best time is mid-morning when sun is hot (Duha means forenoon)
                4. Minimum 2 rak'at, recommended 4, maximum 12
                5. Pray 2 rak'at at a time
                6. Recommended to recite longer surahs
                7. Can be prayed at home or masjid
                
                The Prophet ﷺ would sometimes pray 8 rak'at during the conquest of Makkah.
            """.trimIndent(),
            rakaat = 2,
            timing = "15-20 minutes after sunrise until 10-15 minutes before Dhuhr, best at mid-morning",
            frequency = "Daily (highly recommended)",
            evidences = listOf(
                Evidence(
                    id = "duha_1",
                    arabic = "يُصْبِحُ عَلَى كُلِّ سُلَامَى مِنْ أَحَدِكُمْ صَدَقَةٌ... وَيُجْزِئُ مِنْ ذَلِكَ رَكْعَتَانِ يَرْكَعُهُمَا مِنْ الضُّحَى",
                    translation = "Every morning charity is due from every joint of one of you... Two rak'at prayed in Duha are sufficient for that.",
                    reference = "Hadith - Sahih Muslim 720"
                ),
                Evidence(
                    id = "duha_2",
                    arabic = "مَنْ صَلَّى الضُّحَى أَرْبَعًا وَقَبْلَ الْأُولَى أَرْبَعًا بُنِيَ لَهُ بَيْتٌ فِي الْجَنَّةِ",
                    translation = "Whoever prays four rak'at of Duha and four before Dhuhr, a house will be built for him in Paradise.",
                    reference = "Hadith - Sunan Ibn Majah 1382"
                )
            ),
            specialFeatures = listOf(
                "Easy to establish as regular habit",
                "Very few people maintain it - distinguishes the committed",
                "Perfect for working professionals (can pray at office)",
                "Relatively short duration",
                "Brings barakah to entire day",
                "Known as 'Salat al-Awwabeen' (prayer of the ever-returning to Allah)",
                "If missed, no need to make up - just pray it next day"
            )
        ),
        VoluntaryPrayer(
            id = "istikhara",
            name = "Istikhara (Seeking Guidance)",
            nameArabic = "صلاة الاستخارة",
            category = VoluntaryPrayerCategory.SPECIAL_OCCASION,
            description = """
                Salat al-Istikhara is performed when facing a decision about something permissible. It's a way of asking Allah to guide you to what's best for your religion and worldly life, and to make it easy if it's good, or turn you away if it's bad.
                
                The Prophet ﷺ taught this prayer to his companions and emphasized its importance in decision-making.
            """.trimIndent(),
            virtues = """
                1. Demonstrates trust in Allah's knowledge over our limited understanding
                2. Brings peace of mind knowing you've asked the All-Knowing
                3. Allah guides to what's truly better, not just what seems good
                4. Removes regret - you know you consulted Allah
                5. Form of worship and submission to Allah
            """.trimIndent(),
            howToPerform = """
                1. Have a specific matter in mind (not used for obligatory matters)
                2. Perform wudu
                3. Pray 2 rak'at (not during forbidden times)
                4. After Salam, raise hands and recite the Istikhara dua
                5. Specifically mention what you're seeking guidance about
                6. Can be done in any language after learning Arabic dua
                
                Istikhara Dua (after prayer):
                "Allahumma inni astakhiruka bi'ilmika, wa astaqdiruka bi-qudratika, wa as'aluka min fadlika al-'azim. Fa-innaka taqdiru wa la aqdiru, wa ta'lamu wa la a'lamu, wa anta 'allamu al-ghuyub..."
                
                (O Allah, I seek Your guidance by Your knowledge, and I seek Your ability by Your power, and I ask You of Your great bounty. For You have power and I do not, and You know and I do not, and You are the Knower of the unseen...)
            """.trimIndent(),
            rakaat = 2,
            timing = "Anytime except forbidden times (after Fajr until sunrise, at zenith, after Asr until sunset)",
            frequency = "As needed when facing decisions",
            evidences = listOf(
                Evidence(
                    id = "istikhara_1",
                    arabic = "كَانَ رَسُولُ اللَّهِ صَلَّى اللَّهُ عَلَيْهِ وَسَلَّمَ يُعَلِّمُنَا الِاسْتِخَارَةَ فِي الْأُمُورِ كُلِّهَا",
                    translation = "The Messenger of Allah ﷺ would teach us Istikhara in all matters.",
                    reference = "Hadith - Sahih Bukhari 1162"
                )
            ),
            specialFeatures = listOf(
                "Not for deciding between halal and haram - only for permissible choices",
                "Answer may come as: ease in proceeding, obstacles appearing, feeling in heart, advice from others",
                "Don't expect dreams necessarily - guidance comes in many forms",
                "Can repeat multiple times for same matter",
                "After Istikhara, proceed with what seems best and trust Allah's plan",
                "If still uncertain, consult knowledgeable Muslims",
                "The goal is Allah's guidance, not changing Allah's decree"
            )
        ),
        VoluntaryPrayer(
            id = "witr",
            name = "Witr (Odd-Numbered Prayer)",
            nameArabic = "صلاة الوتر",
            category = VoluntaryPrayerCategory.NIGHT_PRAYER,
            description = """
                Witr is the final prayer of the night, consisting of an odd number of rak'at (1, 3, 5, 7, 9, or 11). The Prophet ﷺ never missed Witr, whether traveling or at home, and called it a duty for every Muslim.
                
                "Allah is odd (Witr) and He loves odd numbers, so observe Witr, O followers of the Quran!" [Hadith]
            """.trimIndent(),
            virtues = """
                1. Highly emphasized Sunnah (some scholars say wajib/necessary)
                2. The Prophet ﷺ never left it, even when traveling
                3. Completes the night prayer
                4. Special dua (Qunoot) is made in Witr
                5. Angels say Ameen to the Witr prayer
                6. Makes up for deficiencies in obligatory prayers
            """.trimIndent(),
            howToPerform = """
                Minimum (1 rak'ah):
                1. Pray 1 rak'ah with Qunoot dua before or after Ruku
                
                Most Common (3 rak'at):
                Method 1: Pray 2 rak'at with salam, then 1 separate rak'ah
                Method 2: Pray all 3 rak'at continuously with one tashahud at end
                
                Longer (5, 7, 9, or 11 rak'at):
                - Pray them all continuously with one tashahud at the end
                - Or pray them 2 by 2 with final odd rak'ah
                
                Qunoot Dua (most commonly in 3rd rak'ah after or before Ruku):
                "Allahumma-hdini fiman hadayt..." (O Allah, guide me among those You have guided...)
                
                Can also make personal duas in your language during Qunoot.
            """.trimIndent(),
            rakaat = 1,
            timing = "After Isha prayer until Fajr begins, best in last third of night",
            frequency = "Daily (highly emphasized)",
            evidences = listOf(
                Evidence(
                    id = "witr_1",
                    arabic = "إِنَّ اللَّهَ وِتْرٌ يُحِبُّ الْوِتْرَ فَأَوْتِرُوا يَا أَهْلَ الْقُرْآنِ",
                    translation = "Allah is Witr (odd) and loves what is odd. So observe Witr, O people of the Quran.",
                    reference = "Hadith - Sunan Abi Dawud 1416"
                ),
                Evidence(
                    id = "witr_2",
                    arabic = "الْوِتْرُ حَقٌّ عَلَى كُلِّ مُسْلِمٍ",
                    translation = "Witr is a duty upon every Muslim.",
                    reference = "Hadith - Sunan Abi Dawud 1420"
                )
            ),
            specialFeatures = listOf(
                "Last prayer of the night",
                "If you wake for Tahajjud, delay Witr until end",
                "If you won't wake up, pray Witr before sleeping",
                "Cannot pray Witr twice in one night - if prayed early then woke up, pray even rak'at only",
                "Qunoot dua is Sunnah and can be in your language",
                "Can pray different odd numbers on different nights",
                "Minimum 1 rak'ah, but 3 is most common",
                "Prophet ﷺ usually prayed 11 rak'at including Witr"
            )
        )
    )
    
    fun getPrayerFiqhByCategory(category: String): List<PrayerFiqh> {
        return prayerFiqhTopics.filter { it.category == category }
    }
    
    fun getVoluntaryPrayersByCategory(category: VoluntaryPrayerCategory): List<VoluntaryPrayer> {
        return voluntaryPrayers.filter { it.category == category }
    }
}
