package com.deenlearn.app.models

import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
data class Surah(
    val id: Int,
    val name: String,
    val nameArabic: String,
    val englishMeaning: String,
    val revelationType: RevelationType,
    val verseCount: Int,
    val juz: List<Int>,
    val page: Int,
    val rukus: Int,
    val tafsir: SurahTafsir? = null
) {
    val kidsEmoji: String
        get() = when (id) {
            1 -> "📖"
            112 -> "☝️"
            113 -> "🌅"
            114 -> "👥"
            108 -> "💧"
            110 -> "🏆"
            111 -> "🔥"
            109 -> "🚫"
            107 -> "🤲"
            105 -> "🐘"
            106 -> "❄️"
            103 -> "⏰"
            102 -> "💰"
            101 -> "⚖️"
            100 -> "🐎"
            else -> "📜"
        }
}

@Serializable
data class SurahTafsir(
    val overview: String,
    val themes: List<String>,
    val context: SurahContext,
    val keyVerses: List<KeyVerse>,
    val linguisticFeatures: List<String>,
    val practicalLessons: List<String>,
    val reflectionQuestions: List<String>,
    val memorizationTips: List<String>
)

@Serializable
data class SurahContext(
    val period: String,
    val occasion: String,
    val audience: String,
    val mainPurpose: String
)

@Serializable
data class KeyVerse(
    val verseNumber: Int,
    val arabic: String,
    val translation: String,
    val commentary: String,
    val application: String
)

@Serializable
enum class RevelationType {
    MECCAN,
    MEDINAN;
    
    val displayName: String
        get() = when (this) {
            MECCAN -> "Meccan"
            MEDINAN -> "Medinan"
        }
}

@Serializable
data class Ayah(
    val id: String,
    val surahId: Int,
    val ayahNumber: Int,
    val arabic: String,
    val transliteration: String,
    val translation: String,
    val words: List<QuranWord>,
    val sajdahType: SajdahType?,
    val juz: Int,
    val page: Int,
    val audioFileName: String?,
    val audioURL: String? = null
)

@Serializable
enum class SajdahType {
    RECOMMENDED,
    OBLIGATORY;
    
    val displayName: String
        get() = when (this) {
            RECOMMENDED -> "Recommended"
            OBLIGATORY -> "Obligatory"
        }
}

@Serializable
data class QuranWord(
    val id: String,
    val arabic: String,
    val transliteration: String,
    val translation: String,
    val rootWord: String?,
    val rootMeaning: String?,
    val tajweedRules: List<TajweedRule>,
    val audioFileName: String?
)

@Serializable
data class TajweedRule(
    val id: String,
    val name: String,
    val nameArabic: String,
    val colorHex: String,
    val description: String,
    val example: String,
    val audioFileName: String?
)

@Serializable
data class MemorizationProgress(
    val id: String,
    val surahId: Int,
    val totalAyahs: Int,
    val ayahsMemorized: Set<Int>,
    @Serializable(with = DateSerializer::class)
    val lastPracticed: Date,
    val strength: Double,
    val totalReviews: Int
) {
    val percentComplete: Double
        get() = if (totalAyahs > 0) {
            ayahsMemorized.size.toDouble() / totalAyahs.toDouble()
        } else {
            0.0
        }
}

@Serializable
data class QuranBookmark(
    val id: String,
    val surahId: Int,
    val ayahNumber: Int,
    @Serializable(with = DateSerializer::class)
    val createdAt: Date,
    val note: String?,
    val colorHex: String
)

@Serializable
data class JuzAmmaAdventure(
    val id: Int,
    val surahName: String,
    val surahNameArabic: String,
    val emoji: String,
    val storyTheme: String,
    val difficulty: DifficultyLevel,
    val reward: String,
    val isUnlocked: Boolean,
    val isCompleted: Boolean,
    val starsEarned: Int
)

@Serializable
enum class DifficultyLevel {
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

object QuranData {
    val juzAmmaSurahs = listOf(
        Surah(78, "An-Naba", "النبأ", "The Tidings", RevelationType.MECCAN, 40, listOf(30), 582, 2),
        Surah(79, "An-Nazi'at", "النازعات", "Those Who Pull Out", RevelationType.MECCAN, 46, listOf(30), 583, 2),
        Surah(80, "Abasa", "عبس", "He Frowned", RevelationType.MECCAN, 42, listOf(30), 585, 1),
        Surah(81, "At-Takwir", "التكوير", "The Overthrowing", RevelationType.MECCAN, 29, listOf(30), 586, 1),
        Surah(82, "Al-Infitar", "الإنفطار", "The Cleaving", RevelationType.MECCAN, 19, listOf(30), 587, 1),
        Surah(83, "Al-Mutaffifin", "المطففين", "The Defrauders", RevelationType.MECCAN, 36, listOf(30), 587, 1),
        Surah(84, "Al-Inshiqaq", "الإنشقاق", "The Splitting Open", RevelationType.MECCAN, 25, listOf(30), 589, 1),
        Surah(85, "Al-Buruj", "البروج", "The Mansions of the Stars", RevelationType.MECCAN, 22, listOf(30), 590, 1),
        Surah(86, "At-Tariq", "الطارق", "The Nightcomer", RevelationType.MECCAN, 17, listOf(30), 591, 1),
        Surah(87, "Al-A'la", "الأعلى", "The Most High", RevelationType.MECCAN, 19, listOf(30), 591, 1),
        Surah(88, "Al-Ghashiyah", "الغاشية", "The Overwhelming", RevelationType.MECCAN, 26, listOf(30), 592, 1),
        Surah(89, "Al-Fajr", "الفجر", "The Dawn", RevelationType.MECCAN, 30, listOf(30), 593, 1),
        Surah(90, "Al-Balad", "البلد", "The City", RevelationType.MECCAN, 20, listOf(30), 594, 1),
        Surah(91, "Ash-Shams", "الشمس", "The Sun", RevelationType.MECCAN, 15, listOf(30), 595, 1),
        Surah(92, "Al-Layl", "الليل", "The Night", RevelationType.MECCAN, 21, listOf(30), 595, 1),
        Surah(93, "Ad-Dhuha", "الضحى", "The Morning Hours", RevelationType.MECCAN, 11, listOf(30), 596, 1),
        Surah(94, "Ash-Sharh", "الشرح", "The Relief", RevelationType.MECCAN, 8, listOf(30), 596, 1),
        Surah(95, "At-Tin", "التين", "The Fig", RevelationType.MECCAN, 8, listOf(30), 597, 1),
        Surah(96, "Al-Alaq", "العلق", "The Clot", RevelationType.MECCAN, 19, listOf(30), 597, 1),
        Surah(97, "Al-Qadr", "القدر", "The Power", RevelationType.MECCAN, 5, listOf(30), 598, 1),
        Surah(98, "Al-Bayyinah", "البينة", "The Clear Proof", RevelationType.MEDINAN, 8, listOf(30), 598, 1),
        Surah(99, "Az-Zalzalah", "الزلزلة", "The Earthquake", RevelationType.MEDINAN, 8, listOf(30), 599, 1),
        Surah(100, "Al-Adiyat", "العاديات", "The Courser", RevelationType.MECCAN, 11, listOf(30), 599, 1),
        Surah(101, "Al-Qari'ah", "القارعة", "The Calamity", RevelationType.MECCAN, 11, listOf(30), 600, 1),
        Surah(102, "At-Takathur", "التكاثر", "The Rivalry in Worldly Increase", RevelationType.MECCAN, 8, listOf(30), 600, 1),
        Surah(103, "Al-Asr", "العصر", "The Declining Day", RevelationType.MECCAN, 3, listOf(30), 601, 1),
        Surah(104, "Al-Humazah", "الهمزة", "The Traducer", RevelationType.MECCAN, 9, listOf(30), 601, 1),
        Surah(105, "Al-Fil", "الفيل", "The Elephant", RevelationType.MECCAN, 5, listOf(30), 601, 1),
        Surah(106, "Quraysh", "قريش", "Quraysh", RevelationType.MECCAN, 4, listOf(30), 602, 1),
        Surah(107, "Al-Ma'un", "الماعون", "The Small Kindnesses", RevelationType.MECCAN, 7, listOf(30), 602, 1),
        Surah(108, "Al-Kawthar", "الكوثر", "The Abundance", RevelationType.MECCAN, 3, listOf(30), 602, 1),
        Surah(109, "Al-Kafirun", "الكافرون", "The Disbelievers", RevelationType.MECCAN, 6, listOf(30), 603, 1),
        Surah(110, "An-Nasr", "النصر", "The Divine Support", RevelationType.MEDINAN, 3, listOf(30), 603, 1),
        Surah(111, "Al-Masad", "المسد", "The Palm Fiber", RevelationType.MECCAN, 5, listOf(30), 603, 1),
        Surah(112, "Al-Ikhlas", "الإخلاص", "The Sincerity", RevelationType.MECCAN, 4, listOf(30), 604, 1),
        Surah(113, "Al-Falaq", "الفلق", "The Daybreak", RevelationType.MECCAN, 5, listOf(30), 604, 1),
        Surah(114, "An-Nas", "الناس", "Mankind", RevelationType.MECCAN, 6, listOf(30), 604, 1),
        Surah(1, "Al-Fatiha", "الفاتحة", "The Opening", RevelationType.MECCAN, 7, listOf(1), 1, 1)
    )
    
    // Kids-friendly surah stories and lessons
    val surahStories = mapOf(
        1 to SurahStory(
            title = "The Opening - Our Daily Prayer",
            kidsExplanation = """
                Al-Fatiha is the very first chapter of the Quran! It's super special because we recite it in every single prayer. 
                
                Think of it like this: When you meet someone important, you say "hello" and introduce yourself, right? Al-Fatiha is like our special way of saying hello to Allah before we talk to Him in prayer!
                
                In this surah, we praise Allah, ask Him to guide us on the right path, and ask Him to keep us away from the wrong path. It's short but very powerful!
                
                Prophet Muhammad ﷺ said this surah is the greatest chapter in the Quran! Every time you pray, you get to say this special surah.
            """.trimIndent(),
            funFact = "This surah has 7 verses, and it has many names! It's called 'Al-Fatiha' (The Opening), 'Um Al-Kitab' (Mother of the Book), and 'Ash-Shifa' (The Healing)!",
            lesson = "Always start with praising Allah and asking for His guidance. When we ask Allah to guide us, He will help us make good choices!",
            memorytips = "Remember: Praise Allah → Ask for guidance → Stay away from bad paths. It's like a map for life!"
        ),
        112 to SurahStory(
            title = "Al-Ikhlas - Allah is One",
            kidsExplanation = """
                Al-Ikhlas means "sincerity" and it's one of the shortest but most important surahs!
                
                Imagine someone asking you: "Who is Allah?" This surah is the perfect answer! It tells us that:
                1. Allah is One and Only
                2. Allah doesn't need anyone or anything (but we all need Him!)
                3. No one is born from Allah, and He wasn't born from anyone
                4. There is absolutely nothing like Allah!
                
                Prophet Muhammad ﷺ said that reading this surah is equal to reading one-third of the Quran! That's how special it is!
                
                This surah teaches us the most important belief in Islam - Tawheed (believing in One Allah).
            """.trimIndent(),
            funFact = "Even though it's only 4 verses, the Prophet said reciting it is like reciting 1/3 of the whole Quran! That's amazing!",
            lesson = "Allah is One and unique. There's no one like Him, and we should worship Him alone. This is the most important thing to believe!",
            memorytips = "It's short and easy! Say it before going to sleep for protection. Remember: One Allah, needs nobody, born from nobody, and nobody is like Him!"
        ),
        113 to SurahStory(
            title = "Al-Falaq - Protection from Evil",
            kidsExplanation = """
                Have you ever felt scared of the dark? Or worried about bad dreams? Al-Falaq is like a shield that protects us!
                
                This surah teaches us to seek protection with Allah from:
                - The evil of everything He created (all bad things)
                - The darkness of the night (scary times)
                - Those who practice harmful magic
                - People who are jealous of us
                
                When Prophet Muhammad ﷺ was sick or scared, he would recite this surah and rub his hands over his body for protection. It's like a protective bubble from Allah!
                
                The best times to recite it are: Before sleeping, when you're scared, in the morning and evening, and when you're sick.
            """.trimIndent(),
            funFact = "This surah is called one of 'Al-Mu'awwidhatayn' (the two protective surahs) along with An-Nas. They're like a shield duo!",
            lesson = "When you're scared or worried, remember that Allah is the best protector! Turn to Him and He will keep you safe.",
            memorytips = "Think: Protection + Morning + Night = Shield! Say it 3 times in the morning and 3 times at night for maximum protection!"
        ),
        114 to SurahStory(
            title = "An-Nas - Protection from Whispers",
            kidsExplanation = """
                An-Nas means "Mankind" and it's the last surah in the Quran! It's all about protecting ourselves from Shaytan's whispers.
                
                You know that little voice in your head that sometimes tells you to do something bad? "Skip your prayer," "Lie to your parents," "Be mean to your friend"? That's Shaytan whispering!
                
                This surah teaches us to seek refuge with Allah from:
                - The whisperer who runs away (Shaytan)
                - Who whispers into people's hearts
                - From both jinn and humans who have evil intentions
                
                Allah is called "Lord of Mankind," "King of Mankind," and "God of Mankind" - meaning He has complete power to protect us!
            """.trimIndent(),
            funFact = "The last two surahs (Al-Falaq and An-Nas) are called 'Al-Mu'awwidhatayn.' The Prophet loved them so much, he recited them every single night!",
            lesson = "Shaytan tries to make us do bad things by whispering to us. When you feel a bad thought, say 'A'udhu billah' (I seek refuge in Allah) and the whisper will stop!",
            memorytips = "Remember the three names of Allah in this surah: Lord, King, and God of Mankind. He protects us from all bad whispers!"
        ),
        105 to SurahStory(
            title = "Al-Fil - The Elephant Story",
            kidsExplanation = """
                This surah tells an amazing true story that happened the year Prophet Muhammad ﷺ was born!
                
                There was a mean king named Abraha who built a big, beautiful church. He wanted everyone to visit his church instead of the Ka'bah in Makkah. But nobody came!
                
                Abraha got angry and decided to destroy the Ka'bah. He brought a huge army and big elephants!  But when the elephant leader reached Makkah, it sat down and refused to move toward the Ka'bah!
                
                Then, something amazing happened! Allah sent tiny birds carrying small stones. These birds dropped the stones on Abraha's army, and the army was destroyed! Allah protected His special house!
                
                This shows that no matter how big and strong someone is, Allah is stronger and can protect what He wants to protect!
            """.trimIndent(),
            funFact = "The year this happened is called 'The Year of the Elephant' and it's the same year Prophet Muhammad ﷺ was born! Allah was protecting the Ka'bah for His last Prophet!",
            lesson = "Never underestimate Allah's power! Even tiny birds can defeat big elephants if Allah wills. Always trust that Allah will protect what is right!",
            memorytips = "Remember: Big elephant + Tiny birds = Allah's Power! Size doesn't matter when Allah is protecting something!"
        ),
        108 to SurahStory(
            title = "Al-Kawthar - The Abundance",
            kidsExplanation = """
                Al-Kawthar is the shortest surah in the Quran - only 3 verses! But it has a beautiful message.
                
                When Prophet Muhammad ﷺ lost his son, some mean people said he had nothing. They made fun of him. But Allah revealed this surah to comfort him!
                
                Allah told the Prophet: "We have given you Al-Kawthar (abundance/a river in Paradise)!" So even though people said he had nothing, Allah gave him SO MUCH!
                
                Al-Kawthar is also the name of a special river in Paradise. It's whiter than milk, sweeter than honey, and whoever drinks from it will never be thirsty again!
                
                This surah teaches us that Allah's gifts are better than anything people can give or take away.
            """.trimIndent(),
            funFact = "The river Al-Kawthar in Paradise is so special that its mud smells like musk (the best perfume ever)! And the Prophet ﷺ will give us drinks from it!",
            lesson = "Even when people are mean to you or you lose something, remember that Allah can give you something much better! His gifts are the best gifts!",
            memorytips = "Super short! Just 3 verses! Remember: Allah gives abundance → Pray and sacrifice → Those who hate you have nothing!"
        ),
        103 to SurahStory(
            title = "Al-Asr - Time is Precious",
            kidsExplanation = """
                Al-Asr means "time" and this tiny surah gives us the secret to success in life!
                
                Allah swears by time (which shows it's very important!) and tells us that all humans are losing... except those who do 4 things:
                
                1. BELIEVE in Allah
                2. DO GOOD DEEDS (help others, pray, be kind)
                3. REMIND each other about truth (tell your friends to do good)
                4. REMIND each other to be patient (help friends when things are hard)
                
                Imam Shafi'i said: If Allah only revealed this surah, it would be enough for all of humanity! That's how important it is!
                
                Time is like a river - it keeps flowing and never comes back. We need to use our time wisely!
            """.trimIndent(),
            funFact = "This is only 3 verses but contains the whole recipe for success! The companions of the Prophet ﷺ would recite this surah before leaving each other!",
            lesson = "Don't waste time! Every day is a gift from Allah. Use it to believe in Allah, do good things, and help your friends do good too!",
            memorytips = "4 steps to success: 1) Believe 2) Do good 3) Tell others about good 4) Be patient. Easy to remember!"
        ),
        109 to SurahStory(
            title = "Al-Kafirun - I Don't Worship What You Worship",
            kidsExplanation = """
                This surah was revealed when some people from Makkah tried to make a deal with Prophet Muhammad ﷺ. They said: "How about we worship Allah for one year, and you worship our idols for one year?"
                
                That sounds fair, right? NO! Because worshipping anyone other than Allah is the biggest sin! So Allah revealed this surah telling the Prophet to say:
                
                "I don't worship what you worship, and you don't worship what I worship. I will never worship what you worship, and you'll never worship what I worship. You have your religion, and I have mine!"
                
                This surah teaches us to be strong in our beliefs. We respect others, but we never change what Allah told us to believe!
            """.trimIndent(),
            funFact = "The Prophet ﷺ loved to recite this surah in his sunnah prayer before sleeping because it clearly states his commitment to worshipping Allah alone!",
            lesson = "Stand firm in your faith! Never compromise on worshipping only Allah, even if people try to convince you otherwise. Be respectful but confident!",
            memorytips = "The whole surah repeats the message: Your way is your way, my way is my way, and I only worship Allah! Clear and strong!"
        ),
        110 to SurahStory(
            title = "An-Nasr - The Victory",
            kidsExplanation = """
                This was one of the last surahs revealed to Prophet Muhammad ﷺ! It talks about when Allah's help comes and victory arrives.
                
                After many years of struggle, the Prophet ﷺ and Muslims finally conquered Makkah peacefully! People started accepting Islam in large groups. It was amazing!
                
                But this surah also told the Prophet something bittersweet: His mission was nearly complete. After this surah was revealed, the Prophet ﷺ lived for only about 2 more months.
                
                The surah tells us: When you achieve success and victory, don't forget to praise Allah and ask His forgiveness! Success can make us forget Allah, so we must always thank Him.
                
                Prophet Muhammad ﷺ would say "SubhanAllah wa bihamdihi, Astaghfirullah wa atubu ilayk" all the time after this!
            """.trimIndent(),
            funFact = "When Ibn Abbas (a companion) saw this surah, he cried because he understood it meant the Prophet's time on Earth was ending. Other companions didn't realize it!",
            lesson = "When you succeed or win something, immediately thank Allah and ask for forgiveness! Success comes from Allah, not from us. Stay humble!",
            memorytips = "Remember: Victory comes → People accept Islam → Thank Allah → Ask forgiveness. Success = more worship, not less!"
        )
    )
    
    data class SurahStory(
        val title: String,
        val kidsExplanation: String,
        val funFact: String,
        val lesson: String,
        val memorytips: String
    )
    
    fun getSurahStory(surahId: Int): SurahStory? = surahStories[surahId]
    
    // Adult Tafsir Content for Major Surahs
    val surahTafsirs = mapOf(
        1 to SurahTafsir(
            overview = """
                Al-Fatiha (The Opening) is the greatest surah of the Quran according to authentic hadith. It is called 'Umm al-Kitab' (Mother of the Book) and 'As-Sab' al-Mathani' (The Seven Oft-Repeated Verses). 
                
                This surah is the foundation of the relationship between the servant and the Lord. It contains three fundamental elements: praise and glorification of Allah, acknowledgment of His exclusive right to be worshipped and sought for help, and supplication for guidance.
                
                The Prophet ﷺ said: "By Him in Whose Hand my soul is! Nothing like it (Al-Fatiha) has been revealed in the Torah, the Gospel, the Zabur, nor in the Furqan (Quran)." [Tirmidhi]
            """.trimIndent(),
            themes = listOf(
                "Tawheed: Oneness of Allah in His names, attributes, and worship",
                "Divine attributes: The Most Merciful, Master of the Day of Judgment",
                "Exclusive worship and exclusive seeking of help belong to Allah alone",
                "The importance of being guided to the Straight Path",
                "Warning against deviation: the path of those who earned anger and went astray"
            ),
            context = SurahContext(
                period = "Meccan period",
                occasion = "Early revelation, established as a pillar of Salah",
                audience = "All believers and seekers of truth",
                mainPurpose = "To establish the framework of proper worship and supplication to Allah"
            ),
            keyVerses = listOf(
                KeyVerse(
                    verseNumber = 5,
                    arabic = "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ",
                    translation = "You alone we worship, and You alone we ask for help.",
                    commentary = "This verse represents the core of Tawheed. By saying 'You ALONE we worship,' we affirm that no one shares in Allah's divinity. 'We ask for help' acknowledges our absolute need for Allah and our complete inability without Him. The pronoun 'we' indicates communal worship, emphasizing the importance of unity in faith.",
                    application = "In every action, especially worship, check your intention - are you doing it for Allah alone? In every difficulty, turn first to Allah before turning to creation."
                ),
                KeyVerse(
                    verseNumber = 6,
                    arabic = "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ",
                    translation = "Guide us to the straight path.",
                    commentary = "We ask for guidance at least 17 times daily in our prayers because guidance is not a one-time event but a continuous need. The straight path is the path of true knowledge and righteous deeds, balanced between extremes.",
                    application = "Before making decisions, recite Al-Fatiha with focus and ask Allah to guide you. Study the lives of the righteous who walked this path successfully."
                ),
                KeyVerse(
                    verseNumber = 7,
                    arabic = "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ",
                    translation = "The path of those upon whom You have bestowed favor, not of those who have earned anger or of those who are astray.",
                    commentary = "Allah clarifies the straight path by defining who walked it (the prophets, truthful ones, martyrs, and righteous) and who deviated from it. 'Those who earned anger' refers to people who knew the truth but rejected it out of arrogance (traditionally understood as Jews who rejected Jesus and Muhammad). 'Those who are astray' refers to people who deviated due to ignorance and misguidance (traditionally understood as Christians who deviated in their understanding of Jesus).",
                    application = "Study the lives of prophets and companions. Avoid the two extremes: arrogance that prevents accepting truth, and ignorance that leads to innovation in religion."
                )
            ),
            linguisticFeatures = listOf(
                "The word 'Ar-Rahman' (Most Merciful) appears after 'Bismillah' and again in verse 3, emphasizing Allah's mercy as His dominant attribute",
                "The shift from third person (Him) to second person (You) in verse 5 indicates increased intimacy in the conversation with Allah",
                "The use of plural 'we' throughout indicates communal identity and shared responsibility in worship",
                "The structure mirrors a complete conversation: Opening with praise → Acknowledging need → Making request → Specifying the request"
            ),
            practicalLessons = listOf(
                "Begin everything with 'Bismillah' (In the name of Allah) following the pattern of Al-Fatiha",
                "Balance praise of Allah with your requests - don't only make dua when in need",
                "Reflect on each phrase when reciting in Salah - the Prophet described prayer as a conversation between you and Allah",
                "Recognize that guidance is an ongoing journey, not a destination - even the guided need to ask for more guidance",
                "Study both the correct path and the deviant paths to protect yourself from going astray"
            ),
            reflectionQuestions = listOf(
                "Do you truly believe that Allah alone deserves worship, or do you sometimes seek approval, validation, or help from creation as if they have independent power?",
                "When you say 'Guide us to the straight path,' what specific guidance are you seeking in your current life circumstances?",
                "Are you actively learning about the straight path through studying Quran, Hadith, and the lives of righteous predecessors?",
                "Have you identified any areas where you might be following either 'those who earned anger' (rejecting truth you know) or 'those who are astray' (following innovation)?",
                "How can you increase your khushu' (consciousness) when reciting this surah 17+ times daily in your prayers?"
            ),
            memorizationTips = listOf(
                "Already memorized by most Muslims! Focus now on understanding each word's meaning",
                "Learn the sentence structure: Praise (1-4) → Worship (5) → Request (6-7)",
                "Connect each verse to a practical application in your daily life",
                "Reflect on one verse per day and write your thoughts about it",
                "Listen to different reciters to appreciate the various allowed styles of recitation"
            )
        ),
        112 to SurahTafsir(
            overview = """
                Surah Al-Ikhlas (The Sincerity/The Purity) is described by the Prophet ﷺ as equivalent to one-third of the Quran. It was revealed in response to polytheists who asked, "Describe your Lord to us."
                
                This surah presents the purest description of Allah's nature and attributes, refuting all forms of shirk (polytheism), anthropomorphism (giving Allah human attributes), and false beliefs about divinity. In just four verses, it establishes the complete concept of Tawheed.
                
                The Prophet ﷺ said: "Read 'Qul Huwallahu Ahad' because it is equivalent to one-third of the Quran." [Bukhari]
            """.trimIndent(),
            themes = listOf(
                "Absolute Oneness of Allah (Tawheed)",
                "Allah's complete self-sufficiency (He needs nothing)",
                "Negation of any beginning or end to Allah's existence",
                "Absolute uniqueness - nothing and no one is comparable to Allah",
                "Refutation of shirk in all its forms"
            ),
            context = SurahContext(
                period = "Meccan period",
                occasion = "Revealed in response to polytheists questioning about Allah's nature",
                audience = "Response to polytheists, but guidance for all believers",
                mainPurpose = "To establish pure Tawheed and refute all forms of shirk and false beliefs about Allah"
            ),
            keyVerses = listOf(
                KeyVerse(
                    verseNumber = 1,
                    arabic = "قُلْ هُوَ اللَّهُ أَحَدٌ",
                    translation = "Say, 'He is Allah, [who is] One.'",
                    commentary = "'Ahad' (One) is stronger than 'Wahid' (one). It means One in His essence, attributes, and actions - there is no division, multiplication, or partnership in His divinity. This is not merely numerical oneness but absolute uniqueness in every aspect.",
                    application = "In every act of worship, consciously affirm that you're doing it for Allah alone. Check if you're seeking anyone's pleasure alongside Allah's pleasure."
                ),
                KeyVerse(
                    verseNumber = 2,
                    arabic = "اللَّهُ الصَّمَدُ",
                    translation = "Allah, the Eternal Refuge.",
                    commentary = "'As-Samad' has multiple meanings: The Master who is resorted to in times of need; The One who is perfect in all His attributes; The One who needs nothing while everything needs Him; The Self-Sufficient. All creation depends on Him, but He depends on nothing.",
                    application = "In times of need, turn directly to Allah before turning to anyone else. Remember that while we can seek help from others as means, only Allah has independent power to help."
                ),
                KeyVerse(
                    verseNumber = 3,
                    arabic = "لَمْ يَلِدْ وَلَمْ يُولَدْ",
                    translation = "He neither begets nor is born.",
                    commentary = "This negates Christian beliefs about God having a son, and Arab polytheistic beliefs about angels being Allah's daughters. Being born implies having a beginning, and giving birth implies need and imperfection. Allah is free from all such deficiencies.",
                    application = "Understand that the Islamic concept of God is fundamentally different from other religions. Never use expressions that imply Allah has family relationships."
                ),
                KeyVerse(
                    verseNumber = 4,
                    arabic = "وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ",
                    translation = "Nor is there to Him any equivalent.",
                    commentary = "Nothing in creation resembles Allah in His essence, attributes, or actions. This negates all forms of comparison, anthropomorphism, and attempts to understand Allah through human concepts or created things.",
                    application = "When you read about Allah's attributes in Quran (Hand, Face, etc.), affirm them without trying to understand 'how' or comparing them to creation."
                )
            ),
            linguisticFeatures = listOf(
                "'Qul' (Say) is a command to the Prophet to proclaim this, emphasizing the importance of verbally affirming Tawheed",
                "The definite article 'Al' in 'Al-Ahad' emphasizes exclusivity and uniqueness",
                "Negative particles 'Lam' (did not) emphasize eternal negation - these things never happened and will never happen",
                "The word order in Arabic places maximum emphasis on the negation and uniqueness of Allah"
            ),
            practicalLessons = listOf(
                "Recite this surah regularly to purify your concept of Tawheed from any subtle forms of shirk",
                "Use this surah as a test: Can you explain Islam's concept of God to a non-Muslim using these four verses?",
                "Before sleeping, recite this surah three times for protection and reward",
                "Teach this surah to children early, as it establishes the foundation of Islamic belief",
                "Reflect on the completeness: This surah answers 'Who is Allah?' with perfect precision in just four verses"
            ),
            reflectionQuestions = listOf(
                "Do you have any beliefs about Allah that contradict His absolute uniqueness and perfection?",
                "Are there any created things or people you turn to with the level of hope and fear that should only be for Allah?",
                "Do you truly believe Allah needs absolutely nothing, or do you sometimes think your worship benefits Him?",
                "How would you explain the concept of Tawheed to a non-Muslim using this surah?",
                "Are there any innovations in your worship that imply others share in Allah's divinity?"
            ),
            memorizationTips = listOf(
                "Only 4 verses! Memorize in one sitting",
                "Understand each word's deep meaning - this aids retention",
                "Connect each verse to a particular false belief it refutes",
                "Recite it in every prayer for one week to cement it",
                "Teach it to someone else - teaching reinforces memorization"
            )
        )
    )
    
    fun getSurahTafsir(surahId: Int): SurahTafsir? = surahTafsirs[surahId]
}
