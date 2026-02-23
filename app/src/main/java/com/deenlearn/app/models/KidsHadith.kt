package com.deenlearn.app.models

import kotlinx.serialization.Serializable

@Serializable
data class KidsHadith(
    val id: String,
    val emoji: String,
    val title: String,
    val arabicText: String,
    val simpleMeaning: String,
    val funFact: String,
    val collection: String,
    val hadithNumber: Int,
    val reference: String
)

object HadithData {
    val kidsHadithList = listOf(
        KidsHadith(
            id = "hadith_smile",
            emoji = "😊",
            title = "Smiling is Charity!",
            arabicText = "تَبَسُّمُكَ فِي وَجْهِ أَخِيكَ صَدَقَةٌ",
            simpleMeaning = "Your smile in your brother's face is charity!",
            funFact = "Did you know? Every time you smile at someone, you get rewarded by Allah! It's free charity that makes everyone happy!",
            collection = "Sunan al-Tirmidhi",
            hadithNumber = 1956,
            reference = "Narrated by Prophet Muhammad ﷺ"
        ),
        KidsHadith(
            id = "hadith_kindness",
            emoji = "🤗",
            title = "Be Kind to Everyone!",
            arabicText = "ارْحَمُوا مَنْ فِي الأَرْضِ يَرْحَمْكُمْ مَنْ فِي السَّمَاءِ",
            simpleMeaning = "Have mercy on those on earth, and the One in heaven will have mercy on you.",
            funFact = "When you're kind to animals, plants, and people, Allah will be kind to you! Kindness is like a boomerang - it comes back to you!",
            collection = "Sunan Abi Dawud",
            hadithNumber = 4941,
            reference = "Narrated by Prophet Muhammad ﷺ"
        ),
        KidsHadith(
            id = "hadith_parents",
            emoji = "👨‍👩‍👧‍👦",
            title = "Paradise is Under Mother's Feet",
            arabicText = "الْجَنَّةُ تَحْتَ أَقْدَامِ الأُمَّهَاتِ",
            simpleMeaning = "Paradise lies under the feet of mothers.",
            funFact = "Being nice to your mom (and dad!) is one of the best things you can do! Helping them, listening to them, and making them happy brings you closer to Paradise!",
            collection = "Sunan al-Nasa'i",
            hadithNumber = 3104,
            reference = "Narrated by Prophet Muhammad ﷺ"
        ),
        KidsHadith(
            id = "hadith_clean",
            emoji = "🧼",
            title = "Cleanliness is Part of Faith",
            arabicText = "الطَّهُورُ شَطْرُ الإِيمَانِ",
            simpleMeaning = "Cleanliness is half of faith.",
            funFact = "Keeping yourself, your clothes, and your room clean makes Allah happy! When you brush your teeth, wash your hands, and tidy up, you're doing an act of worship!",
            collection = "Sahih Muslim",
            hadithNumber = 223,
            reference = "Narrated by Prophet Muhammad ﷺ"
        ),
        KidsHadith(
            id = "hadith_truthful",
            emoji = "🎯",
            title = "Always Tell the Truth",
            arabicText = "عَلَيْكُمْ بِالصِّدْقِ فَإِنَّ الصِّدْقَ يَهْدِي إِلَى الْبِرِّ",
            simpleMeaning = "Be truthful, for truthfulness leads to righteousness.",
            funFact = "Even if telling the truth might get you in trouble, it's always better than lying! Allah loves honest people, and lying makes Shaytan happy. Always choose truth!",
            collection = "Sahih al-Bukhari",
            hadithNumber = 6094,
            reference = "Narrated by Prophet Muhammad ﷺ"
        ),
        KidsHadith(
            id = "hadith_anger",
            emoji = "😤",
            title = "Don't Get Angry",
            arabicText = "لَا تَغْضَبْ",
            simpleMeaning = "Do not get angry.",
            funFact = "A man asked the Prophet ﷺ for advice many times, and every time the Prophet said 'Don't get angry!' Staying calm when you're upset is a superpower! Take deep breaths and remember Allah.",
            collection = "Sahih al-Bukhari",
            hadithNumber = 6116,
            reference = "Narrated by Prophet Muhammad ﷺ"
        ),
        KidsHadith(
            id = "hadith_neighbor",
            emoji = "🏘️",
            title = "Be Good to Your Neighbors",
            arabicText = "مَا زَالَ جِبْرِيلُ يُوصِينِي بِالْجَارِ",
            simpleMeaning = "Angel Jibril kept advising me about treating neighbors well.",
            funFact = "Your neighbor doesn't just mean the house next door - it means the 40 houses around you in all directions! Share your food, help them, and never hurt them!",
            collection = "Sahih al-Bukhari",
            hadithNumber = 6014,
            reference = "Narrated by Prophet Muhammad ﷺ"
        ),
        KidsHadith(
            id = "hadith_good_word",
            emoji = "💬",
            title = "Kind Words Are Charity",
            arabicText = "الْكَلِمَةُ الطَّيِّبَةُ صَدَقَةٌ",
            simpleMeaning = "A good word is charity.",
            funFact = "Saying nice things to people is like giving them a gift! 'Please,' 'Thank you,' 'You did great!' - these words cost nothing but are worth a lot to Allah!",
            collection = "Sahih al-Bukhari",
            hadithNumber = 2989,
            reference = "Narrated by Prophet Muhammad ﷺ"
        ),
        KidsHadith(
            id = "hadith_strong",
            emoji = "💪",
            title = "The Strong Believer",
            arabicText = "الْمُؤْمِنُ الْقَوِيُّ خَيْرٌ وَأَحَبُّ إِلَى اللَّهِ مِنَ الْمُؤْمِنِ الضَّعِيفِ",
            simpleMeaning = "The strong believer is better and more beloved to Allah than the weak believer.",
            funFact = "Being strong doesn't just mean muscles! It means being strong in faith, brave in doing good, and confident in standing up for what's right! Be a strong believer!",
            collection = "Sahih Muslim",
            hadithNumber = 2664,
            reference = "Narrated by Prophet Muhammad ﷺ"
        ),
        KidsHadith(
            id = "hadith_best_person",
            emoji = "⭐",
            title = "The Best Person",
            arabicText = "خَيْرُ النَّاسِ أَنْفَعُهُمْ لِلنَّاسِ",
            simpleMeaning = "The best of people are those who are most beneficial to others.",
            funFact = "Want to be the best? Help people! Share your toys, help with homework, carry heavy bags for others. The more you help, the more Allah loves you!",
            collection = "Daraqutni",
            hadithNumber = 2623,
            reference = "Narrated by Prophet Muhammad ﷺ"
        ),
        KidsHadith(
            id = "hadith_plants",
            emoji = "🌱",
            title = "Plant a Tree, Get Rewards!",
            arabicText = "مَا مِنْ مُسْلِمٍ يَغْرِسُ غَرْسًا إِلَّا كَانَ مَا أُكِلَ مِنْهُ لَهُ صَدَقَةً",
            simpleMeaning = "Any Muslim who plants a tree or sows seeds, then a bird, person, or animal eats from it, it is charity for him.",
            funFact = "When you plant a tree or flower, every time a bird eats from it or someone rests in its shade, you get rewards from Allah - even after you die! Nature is amazing!",
            collection = "Sahih al-Bukhari",
            hadithNumber = 2320,
            reference = "Narrated by Prophet Muhammad ﷺ"
        ),
        KidsHadith(
            id = "hadith_path",
            emoji = "🚶",
            title = "Remove Harm from the Road",
            arabicText = "الإِيمَانُ بِضْعٌ وَسَبْعُونَ شُعْبَةً وَأَدْنَاهَا إِمَاطَةُ الأَذَى عَنِ الطَّرِيقِ",
            simpleMeaning = "Faith has more than seventy branches, and the least is removing harmful things from the road.",
            funFact = "Even picking up a banana peel from the sidewalk so no one slips is an act of faith! Keep paths clean and safe for others - it's worship!",
            collection = "Sahih Muslim",
            hadithNumber = 35,
            reference = "Narrated by Prophet Muhammad ﷺ"
        ),
        KidsHadith(
            id = "hadith_animals",
            emoji = "🐈",
            title = "Be Kind to Animals",
            arabicText = "دَخَلَتِ امْرَأَةٌ النَّارَ فِي هِرَّةٍ رَبَطَتْهَا",
            simpleMeaning = "A woman entered Hell because of a cat which she tied up and did not feed, nor did she let it go to eat the insects of the earth.",
            funFact = "Allah loves animals too! Feed stray cats, give water to birds, don't hurt insects unless they're bothering you. Being kind to animals shows you have a good heart!",
            collection = "Sahih al-Bukhari",
            hadithNumber = 3318,
            reference = "Narrated by Prophet Muhammad ﷺ"
        ),
        KidsHadith(
            id = "hadith_thankful",
            emoji = "🙏",
            title = "Say Alhamdulillah",
            arabicText = "مَنْ لَمْ يَشْكُرِ النَّاسَ لَمْ يَشْكُرِ اللَّهَ",
            simpleMeaning = "Whoever does not thank people, does not thank Allah.",
            funFact = "Always say 'Thank you' or 'Jazakallah Khairan' (May Allah reward you) when someone helps you! Being grateful makes you and others happy!",
            collection = "Sunan Abi Dawud",
            hadithNumber = 4811,
            reference = "Narrated by Prophet Muhammad ﷺ"
        ),
        KidsHadith(
            id = "hadith_helping",
            emoji = "🤝",
            title = "Help Your Brother",
            arabicText = "انْصُرْ أَخَاكَ ظَالِمًا أَوْ مَظْلُومًا",
            simpleMeaning = "Help your brother whether he is the oppressor or the oppressed.",
            funFact = "Helping someone who's doing wrong means stopping them from doing it! Real friends don't let friends do bad things. Be a good friend by helping people do what's right!",
            collection = "Sahih al-Bukhari",
            hadithNumber = 2444,
            reference = "Narrated by Prophet Muhammad ﷺ"
        )
    )
    
    val kidsHadithCategories = mapOf(
        "Character" to listOf("hadith_smile", "hadith_kindness", "hadith_truthful", "hadith_anger"),
        "Family" to listOf("hadith_parents", "hadith_neighbor", "hadith_helping"),
        "Good Deeds" to listOf("hadith_good_word", "hadith_best_person", "hadith_thankful"),
        "Nature & Animals" to listOf("hadith_plants", "hadith_path", "hadith_animals"),
        "Faith" to listOf("hadith_clean", "hadith_strong")
    )
    
    fun getHadithById(id: String): KidsHadith? = kidsHadithList.find { it.id == id }
    
    fun getHadithsByCategory(category: String): List<KidsHadith> {
        val ids = kidsHadithCategories[category] ?: return emptyList()
        return ids.mapNotNull { getHadithById(it) }
    }
}

// Adult Hadith Content with Full Commentary

@Serializable
data class AdultHadith(
    val id: String,
    val number: Int,
    val collection: HadithCollection,
    val book: String,
    val chapter: String,
    val arabicText: String,
    val englishTranslation: String,
    val narrator: String,
    val narratorChain: String,
    val grade: HadithGrade,
    val category: HadithCategory,
    val commentary: HadithCommentary,
    val practicalLessons: List<String>,
    val modernApplications: List<String>,
    val relatedVerses: List<String>,
    val relatedHadiths: List<String>
)

@Serializable
enum class HadithCollection {
    SAHIH_BUKHARI,
    SAHIH_MUSLIM,
    SUNAN_ABU_DAWUD,
    SUNAN_TIRMIDHI,
    SUNAN_NASAI,
    SUNAN_IBN_MAJAH,
    MUWATTA_MALIK,
    MUSNAD_AHMAD,
    FORTY_NAWAWI;
    
    val displayName: String
        get() = when (this) {
            SAHIH_BUKHARI -> "Sahih al-Bukhari"
            SAHIH_MUSLIM -> "Sahih Muslim"
            SUNAN_ABU_DAWUD -> "Sunan Abi Dawud"
            SUNAN_TIRMIDHI -> "Sunan at-Tirmidhi"
            SUNAN_NASAI -> "Sunan an-Nasa'i"
            SUNAN_IBN_MAJAH -> "Sunan Ibn Majah"
            MUWATTA_MALIK -> "Muwatta Imam Malik"
            MUSNAD_AHMAD -> "Musnad Ahmad"
            FORTY_NAWAWI -> "An-Nawawi's 40 Hadith"
        }
}

@Serializable
enum class HadithGrade {
    SAHIH,
    HASAN,
    DAIF,
    MAWDU;
    
    val displayName: String
        get() = when (this) {
            SAHIH -> "Sahih (Authentic)"
            HASAN -> "Hasan (Good)"
            DAIF -> "Da'if (Weak)"
            MAWDU -> "Mawdu' (Fabricated)"
        }
}

@Serializable
enum class HadithCategory {
    AQEEDAH,
    WORSHIP,
    TRANSACTIONS,
    CHARACTER,
    FAMILY,
    SOCIAL_RELATIONS,
    KNOWLEDGE,
    DAWAH;
    
    val displayName: String
        get() = when (this) {
            AQEEDAH -> "Aqeedah (Faith)"
            WORSHIP -> "Worship"
            TRANSACTIONS -> "Transactions & Business"
            CHARACTER -> "Character & Manners"
            FAMILY -> "Family & Marriage"
            SOCIAL_RELATIONS -> "Social Relations"
            KNOWLEDGE -> "Seeking Knowledge"
            DAWAH -> "Da'wah & Calling to Islam"
        }
}

@Serializable
data class HadithCommentary(
    val overview: String,
    val keyPoints: List<String>,
    val scholarlyInsights: List<String>,
    val linguisticNotes: String?,
    val historicalContext: String?
)

object AdultHadithContent {
    
    val authenticHadithCollection = listOf(
        AdultHadith(
            id = "nawawi_1",
            number = 1,
            collection = HadithCollection.FORTY_NAWAWI,
            book = "An-Nawawi's 40 Hadith",
            chapter = "Intention",
            arabicText = """
                إِنَّمَا الأَعْمَالُ بِالنِّيَّاتِ، وَإِنَّمَا لِكُلِّ امْرِئٍ مَا نَوَى، فَمَنْ كَانَتْ هِجْرَتُهُ إِلَى اللَّهِ وَرَسُولِهِ فَهِجْرَتُهُ إِلَى اللَّهِ وَرَسُولِهِ، وَمَنْ كَانَتْ هِجْرَتُهُ لِدُنْيَا يُصِيبُهَا أَوْ امْرَأَةٍ يَنْكِحُهَا فَهِجْرَتُهُ إِلَى مَا هَاجَرَ إِلَيْهِ
            """.trimIndent(),
            englishTranslation = """
                Actions are but by intentions, and every person will have only what they intended. So whoever's migration was for Allah and His Messenger, then his migration was for Allah and His Messenger. And whoever's migration was for worldly gain or to marry a woman, then his migration was for whatever he migrated for.
            """.trimIndent(),
            narrator = "Umar ibn al-Khattab (رضي الله عنه)",
            narratorChain = "Related by Bukhari and Muslim through multiple chains",
            grade = HadithGrade.SAHIH,
            category = HadithCategory.WORSHIP,
            commentary = HadithCommentary(
                overview = """
                    This is the first hadith in Imam An-Nawawi's collection and is considered one of the most important hadiths in Islam. Imam Ahmad and Imam Shafi'i said that this hadith is one-third of Islam, as it covers intentions (inner aspect), while the hadith "Whoever introduces something into this matter of ours..." covers actions (outer aspect), and other hadiths cover beliefs.
                    
                    This hadith establishes the fundamental principle that the acceptability and reward of all deeds depend on the intention behind them. It's placed first because it's the foundation of all actions.
                """.trimIndent(),
                keyPoints = listOf(
                    "Intention (Niyyah) is the foundation of all actions in Islam",
                    "The same physical action can be rewarded, neutral, or sinful based on intention",
                    "Intention determines whether an action is worship or mere habit",
                    "Sincerity (Ikhlas) means doing actions purely for Allah's sake",
                    "Migration (Hijrah) is used as an example because it involves leaving one's homeland, a major sacrifice",
                    "The hadith applies to ALL actions: worship, transactions, social interactions, etc."
                ),
                scholarlyInsights = listOf(
                    "Imam Ibn Rajab: This hadith is a scale by which we weigh all our actions",
                    "Imam An-Nawawi: Scholars of all schools agree on its authenticity and importance",
                    "Imam Shafi'i: This hadith covers one-third of knowledge",
                    "Imam Ahmad: Islam revolves around three hadiths, and this is one of them",
                    "The repetition in the hadith emphasizes the importance of intention",
                    "The example of migration shows how the same act can have different values"
                ),
                linguisticNotes = """
                    'Innama' (إنما) is a particle of restriction, meaning 'only' or 'nothing but', emphasizing that actions have NO VALUE without proper intention.
                    
                    'A'mal' (أعمال) is plural of 'amal', meaning all types of actions - physical, verbal, and mental.
                    
                    The structure 'faman kanat hijratuhu...' (whoever's migration...) is a conditional sentence showing direct correlation between intention and outcome.
                """,
                historicalContext = """
                    This hadith was narrated in the context of a man who migrated from Makkah to Madinah not for religious reasons, but to marry a woman known as Umm Qays. The Prophet ﷺ used this as a teaching moment about the importance of intention.
                    
                    The migration (Hijrah) from Makkah to Madinah was a central event in early Islam, representing leaving disbelief for Islam, persecution for safety, and polytheism for monotheism.
                """
            ),
            practicalLessons = listOf(
                "Before every action, check your intention: Am I doing this for Allah or for showing off?",
                "Same action (like charity) can be worship or sin depending on intention",
                "Renew your intention regularly - intentions can change mid-action",
                "Hidden actions (night prayers, secret charity) protect against ostentation",
                "Work, earning halal income, caring for family - all become worship with right intention",
                "Even permissible actions (eating, sleeping) become rewarded if done with intention to obey Allah",
                "This hadith is a cure for ostentation (riya) - the disease of doing good deeds to be seen by people"
            ),
            modernApplications = listOf(
                "Social Media: Sharing good deeds - is it for Allah or for likes and validation?",
                "Career: Are you working to provide for family (worship) or just for status and wealth?",
                "Education: Seeking knowledge to please Allah and serve His religion, or just for worldly success?",
                "Charity: Giving to genuinely help others and please Allah, or to feel superior and be praised?",
                "Religious practice: Praying, fasting, hijab - for Allah or for family/cultural pressure?",
                "Convert's challenge: Practicing Islam sincerely vs. trying to fit in with Muslim community",
                "Leadership: Volunteering in masjid/organization - serving Allah or seeking recognition?"
            ),
            relatedVerses = listOf(
                "Quran 18:110 - Say: I am only a man like you, to whom has been revealed that your god is one God. So whoever would hope for the meeting with his Lord - let him do righteous work and not associate in the worship of his Lord anyone.",
                "Quran 98:5 - And they were not commanded except to worship Allah, [being] sincere to Him in religion."
            ),
            relatedHadiths = listOf(
                "Whoever does an action that is not in accordance with our matter (religion), it is rejected - Sahih Muslim",
                "The most hated people to Allah are three... - includes one who shows off in his deeds",
                "Actions are according to their endings - the importance of maintaining intention until completion"
            )
        ),
        AdultHadith(
            id = "nawawi_2",
            number = 2,
            collection = HadithCollection.FORTY_NAWAWI,
            book = "An-Nawawi's 40 Hadith",
            chapter = "Faith, Islam, and Ihsan",
            arabicText = """
                بَيْنَمَا نَحْنُ جُلُوسٌ عِنْدَ رَسُولِ اللَّهِ صَلَّى اللَّهُ عَلَيْهِ وَسَلَّمَ ذَاتَ يَوْمٍ إِذْ طَلَعَ عَلَيْنَا رَجُلٌ شَدِيدُ بَيَاضِ الثِّيَابِ، شَدِيدُ سَوَادِ الشَّعَرِ، لَا يُرَى عَلَيْهِ أَثَرُ السَّفَرِ، وَلَا يَعْرِفُهُ مِنَّا أَحَدٌ، حَتَّى جَلَسَ إِلَى النَّبِيِّ صَلَّى اللَّهُ عَلَيْهِ وَسَلَّمَ فَأَسْنَدَ رُكْبَتَيْهِ إِلَى رُكْبَتَيْهِ، وَوَضَعَ كَفَّيْهِ عَلَى فَخِذَيْهِ، وَقَالَ: يَا مُحَمَّدُ أَخْبِرْنِي عَنْ الْإِسْلَامِ...
            """.trimIndent(),
            englishTranslation = """
                While we were sitting with the Messenger of Allah ﷺ one day, a man appeared before us with extremely white clothing and extremely black hair. No signs of travel were visible on him, and none of us knew him. He sat down before the Prophet ﷺ, resting his knees against his knees and placing his palms on his thighs, and said: "O Muhammad, tell me about Islam..."
                
                The Prophet ﷺ defined:
                - Islam: The five pillars (Shahada, Salah, Zakat, Sawm, Hajj)
                - Iman: Belief in Allah, His angels, His books, His messengers, the Last Day, and divine decree
                - Ihsan: To worship Allah as if you see Him, for even though you don't see Him, He sees you
                
                The man then left, and the Prophet said: "That was Jibril who came to teach you your religion."
            """.trimIndent(),
            narrator = "Umar ibn al-Khattab (رضي الله عنه)",
            narratorChain = "Related by Muslim",
            grade = HadithGrade.SAHIH,
            category = HadithCategory.AQEEDAH,
            commentary = HadithCommentary(
                overview = """
                    This hadith is known as "Hadith Jibril" and is considered one of the most comprehensive hadiths in Islam. It defines the three levels of religion: Islam (outward submission), Iman (inward belief), and Ihsan (spiritual excellence).
                    
                    The unique teaching method used - Angel Jibril asking questions and the Prophet answering - made the lesson memorable and emphasized that learning through questions is encouraged. The dramatic appearance and disappearance showed that this was a divine teaching moment.
                """.trimIndent(),
                keyPoints = listOf(
                    "Islam, Iman, and Ihsan are three interconnected levels of faith",
                    "Islam: The foundation - the five pillars that define Muslim identity",
                    "Iman: The beliefs - six articles of faith that must be held in the heart",
                    "Ihsan: The perfection - spiritual consciousness and excellence in worship",
                    "Religion is not just rituals - it's belief, practice, and spiritual state",
                    "The method of teaching through questions and answers is encouraged",
                    "Even angels learn and teach in visible form sometimes"
                ),
                scholarlyInsights = listOf(
                    "Imam An-Nawawi: This is one of the most important hadiths containing essential knowledge",
                    "Ibn Rajab: The hadith encompasses all sciences of Shariah - external and internal",
                    "Scholars note Jibril's respectful demeanor when sitting before the Prophet ﷺ",
                    "The progression from Islam → Iman → Ihsan shows spiritual development stages",
                    "Some scholars use different counts of pillars/articles, but the core meaning is the same",
                    "The companions' surprise at the stranger and later revelation taught them about angels"
                ),
                linguisticNotes = """
                    The description of Jibril's appearance (very white clothes, very black hair, no travel signs) emphasizes the supernatural nature of his arrival. In desert Arabia, anyone traveling would show dust and signs of journey.
                    
                    The word 'Ihsan' comes from 'husn' meaning beauty/excellence - it's about making worship beautiful and excellent.
                    
                    'Ka'annaka tarahu' (as if you see Him) uses the conditional 'ka'anna' to indicate the state of consciousness one should strive for in worship.
                """,
                historicalContext = """
                    This event occurred in Madinah when the Muslim community was established. The companions were learning the details of their religion, and this comprehensive teaching came to clarify the structure of Islamic belief and practice.
                    
                    The Prophet's companions were already practicing Muslims, but this lesson organized their understanding into clear categories that became the foundation of Islamic theology and jurisprudence.
                """
            ),
            practicalLessons = listOf(
                "Don't separate belief from practice - both are essential",
                "Aim for Ihsan in everything - not just doing it, but doing it beautifully",
                "Constant awareness of Allah's presence transforms worship from routine to spiritual experience",
                "Asking questions to learn religion is praiseworthy, not disrespectful",
                "Knowledge must be sought systematically, covering both external practices and internal states",
                "The five pillars are the minimum - excellence (Ihsan) is the goal",
                "Teachers should present knowledge clearly and comprehensively"
            ),
            modernApplications = listOf(
                "Prayer: Not just completing movements, but feeling Allah's presence in each position",
                "Business: Not just avoiding haram, but conducting all transactions with excellence and consciousness of Allah",
                "Parenting: Raising children with awareness that Allah sees how we treat them",
                "Work: Performing job duties excellently because Allah is watching, not just for salary",
                "Online behavior: Acting with Ihsan on social media - Allah sees our posts and comments",
                "Relationships: Treating spouse, family, friends with excellence for Allah's sake",
                "Personal development: Constantly improving religious knowledge and spiritual state"
            ),
            relatedVerses = listOf(
                "Quran 49:14 - The bedouins say: 'We believe.' Say: 'You do not believe; but you say, We submit.' For faith has not yet entered your hearts...",
                "Quran 16:90 - Indeed, Allah orders justice and good conduct (ihsan)..."
            ),
            relatedHadiths = listOf(
                "The one with complete faith is the one with the best character - connects Iman with behavior",
                "None of you truly believes until he loves for his brother what he loves for himself - practical Iman",
                "The best among you are those who learn the Quran and teach it - pathway to Ihsan"
            )
        ),
        AdultHadith(
            id = "bukhari_iman",
            number = 13,
            collection = HadithCollection.SAHIH_BUKHARI,
            book = "Book of Faith",
            chapter = "Excellence of Faith",
            arabicText = """
                قَالَ النَّبِيُّ صَلَّى اللَّهُ عَلَيْهِ وَسَلَّمَ: الْإِيمَانُ بِضْعٌ وَسِتُّونَ شُعْبَةً، وَالْحَيَاءُ شُعْبَةٌ مِنَ الْإِيمَانِ
            """.trimIndent(),
            englishTranslation = """
                The Prophet ﷺ said: "Faith has sixty-some branches, and modesty (haya) is a branch of faith."
            """.trimIndent(),
            narrator = "Abu Huraira (رضي الله عنه)",
            narratorChain = "Related by Bukhari and Muslim",
            grade = HadithGrade.SAHIH,
            category = HadithCategory.AQEEDAH,
            commentary = HadithCommentary(
                overview = """
                    This hadith presents faith (Iman) not as a single abstract concept, but as a tree with many branches. Each branch represents a belief, action, or character trait that strengthens overall faith.
                    
                    By specifically mentioning modesty (haya) as one branch, the Prophet ﷺ highlighted its importance and showed that faith includes moral character, not just beliefs and rituals. Haya prevents evil and encourages good.
                """.trimIndent(),
                keyPoints = listOf(
                    "Faith is not monolithic - it has multiple components",
                    "Faith can increase and decrease based on actions and character",
                    "The highest branch is saying 'La ilaha illa Allah'",
                    "The lowest branch is removing harm from people's path",
                    "Modesty (haya) is specifically mentioned as essential to faith",
                    "Both belief and action are part of faith",
                    "Character traits are integral to Islamic faith, not separate from it"
                ),
                scholarlyInsights = listOf(
                    "Different narrations mention 60+, 70+, or 77 branches - emphasizing 'many' rather than exact count",
                    "Scholars have compiled lists of the branches based on Quran and Hadith",
                    "Some branches are beliefs (heart), some are actions (limbs), some are speech (tongue)",
                    "Imam Ibn Hibban compiled a book listing all the branches of faith",
                    "The mention of haya shows that shyness/modesty in Islamic context is praiseworthy, not weakness",
                    "This hadith refutes those who separate faith from action"
                ),
                linguisticNotes = """
                    'Bid' (بضع) means 'some' or 'several' - an indeterminate number between 3 and 9.
                    
                    'Shu'bah' (شعبة) literally means 'branch' - beautifully illustrating faith as a living tree that grows and produces.
                    
                    'Haya' (حياء) is a comprehensive term including modesty, shame (in good sense), shyness from evil, and consciousness that prevents wrong.
                """,
                historicalContext = """
                    This teaching came to show that Islam is a comprehensive way of life, not limited to rituals. In a society where modesty was valued but the concept of faith was often limited to beliefs, this hadith expanded the understanding.
                    
                    The detailed explanation of faith's components helped early Muslims understand which areas of life needed improvement.
                """
            ),
            practicalLessons = listOf(
                "Work on improving different aspects of faith - beliefs, actions, character",
                "Modesty in dress, speech, and behavior is part of religious practice",
                "Small actions like removing obstacles from people's path are acts of faith",
                "Faith isn't static - nurture it like a tree by adding branches",
                "Character development is religious obligation, not just self-improvement",
                "When you feel shy to do something sinful, that's faith preventing you",
                "Assess yourself regularly: Which branches of faith are strong? Which need work?"
            ),
            modernApplications = listOf(
                "Modesty in digital age: What we post, share, and how we present ourselves online",
                "Removing harm: Reporting dangerous situations, helping fix community problems",
                "Faith includes environmental consciousness - not littering is a branch of faith",
                "Modest dress in professional settings while maintaining religious identity",
                "Haya in relationships: Maintaining boundaries, respecting privacy, avoiding obscenity",
                "Using social media with modesty - not oversharing, maintaining dignity",
                "In diverse workplaces: Demonstrating Islamic character through actions, not just stating beliefs"
            ),
            relatedVerses = listOf(
                "Quran 7:26 - And the clothing of righteousness - that is best.",
                "Quran 24:30-31 - Lower your gaze and guard your modesty..."
            ),
            relatedHadiths = listOf(
                "Modesty brings nothing but good - emphasizes positive nature of haya",
                "Removing harmful things from the road is charity - connecting faith to public service",
                "La ilaha illa Allah is the best branch - importance of Tawheed"
            )
        )
    )
    
    fun getHadithById(id: String): AdultHadith? = authenticHadithCollection.find { it.id == id }
    
    fun getHadithsByCollection(collection: HadithCollection): List<AdultHadith> {
        return authenticHadithCollection.filter { it.collection == collection }
    }
    
    fun getHadithsByCategory(category: HadithCategory): List<AdultHadith> {
        return authenticHadithCollection.filter { it.category == category }
    }
    
    fun getHadithsByGrade(grade: HadithGrade): List<AdultHadith> {
        return authenticHadithCollection.filter { it.grade == grade }
    }
}
