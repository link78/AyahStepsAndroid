package com.deenlearn.app.models

import kotlinx.serialization.Serializable

@Serializable
data class Pillar(
    val id: String,
    val number: Int,
    val name: String,
    val nameArabic: String,
    val icon: String,
    val colorHex: String,
    val worldEmoji: String,
    val description: String,
    val storyEpisodes: List<StoryEpisode>,
    val miniGames: List<MiniGame>,
    val definition: String,
    val quranEvidence: List<Evidence>,
    val hadithEvidence: List<Evidence>,
    val wisdom: String,
    val practicalApplication: String,
    val scenarios: List<Scenario>,
    val fiqhDifferences: List<FiqhDifference>,
    val kidsHadiths: List<KidsHadith>
)

@Serializable
data class StoryEpisode(
    val id: String,
    val title: String,
    val narrator: String,
    val content: String,
    val emoji: String,
    val duration: Int
)

@Serializable
data class MiniGame(
    val id: String,
    val title: String,
    val type: MiniGameType,
    val description: String,
    val icon: String
)

@Serializable
enum class MiniGameType {
    MATCH_PILLAR_MEANING,
    FIX_BROKEN_PILLAR,
    ORDER_THE_PILLARS,
    QUIZ_TIME
}

@Serializable
data class Evidence(
    val id: String,
    val arabic: String,
    val translation: String,
    val reference: String
)

@Serializable
data class Scenario(
    val id: String,
    val question: String,
    val answer: String,
    val category: String
)

@Serializable
data class FiqhDifference(
    val id: String,
    val topic: String,
    val hanafi: String,
    val maliki: String,
    val shafii: String,
    val hanbali: String
)

object PillarData {
    val shahadaStories = listOf(
        StoryEpisode(
            id = "shahada_1",
            title = "The Words That Changed the World",
            narrator = "Teacher Ali",
            content = """
                Once upon a time, there was a kind man named Bilal. He was treated badly by his master, but Bilal had a special secret in his heart - he believed in Allah! 
                
                Every day, even when things were hard, Bilal would say "Ahad, Ahad" (One, One) - meaning Allah is One. This is part of the Shahada, the most important words in Islam!
                
                The Shahada means: "I bear witness that there is no god but Allah, and Muhammad is His messenger."
                
                When you say these special words and truly believe them in your heart, you become a Muslim! It's like getting a special ticket to Paradise, but you have to live by these words every day.
                
                Just like Bilal, we should remember Allah in good times and hard times. The Shahada reminds us that Allah is always with us!
            """.trimIndent(),
            emoji = "☝️",
            duration = 180
        ),
        StoryEpisode(
            id = "shahada_2",
            title = "The Boy Who Loved to Say the Shahada",
            narrator = "Sister Fatima",
            content = """
                There was a young boy named Umar who loved saying the Shahada. Every morning when he woke up, he would say it. Before he went to sleep, he would say it. 
                
                One day, his little sister asked, "Why do you say these words so much?"
                
                Umar smiled and said, "Because these are the most special words ever! They remind me that Allah made everything - the stars, the moon, the trees, and even us!"
                
                "And they remind me that Prophet Muhammad (peace be upon him) taught us how to be good people, how to pray, how to be kind to others, and how to love Allah."
                
                From that day on, his little sister started saying the Shahada too, and it made them both feel happy and close to Allah!
                
                Remember: The Shahada is not just words we say - it's a promise we make to live as good Muslims!
            """.trimIndent(),
            emoji = "🌟",
            duration = 200
        )
    )
    
    val salahStories = listOf(
        StoryEpisode(
            id = "salah_1",
            title = "The Best Time of Day",
            narrator = "Imam Hassan",
            content = """
                Little Aisha loved spending time with her grandmother. One day she asked, "Grandma, what's your favorite time of day?"
                
                Grandma smiled and said, "Prayer time! When I pray, I talk directly to Allah. It's like having a special phone line to the King of the Universe!"
                
                "Really?" said Aisha, "But we pray five times every day. Isn't that a lot?"
                
                "Think about it," Grandma explained, "If your best friend wanted to talk to you five times a day, wouldn't that make you happy? Allah is better than any friend! He loves when we pray to Him."
                
                "In prayer, we stand before Allah, bow to show respect, and put our forehead on the ground to show that He is the Greatest. It's the best gift Allah gave us!"
                
                From that day, Aisha looked forward to prayer time, knowing she was talking to Allah, the Most Merciful!
            """.trimIndent(),
            emoji = "🤲",
            duration = 190
        ),
        StoryEpisode(
            id = "salah_2",
            title = "The Boy Who Never Missed Fajr",
            narrator = "Uncle Ibrahim",
            content = """
                Young Zayd loved sleeping, but he never missed Fajr prayer! His friends asked, "How do you wake up so early?"
                
                Zayd said, "My dad told me that angels take turns watching over us. The day angels and night angels meet at Fajr time! When we pray Fajr, the angels report to Allah that we were praying!"
                
                "Wow!" his friends said. "That's amazing!"
                
                "And there's more," Zayd continued. "Prophet Muhammad said that the morning prayer is protected by Allah. Anyone who prays Fajr is under Allah's special care for the whole day!"
                
                "Plus," Zayd grinned, "When I wake up for Fajr, I get to see the beautiful sunrise Allah makes every morning. It's like Allah is painting the sky just for those who wake up early!"
                
                Remember: Prayer is our connection to Allah. Five times a day, we get to talk to Him!
            """.trimIndent(),
            emoji = "🌅",
            duration = 210
        )
    )
    
    val zakatStories = listOf(
        StoryEpisode(
            id = "zakat_1",
            title = "The Generous Baker",
            narrator = "Sister Khadija",
            content = """
                In a small village lived a baker named Abdullah. Every year, he would count his earnings and give 2.5% to charity - this is called Zakat!
                
                One year, a child asked, "Mr. Abdullah, why do you give away your money?"
                
                Abdullah knelt down and explained, "Everything we have is actually a gift from Allah! The flour, the oven, my ability to bake - all from Allah. Zakat is my way of saying 'Thank you, Allah!' and helping others."
                
                "When I give Zakat, Allah blesses my remaining wealth. It's like when you share your toys - you don't have less fun, you have more friends!"
                
                That year, Abdullah's bakery became more successful, and he was able to help even more people. 
                
                The child learned that Zakat isn't about losing money - it's about cleaning our wealth and helping others. It makes our hearts pure and our communities strong!
            """.trimIndent(),
            emoji = "💝",
            duration = 195
        ),
        StoryEpisode(
            id = "zakat_2",
            title = "Sharing is Caring",
            narrator = "Teacher Yusuf",
            content = """
                Little Maryam had a big piggy bank full of coins. Her mother explained, "Maryam, when you have saved enough money, you need to give some to help others. This is called Zakat."
                
                "But Mom," Maryam worried, "if I give my money away, I'll have less!"
                
                Her mother hugged her and said, "Let me tell you a secret: Allah promises that when you give Zakat, He will give you even more blessings! Maybe not more money, but more happiness, more health, and more love."
                
                Maryam decided to try. She gave some of her savings to help buy food for poor families. 
                
                That night, Maryam felt so happy she couldn't stop smiling! She realized her mother was right - giving to others made her heart feel full and warm.
                
                Remember: Zakat teaches us that everything belongs to Allah, and we're just taking care of it for Him!
            """.trimIndent(),
            emoji = "🎁",
            duration = 185
        )
    )
    
    val sawmStories = listOf(
        StoryEpisode(
            id = "sawm_1",
            title = "The Special Month",
            narrator = "Imam Malik",
            content = """
                It was the first day of Ramadan, and young Omar was fasting for the first time! His stomach rumbled, but he remembered why he was fasting.
                
                "Mom," he asked, "why do we fast?"
                
                His mother explained, "Fasting teaches us three important things: First, it helps us understand how poor people feel when they're hungry. This makes us want to help them more."
                
                "Second, it makes us stronger! When we say no to food and drinks during the day, we're training ourselves to say no to bad things and yes to good things."
                
                "Third, and most important, fasting is a special gift between us and Allah. No one can see if you're really fasting or sneaking food - only Allah knows! So it makes our connection with Allah super strong!"
                
                Omar felt proud. Yes, he was hungry, but he was doing something special for Allah!
            """.trimIndent(),
            emoji = "🌙",
            duration = 200
        ),
        StoryEpisode(
            id = "sawm_2",
            title = "The Night Better Than a Thousand Months",
            narrator = "Sister Aisha",
            content = """
                During Ramadan, there's a special night called Laylatul Qadr - the Night of Power! It's better than 1,000 months!
                
                Little Fatima asked her father, "How can one night be better than a thousand months?"
                
                "Imagine," her father said, "that on your birthday, one hour of playing is worth 1,000 hours of regular play! That's how special Laylatul Qadr is!"
                
                "On this night, angels come down to earth, and Allah accepts our prayers specially. It's like having a direct line to Allah with no waiting!"
                
                "We don't know exactly which night it is, but it's in the last 10 nights of Ramadan. So we try extra hard in those nights - praying, reading Quran, and asking Allah for good things."
                
                Fatima stayed up late on those nights, praying for her family, her friends, and all the Muslims in the world. She felt special knowing angels were all around!
            """.trimIndent(),
            emoji = "✨",
            duration = 210
        )
    )
    
    val hajjStories = listOf(
        StoryEpisode(
            id = "hajj_1",
            title = "The Journey of a Lifetime",
            narrator = "Uncle Abdullah",
            content = """
                Grandpa came back from Hajj with tears of joy in his eyes. "I've seen the Ka'bah!" he told his grandchildren.
                
                "What's so special about the Ka'bah?" little Ahmed asked.
                
                Grandpa explained, "The Ka'bah is the house that Prophet Ibrahim and his son Ismail built for Allah, thousands of years ago! When Muslims pray anywhere in the world, we all face the Ka'bah. It's like we're all in one huge circle around it!"
                
                "At Hajj, millions of Muslims from every country come together - all wearing simple white clothes. Rich and poor, old and young, all equal before Allah!"
                
                "We walk around the Ka'bah seven times, we run between two hills like Hajar did when she was looking for water for baby Ismail, and we throw stones at pillars to show we reject evil."
                
                "Hajj teaches us that we're all one big family - the Muslim family!"
            """.trimIndent(),
            emoji = "🕋",
            duration = 220
        ),
        StoryEpisode(
            id = "hajj_2",
            title = "The Water of Zamzam",
            narrator = "Teacher Maryam",
            content = """
                During Hajj, pilgrims drink from a special well called Zamzam. Let me tell you its amazing story!
                
                Long ago, Prophet Ibrahim left his wife Hajar and baby Ismail in a desert with only a little food and water. When the water ran out, baby Ismail cried.
                
                Hajar ran between two hills seven times, looking desperately for water. She kept trusting Allah and never gave up!
                
                Suddenly, angel Jibril appeared and struck the ground. Water burst out! This became the well of Zamzam, and it's been flowing for thousands of years!
                
                Today, when people go for Hajj, they run between the same two hills that Hajar ran between, and they drink from the same Zamzam water!
                
                The story teaches us: Never give up! Keep trusting Allah, and He will help you in ways you never imagined!
                
                Millions of people drink Zamzam water every year, and it never runs out - it's a miracle from Allah!
            """.trimIndent(),
            emoji = "💧",
            duration = 230
        )
    )
    
    fun getAllStories(): List<Pair<String, List<StoryEpisode>>> {
        return listOf(
            "Shahada" to shahadaStories,
            "Salah" to salahStories,
            "Zakat" to zakatStories,
            "Sawm" to sawmStories,
            "Hajj" to hajjStories
        )
    }
    
    // Adult Learning Content
    val shahadaAdult = Pillar(
        id = "shahada",
        number = 1,
        name = "Shahada",
        nameArabic = "الشهادة",
        icon = "☝️",
        colorHex = "#4CAF50",
        worldEmoji = "🌍",
        description = "The testimony of faith - the foundation of Islamic belief",
        storyEpisodes = shahadaStories,
        miniGames = emptyList(),
        definition = """
            The Shahada (testimony of faith) consists of two declarations:
            
            1. "Ash-hadu an la ilaha illa Allah" - I bear witness that there is no deity worthy of worship except Allah
            2. "Wa ash-hadu anna Muhammadan rasulu Allah" - And I bear witness that Muhammad is the Messenger of Allah
            
            This testimony is the gateway to Islam and the foundation upon which all other pillars are built. It represents the core monotheistic belief in the Oneness of Allah (Tawheed) and the acceptance of Muhammad (ﷺ) as the final prophet and messenger.
            
            The Shahada must be:
            - Spoken with the tongue (النطق باللسان)
            - Believed in the heart (التصديق بالقلب)
            - Acted upon with the limbs (العمل بالجوارح)
        """.trimIndent(),
        quranEvidence = listOf(
            Evidence(
                id = "shahada_quran_1",
                arabic = "شَهِدَ اللَّهُ أَنَّهُ لَا إِلَٰهَ إِلَّا هُوَ وَالْمَلَائِكَةُ وَأُولُو الْعِلْمِ قَائِمًا بِالْقِسْطِ",
                translation = "Allah bears witness that there is no deity except Him, and [so do] the angels and those of knowledge - [that He is] maintaining [creation] in justice.",
                reference = "Surah Al-Imran (3:18)"
            ),
            Evidence(
                id = "shahada_quran_2",
                arabic = "فَاعْلَمْ أَنَّهُ لَا إِلَٰهَ إِلَّا اللَّهُ",
                translation = "So know, [O Muhammad], that there is no deity except Allah.",
                reference = "Surah Muhammad (47:19)"
            ),
            Evidence(
                id = "shahada_quran_3",
                arabic = "مُّحَمَّدٌ رَّسُولُ اللَّهِ",
                translation = "Muhammad is the Messenger of Allah.",
                reference = "Surah Al-Fath (48:29)"
            )
        ),
        hadithEvidence = listOf(
            Evidence(
                id = "shahada_hadith_1",
                arabic = "بُنِيَ الْإِسْلَامُ عَلَى خَمْسٍ: شَهَادَةِ أَنْ لَا إِلَهَ إِلَّا اللَّهُ وَأَنَّ مُحَمَّدًا رَسُولُ اللَّهِ",
                translation = "Islam is built upon five pillars: testifying that there is no deity worthy of worship except Allah and that Muhammad is the Messenger of Allah...",
                reference = "Sahih al-Bukhari 8, Sahih Muslim 16"
            ),
            Evidence(
                id = "shahada_hadith_2",
                arabic = "مَنْ قَالَ لَا إِلَهَ إِلَّا اللَّهُ وَكَفَرَ بِمَا يُعْبَدُ مِنْ دُونِ اللَّهِ حَرُمَ مَالُهُ وَدَمُهُ",
                translation = "Whoever says 'There is no deity except Allah' and disbelieves in what is worshipped besides Allah, his wealth and blood become sacred.",
                reference = "Sahih Muslim 23"
            )
        ),
        wisdom = """
            The Shahada represents the complete transformation of one's worldview and life purpose. It is:
            
            1. Liberation from false deities - Freeing oneself from worship of anything besides Allah
            2. Establishment of Tawheed - Affirming the absolute Oneness of Allah in His Lordship, worship, and attributes
            3. Covenant with Allah - A binding contract between the believer and the Creator
            4. Source of spiritual power - The foundation for all acts of worship and righteousness
            5. Promise of Paradise - The key to eternal success when maintained until death
            
            The second part acknowledges Muhammad (ﷺ) as the final messenger, obligating us to:
            - Follow his teachings and example (Sunnah)
            - Accept his authority in matters of faith
            - Love and respect him above all creation
            - Send blessings upon him regularly
        """.trimIndent(),
        practicalApplication = """
            Living by the Shahada means:
            
            Daily Practice:
            - Begin each day renewing your testimony
            - Make decisions based on Islamic principles
            - Avoid shirk (associating partners with Allah) in all forms
            - Follow the Sunnah in personal and social matters
            
            In Worship:
            - Make all worship sincerely for Allah alone
            - Perform acts of worship as taught by the Prophet (ﷺ)
            - Constantly increase in knowledge of faith
            
            In Character:
            - Embody the characteristics praised in Quran and Hadith
            - Maintain truthfulness, honesty, and integrity
            - Show mercy and compassion to all creation
            
            In Society:
            - Be a positive representative of Islam
            - Enjoin good and forbid evil
            - Contribute to the welfare of the community
        """.trimIndent(),
        scenarios = listOf(
            Scenario(
                id = "shahada_scenario_1",
                question = "A non-Muslim friend asks me what makes Islam different from other religions. How should I explain the Shahada?",
                answer = "Explain that the Shahada represents pure monotheism - belief in One God without partners, intermediaries, or associates. Unlike other faiths that may have multiple deities or divine incarnations, Islam maintains absolute Tawheed. The second part of the Shahada establishes that Muhammad (ﷺ) is the final prophet, bringing the complete and preserved message for all humanity until the Day of Judgment.",
                category = "Dawah"
            ),
            Scenario(
                id = "shahada_scenario_2",
                question = "Someone claims to believe in Allah but doesn't follow the teachings of Prophet Muhammad (ﷺ). Is their faith complete?",
                answer = "No, both parts of the Shahada are essential. Believing in Allah alone is necessary but not sufficient. One must also accept Muhammad (ﷺ) as the final messenger and follow his teachings. The Quran states: 'Whoever obeys the Messenger has obeyed Allah' (4:80). Rejecting the Prophet's authority while claiming to believe in Allah contradicts the complete testimony of faith.",
                category = "Aqeedah"
            ),
            Scenario(
                id = "shahada_scenario_3",
                question = "I sometimes feel my faith weakening. How can I strengthen my connection to the Shahada?",
                answer = "1) Frequently repeat and contemplate the Shahada with understanding. 2) Study the Names and Attributes of Allah to deepen your knowledge of Who you worship. 3) Read the Seerah (biography) of the Prophet (ﷺ) to strengthen your love and connection to him. 4) Make sincere dua asking Allah to increase your faith. 5) Seek knowledge through authentic Islamic resources. 6) Associate with righteous company who remind you of Allah.",
                category = "Spiritual Growth"
            )
        ),
        fiqhDifferences = listOf(
            FiqhDifference(
                id = "shahada_fiqh_1",
                topic = "Conditions for accepting the Shahada",
                hanafi = "Emphasizes seven conditions: Knowledge, certainty, sincerity, truthfulness, love, submission, and acceptance. The verbal testimony must be accompanied by internal belief.",
                maliki = "Stresses the importance of understanding and acting upon the Shahada. Mere verbal testimony without understanding is insufficient for adults.",
                shafii = "Requires seven conditions including knowledge of its meaning, certainty without doubt, sincerity, truthfulness, love, submission, and acceptance of all its implications.",
                hanbali = "Emphasizes both belief in the heart and verbal testimony. Lists eight conditions including knowledge, certainty, acceptance, submission, truthfulness, sincerity, love, and rejection of all false deities."
            )
        ),
        kidsHadiths = emptyList()
    )
    
    val salahAdult = Pillar(
        id = "salah",
        number = 2,
        name = "Salah",
        nameArabic = "الصلاة",
        icon = "🤲",
        colorHex = "#2196F3",
        worldEmoji = "🕌",
        description = "The ritual prayer - the connection between the servant and the Lord",
        storyEpisodes = salahStories,
        miniGames = emptyList(),
        definition = """
            Salah (prayer) is the second pillar of Islam and the most important act of worship after the Shahada. It is a direct link between the worshipper and Allah, performed five times daily at prescribed times.
            
            The five daily prayers are:
            1. Fajr (Dawn) - 2 rak'ahs before sunrise
            2. Dhuhr (Noon) - 4 rak'ahs when the sun passes its zenith
            3. Asr (Afternoon) - 4 rak'ahs in the late afternoon
            4. Maghrib (Sunset) - 3 rak'ahs just after sunset
            5. Isha (Night) - 4 rak'ahs after twilight disappears
            
            Salah involves specific physical postures (standing, bowing, prostrating, sitting) combined with recitation of Quranic verses and prescribed supplications. It requires ritual purity (wudu or ghusl) and must face the direction of the Kaaba (Qibla).
        """.trimIndent(),
        quranEvidence = listOf(
            Evidence(
                id = "salah_quran_1",
                arabic = "إِنَّ الصَّلَاةَ كَانَتْ عَلَى الْمُؤْمِنِينَ كِتَابًا مَّوْقُوتًا",
                translation = "Indeed, prayer has been decreed upon the believers at fixed times.",
                reference = "Surah An-Nisa (4:103)"
            ),
            Evidence(
                id = "salah_quran_2",
                arabic = "حَافِظُوا عَلَى الصَّلَوَاتِ وَالصَّلَاةِ الْوُسْطَىٰ وَقُومُوا لِلَّهِ قَانِتِينَ",
                translation = "Maintain with care the prayers and [in particular] the middle prayer and stand before Allah, devoutly obedient.",
                reference = "Surah Al-Baqarah (2:238)"
            ),
            Evidence(
                id = "salah_quran_3",
                arabic = "وَأَقِيمُوا الصَّلَاةَ وَآتُوا الزَّكَاةَ وَارْكَعُوا مَعَ الرَّاكِعِينَ",
                translation = "And establish prayer and give zakah and bow with those who bow [in worship].",
                reference = "Surah Al-Baqarah (2:43)"
            )
        ),
        hadithEvidence = listOf(
            Evidence(
                id = "salah_hadith_1",
                arabic = "الصَّلَاةُ عِمَادُ الدِّينِ",
                translation = "Prayer is the pillar of religion.",
                reference = "Al-Bayhaqi"
            ),
            Evidence(
                id = "salah_hadith_2",
                arabic = "أَوَّلُ مَا يُحَاسَبُ بِهِ الْعَبْدُ يَوْمَ الْقِيَامَةِ الصَّلَاةُ",
                translation = "The first matter that the servant will be brought to account for on the Day of Judgment is the prayer.",
                reference = "Sunan an-Nasa'i 465"
            ),
            Evidence(
                id = "salah_hadith_3",
                arabic = "بَيْنَ الرَّجُلِ وَبَيْنَ الشِّرْكِ وَالْكُفْرِ تَرْكُ الصَّلَاةِ",
                translation = "Between a man and disbelief and polytheism is the abandonment of prayer.",
                reference = "Sahih Muslim 82"
            )
        ),
        wisdom = """
            Salah serves multiple profound purposes:
            
            Spiritual Dimension:
            - Direct communication with Allah five times daily
            - Remembrance and consciousness of the Creator (Dhikr)
            - Spiritual purification and development of taqwa
            - Training in sincerity and presence of heart (khushu')
            
            Personal Development:
            - Discipline and time management
            - Self-control and consistency
            - Mindfulness and focus
            - Breaking from worldly distractions
            
            Social Dimension:
            - Unity through congregational prayer
            - Equality before Allah regardless of social status
            - Regular community gathering and bonding
            - Collective remembrance strengthens faith
            
            The Prophet (ﷺ) said: "Prayer is light" - it illuminates the heart and guides one's path through life.
        """.trimIndent(),
        practicalApplication = """
            Establishing Regular Prayer:
            
            1. Preparation:
            - Learn prayer times for your location
            - Set reminders/alarms for each prayer
            - Maintain wudu (ablution) when possible
            - Choose a clean, quiet space for prayer
            
            2. Quality over Quantity:
            - Focus on perfecting obligatory prayers first
            - Develop khushu' (concentration and humility)
            - Learn meanings of what you recite
            - Add voluntary prayers gradually
            
            3. Congregational Prayer:
            - Men: Attend mosque prayers, especially Fajr and Isha
            - Women: May pray at mosque or home (home is better for women according to some scholars)
            - Friday prayer (Jumu'ah) is obligatory for men
            
            4. When Traveling:
            - Shorten 4-rak'ah prayers to 2 rak'ahs
            - Combine Dhuhr with Asr, and Maghrib with Isha when needed
            - Always pray on time even while traveling
            
            5. For Those Struggling:
            - Start with one prayer perfected, then add more
            - Seek Allah's help through dua
            - Find an accountability partner
            - Remember the severe warning against abandoning prayer
        """.trimIndent(),
        scenarios = listOf(
            Scenario(
                id = "salah_scenario_1",
                question = "My work schedule makes it difficult to pray on time. What should I do?",
                answer = "Prayer must be performed within its time range, which cannot be compromised. Options include: 1) Inform your employer of your religious obligation - many workplaces provide accommodation. 2) Use break times for prayer. 3) Combine prayers when traveling (Dhuhr-Asr, Maghrib-Isha). 4) Consider finding employment that respects religious obligations if current situation makes prayer impossible. The Prophet (ﷺ) said prayer times are fixed obligations that cannot be delayed without valid reason.",
                category = "Contemporary Issues"
            ),
            Scenario(
                id = "salah_scenario_2",
                question = "I struggle with concentration in prayer. My mind wanders constantly. How can I improve my khushu'?",
                answer = "Improving khushu' requires consistent effort: 1) Understand what you're reciting - learn Arabic or translations. 2) Prepare mentally before prayer - make wudu mindfully, clear your mind. 3) Slow down - don't rush through movements. 4) Visualize standing before Allah. 5) Vary your recitation to maintain engagement. 6) Make dua before prayer asking Allah for khushu'. 7) Reduce worldly distractions before prayer time. 8) Reflect on death and the Day of Judgment. Remember, even the Sahaba struggled with this, so be patient with yourself.",
                category = "Spiritual Development"
            ),
            Scenario(
                id = "salah_scenario_3",
                question = "Is it better to pray quickly in congregation or slowly alone?",
                answer = "For men, praying in congregation carries immense reward - 27 times greater than praying alone according to authentic hadith. However, this doesn't mean rushing. The ideal is to pray with the congregation while maintaining proper pace and khushu'. If the imam rushes excessively, affecting the validity of prayer, it's better to pray alone with proper form. The Prophet (ﷺ) emphasized both congregation and tranquility in prayer.",
                category = "Fiqh"
            )
        ),
        fiqhDifferences = listOf(
            FiqhDifference(
                id = "salah_fiqh_1",
                topic = "Raising hands (Rafa' al-Yadayn)",
                hanafi = "Hands are raised only at the opening takbir. Hands are then folded below the navel (for men) or on the chest (for women).",
                maliki = "Hands are raised only at the opening takbir. Arms are left hanging by the sides during standing (Qiyam).",
                shafii = "Hands are raised at four points: opening takbir, before and after ruku', and when standing from the first tashahhud.",
                hanbali = "Similar to Shafi'i - hands are raised at the opening takbir, before and after ruku', and when rising from the first tashahhud."
            ),
            FiqhDifference(
                id = "salah_fiqh_2",
                topic = "Saying 'Ameen' after Al-Fatihah",
                hanafi = "Ameen is said silently in both loud and quiet prayers.",
                maliki = "Ameen is said silently only by the imam; the congregation does not say it aloud.",
                shafii = "Ameen is said loudly in prayers where recitation is loud, silently in quiet prayers.",
                hanbali = "Similar to Shafi'i - Ameen is said loudly when the imam's recitation is loud."
            ),
            FiqhDifference(
                id = "salah_fiqh_3",
                topic = "Combining prayers",
                hanafi = "Combining prayers is only allowed during Hajj at Arafah and Muzdalifah. Not permitted during regular travel.",
                maliki = "Combining is allowed during travel and for rain. Travel must be beyond 48 miles (approximately 77 km).",
                shafii = "Combining is allowed during travel, rain, illness, and other hardships. Can combine Dhuhr-Asr and Maghrib-Isha.",
                hanbali = "Most lenient - allows combining for travel, rain, illness, hardship, and even without these conditions if needed occasionally."
            )
        ),
        kidsHadiths = emptyList()
    )
    
    val zakatAdult = Pillar(
        id = "zakat",
        number = 3,
        name = "Zakat",
        nameArabic = "الزكاة",
        icon = "💝",
        colorHex = "#FF9800",
        worldEmoji = "🤝",
        description = "Obligatory charity - purification of wealth and soul",
        storyEpisodes = zakatStories,
        miniGames = emptyList(),
        definition = """
            Zakat is the third pillar of Islam, an obligatory annual charity given to specific categories of people. The word "Zakat" means "purification" and "growth" - it purifies wealth and the soul while promoting economic justice in society.
            
            Zakat is obligatory on:
            1. Gold and Silver (85 grams gold or 595 grams silver threshold)
            2. Cash and savings (above nisab)
            3. Business inventory
            4. Agricultural produce
            5. Livestock (camels, cattle, sheep/goats above minimum number)
            
            The standard rate is 2.5% (1/40) of qualifying wealth held for one lunar year. Different rates apply to agricultural produce (5-10%) depending on irrigation method.
            
            Zakat is paid to eight categories mentioned in Quran (9:60): the poor, the needy, administrators of Zakat, those whose hearts are to be reconciled, freeing slaves, those in debt, in the cause of Allah, and wayfarers.
        """.trimIndent(),
        quranEvidence = listOf(
            Evidence(
                id = "zakat_quran_1",
                arabic = "وَأَقِيمُوا الصَّلَاةَ وَآتُوا الزَّكَاةَ",
                translation = "And establish prayer and give Zakat.",
                reference = "Surah Al-Baqarah (2:43)"
            ),
            Evidence(
                id = "zakat_quran_2",
                arabic = "خُذْ مِنْ أَمْوَالِهِمْ صَدَقَةً تُطَهِّرُهُمْ وَتُزَكِّيهِم بِهَا",
                translation = "Take from their wealth a charity by which you purify them and cause them increase.",
                reference = "Surah At-Tawbah (9:103)"
            ),
            Evidence(
                id = "zakat_quran_3",
                arabic = "إِنَّمَا الصَّدَقَاتُ لِلْفُقَرَاءِ وَالْمَسَاكِينِ...",
                translation = "Zakat expenditures are only for the poor and for the needy, and for those employed to collect it, and for bringing hearts together, and for freeing captives, and for those in debt, and for the cause of Allah, and for the traveler...",
                reference = "Surah At-Tawbah (9:60)"
            )
        ),
        hadithEvidence = listOf(
            Evidence(
                id = "zakat_hadith_1",
                arabic = "فِي كُلِّ أَرْبَعِينَ دِينَارًا دِينَارٌ",
                translation = "For every forty dinars, one dinar [must be paid in Zakat].",
                reference = "Sunan Abu Dawud 1573"
            ),
            Evidence(
                id = "zakat_hadith_2",
                arabic = "مَا نَقَصَتْ صَدَقَةٌ مِنْ مَالٍ",
                translation = "Charity does not decrease wealth.",
                reference = "Sahih Muslim 2588"
            ),
            Evidence(
                id = "zakat_hadith_3",
                arabic = "مَنْ آتَاهُ اللَّهُ مَالاً فَلَمْ يُؤَدِّ زَكَاتَهُ مُثِّلَ لَهُ شُجَاعًا أَقْرَعَ",
                translation = "Whoever is given wealth by Allah and does not pay its Zakat, on the Day of Resurrection it will appear to him as a bald-headed poisonous male snake.",
                reference = "Sahih al-Bukhari 1403"
            )
        ),
        wisdom = """
            The wisdom behind Zakat includes:
            
            Spiritual Benefits:
            - Purifies the soul from greed and miserliness
            - Develops gratitude for Allah's blessings
            - Increases barakah (blessing) in wealth
            - Protects from attachment to material possessions
            
            Social Justice:
            - Redistributes wealth in society
            - Provides for those unable to earn
            - Reduces economic inequality
            - Creates a caring, compassionate community
            
            Economic Benefits:
            - Circulates wealth in the economy
            - Prevents hoarding of resources
            - Encourages investment over savings
            - Provides social safety net
            
            Religious Significance:
            - Test of obedience and trust in Allah
            - Acknowledgment that all wealth belongs to Allah
            - Fulfillment of the rights of others in our wealth
            - Means of attaining Allah's mercy and forgiveness
        """.trimIndent(),
        practicalApplication = """
            Calculating and Paying Zakat:
            
            1. Determine Your Zakatable Assets:
            - Cash and bank balances
            - Gold and silver jewelry (scholars differ on personal use jewelry)
            - Stocks, bonds, and investments
            - Business inventory and receivables
            - Rental income properties (some scholars)
            - Retirement accounts (accessible funds)
            
            2. Calculate the Nisab:
            - Gold: 85 grams (approx. 3 oz)
            - Silver: 595 grams (approx. 21 oz)
            - Use current market price to determine cash equivalent
            - More cautious: use silver nisab (benefits the poor)
            
            3. Choose Your Zakat Date:
            - One lunar year after reaching nisab
            - Ramadan is popular but not required
            - Be consistent with your chosen date
            
            4. Calculate 2.5%:
            - Total qualifying assets × 0.025
            - Deduct outstanding debts (scholarly difference)
            - Round up to be generous
            
            5. Distribution:
            - Prioritize local poor and needy
            - Can give through trusted organizations
            - Verify recipients qualify under Shariah
            - Cannot give to: parents, children, spouse, or non-Muslims (except to reconcile hearts)
            
            6. Record Keeping:
            - Maintain annual Zakat records
            - Track asset values on your Zakat date
            - Document distributions
        """.trimIndent(),
        scenarios = listOf(
            Scenario(
                id = "zakat_scenario_1",
                question = "I have a mortgage on my house. Can I deduct this debt before calculating Zakat?",
                answer = "Scholars differ on this. Hanafi school: Immediate debts (due within the year) can be deducted. Long-term debts like mortgages generally cannot. Shafi'i and Hanbali schools: All debts can be deducted if they're binding. Maliki school: No debts are deducted from Zakatable wealth. Most contemporary scholars suggest that scheduled mortgage payments for the coming year may be deducted, but not the entire mortgage amount. The safest approach is to calculate Zakat on total assets without deducting long-term debts.",
                category = "Fiqh"
            ),
            Scenario(
                id = "zakat_scenario_2",
                question = "Can I give my Zakat to a family member who is in need?",
                answer = "Yes, with conditions: You CAN give Zakat to siblings, uncles, aunts, cousins, nephews, nieces, and in-laws if they qualify as poor or needy. You CANNOT give to parents, grandparents, children, grandchildren, or spouse - you're already obligated to support them. Giving Zakat to eligible relatives is actually more rewarding as it combines charity with maintaining family ties. The Prophet (ﷺ) said: 'Charity given to a poor person is charity, but charity given to a relative is two things: charity and upholding family ties.'",
                category = "Contemporary Issues"
            ),
            Scenario(
                id = "zakat_scenario_3",
                question = "I live in a non-Muslim country. Can I send my Zakat back home where poverty is greater?",
                answer = "The principle is to prioritize local poor Muslims, as they have the primary right to the Zakat of their community. However, if: 1) There are no or few qualifying recipients locally, 2) The need is much greater elsewhere, 3) Disaster or crisis situation exists elsewhere - then sending Zakat abroad is permissible and even recommended. Many scholars suggest distributing some locally (even if the need is less) and sending the rest where it's most needed. Always ensure reliable delivery through trustworthy organizations.",
                category = "Contemporary Issues"
            )
        ),
        fiqhDifferences = listOf(
            FiqhDifference(
                id = "zakat_fiqh_1",
                topic = "Zakat on personal jewelry",
                hanafi = "Zakat is due on all gold and silver jewelry, whether worn or not, if it reaches nisab.",
                maliki = "No Zakat on jewelry that is for permissible use (not excessive). Zakat is due on jewelry kept for investment or excessive adornment.",
                shafii = "No Zakat on jewelry intended for personal use and adornment. Zakat is due if kept for trade or investment.",
                hanbali = "Similar to Shafi'i - no Zakat on permissible jewelry for personal use. Zakat is due on jewelry for trade."
            ),
            FiqhDifference(
                id = "zakat_fiqh_2",
                topic = "Deducting debts from Zakatable wealth",
                hanafi = "Only immediate debts (due within the year) can be deducted from Zakatable assets.",
                maliki = "Debts do not reduce the obligation of Zakat. Zakat is calculated on gross assets.",
                shafii = "All debts, whether immediate or long-term, can be deducted from Zakatable wealth.",
                hanbali = "Debts reduce Zakatable wealth. One calculates Zakat only on assets remaining after deducting all debts."
            )
        ),
        kidsHadiths = emptyList()
    )
    
    val sawmAdult = Pillar(
        id = "sawm",
        number = 4,
        name = "Sawm",
        nameArabic = "الصوم",
        icon = "🌙",
        colorHex = "#9C27B0",
        worldEmoji = "🌟",
        description = "Fasting during Ramadan - self-discipline and spiritual elevation",
        storyEpisodes = sawmStories,
        miniGames = emptyList(),
        definition = """
            Sawm (fasting) is the fourth pillar of Islam, obligatory during the month of Ramadan for every adult, sane Muslim who is physically able. Fasting means abstaining from food, drink, sexual relations, and sinful behavior from dawn (Fajr) until sunset (Maghrib).
            
            Ramadan is the ninth month of the Islamic lunar calendar, during which the Quran was first revealed. The month lasts 29 or 30 days depending on the moon sighting.
            
            Essential Elements of Fasting:
            1. Intention (Niyyah) - Made before dawn for each day or for the entire month
            2. Abstinence from food and drink from dawn to sunset
            3. Abstinence from sexual relations during fasting hours
            4. Avoiding vain talk, lying, and sinful behavior
            5. Breaking the fast immediately after sunset
            
            Exemptions from Fasting:
            - Travelers (must make up later)
            - Menstruating or postpartum women (must make up later)
            - Sick individuals (must make up or feed poor)
            - Elderly unable to fast (must feed one poor person per day)
            - Pregnant or nursing women fearing harm (must make up or feed poor)
        """.trimIndent(),
        quranEvidence = listOf(
            Evidence(
                id = "sawm_quran_1",
                arabic = "يَا أَيُّهَا الَّذِينَ آمَنُوا كُتِبَ عَلَيْكُمُ الصِّيَامُ كَمَا كُتِبَ عَلَى الَّذِينَ مِن قَبْلِكُمْ لَعَلَّكُمْ تَتَّقُونَ",
                translation = "O you who believe! Fasting is prescribed for you as it was prescribed for those before you, that you may attain taqwa (God-consciousness).",
                reference = "Surah Al-Baqarah (2:183)"
            ),
            Evidence(
                id = "sawm_quran_2",
                arabic = "شَهْرُ رَمَضَانَ الَّذِي أُنزِلَ فِيهِ الْقُرْآنُ هُدًى لِّلنَّاسِ",
                translation = "The month of Ramadan in which was revealed the Quran, a guidance for mankind.",
                reference = "Surah Al-Baqarah (2:185)"
            ),
            Evidence(
                id = "sawm_quran_3",
                arabic = "وَكُلُوا وَاشْرَبُوا حَتَّىٰ يَتَبَيَّنَ لَكُمُ الْخَيْطُ الْأَبْيَضُ مِنَ الْخَيْطِ الْأَسْوَدِ مِنَ الْفَجْرِ",
                translation = "And eat and drink until the white thread of dawn becomes distinct from the black thread [of night].",
                reference = "Surah Al-Baqarah (2:187)"
            )
        ),
        hadithEvidence = listOf(
            Evidence(
                id = "sawm_hadith_1",
                arabic = "مَنْ صَامَ رَمَضَانَ إِيمَانًا وَاحْتِسَابًا غُفِرَ لَهُ مَا تَقَدَّمَ مِنْ ذَنْبِهِ",
                translation = "Whoever fasts Ramadan out of faith and seeking reward, his previous sins will be forgiven.",
                reference = "Sahih al-Bukhari 38, Sahih Muslim 760"
            ),
            Evidence(
                id = "sawm_hadith_2",
                arabic = "الصِّيَامُ جُنَّةٌ",
                translation = "Fasting is a shield [from sin and Hell].",
                reference = "Sahih al-Bukhari 1894, Sahih Muslim 1151"
            ),
            Evidence(
                id = "sawm_hadith_3",
                arabic = "إِنَّ فِي الْجَنَّةِ بَابًا يُقَالُ لَهُ الرَّيَّانُ يَدْخُلُ مِنْهُ الصَّائِمُونَ",
                translation = "There is a gate in Paradise called Ar-Rayyan, through which those who fasted will enter.",
                reference = "Sahih al-Bukhari 1896, Sahih Muslim 1152"
            )
        ),
        wisdom = """
            The profound wisdom of fasting includes:
            
            Spiritual Development:
            - Strengthens taqwa (God-consciousness)
            - Develops self-control and discipline
            - Increases devotion and closeness to Allah
            - Enhances appreciation for blessings
            - Provides training in patience and perseverance
            
            Social Awareness:
            - Experience of hunger develops empathy for the poor
            - Encourages charity and generosity
            - Unifies the Muslim community globally
            - Breaks down social barriers (rich and poor fast equally)
            
            Physical Benefits:
            - Detoxification and cellular regeneration
            - Improved metabolic health
            - Enhanced mental clarity
            - Breaking harmful eating habits
            
            Psychological Benefits:
            - Strengthens willpower
            - Reduces anxiety and stress
            - Improves focus and concentration
            - Develops mindfulness
            
            The Prophet (ﷺ) said: "Every deed of the son of Adam will be multiplied between ten to seven hundred times, except fasting. It is for Me and I will reward it." (Sahih Muslim)
        """.trimIndent(),
        practicalApplication = """
            Maximizing Ramadan:
            
            Before Ramadan:
            - Prepare spiritually: increase worship, repent from sins
            - Prepare physically: adjust sleep schedule gradually
            - Fast voluntary days (Sha'ban) to train
            - Set specific goals for the month
            - Plan Quran reading schedule
            
            During Ramadan:
            - Suhoor (pre-dawn meal): Eat nutritious food, delay as much as possible
            - Fajr prayer: Pray in congregation if possible
            - Quran: Aim to complete at least one full reading
            - Dua: Increase supplications, especially before iftar
            - Iftar (breaking fast): Start with dates and water following Sunnah
            - Taraweeh: Attend mosque for night prayers
            - Last 10 nights: Intensify worship, seek Laylatul Qadr
            - I'tikaf: Consider spiritual retreat in mosque if possible
            
            What Breaks the Fast:
            - Intentional eating or drinking
            - Smoking
            - Sexual intercourse
            - Deliberate vomiting
            - Menstruation or postpartum bleeding
            
            What Doesn't Break the Fast:
            - Unintentional eating/drinking (forgetfulness)
            - Swallowing saliva
            - Taking injections (according to many scholars)
            - Using eye/ear drops (according to some scholars)
            - Rinsing mouth or nose (without swallowing)
            - Taking blood test
            - Unintentional vomiting
            
            For Those Unable to Fast:
            - Chronic illness: Feed one poor person per day
            - Pregnancy/nursing: Make up later or feed poor
            - Travel: Make up later (may continue fasting if easy)
            - Menstruation: Must make up later
            - Temporary illness: Make up later
        """.trimIndent(),
        scenarios = listOf(
            Scenario(
                id = "sawm_scenario_1",
                question = "I forgot I was fasting and ate something. Is my fast broken?",
                answer = "No, your fast remains valid. The Prophet (ﷺ) said: 'Whoever forgets he is fasting and eats or drinks, let him complete his fast, for it is Allah Who has fed him and given him drink.' (Sahih al-Bukhari 1933, Sahih Muslim 1155). This is a mercy from Allah. Simply continue your fast when you remember. However, if you intentionally eat after remembering, the fast is broken and must be made up.",
                category = "Fiqh"
            ),
            Scenario(
                id = "sawm_scenario_2",
                question = "I work a physically demanding job. The heat and exhaustion make fasting extremely difficult. What should I do?",
                answer = "Fasting is obligatory despite difficulty, as hardship is part of its purpose. However, if fasting poses genuine health risk or unbearable hardship, you may: 1) Continue fasting but reduce work if possible. 2) Use vacation days during Ramadan. 3) If impossible, make up fasts in winter when days are shorter and cooler. 4) If your job permanently prevents fasting, seek halal employment if possible. The elderly or chronically ill who cannot fast must feed one poor person per day. Consult a knowledgeable scholar for your specific situation.",
                category = "Contemporary Issues"
            ),
            Scenario(
                id = "sawm_scenario_3",
                question = "I missed several fasts due to travel and illness. Can I make them all up at once, or must I do them separately?",
                answer = "You can make up missed fasts at any time before the next Ramadan, either consecutively or separately. Most scholars say: 1) Making them up consecutively is not required (unlike Kaffarah fasts). 2) You have until the next Ramadan to make them up. 3) If the next Ramadan arrives and you haven't made them up, you must fast them after and additionally feed one poor person per day. 4) Delaying without excuse is sinful. Winter months are often easier for making up fasts due to shorter days.",
                category = "Fiqh"
            )
        ),
        fiqhDifferences = listOf(
            FiqhDifference(
                id = "sawm_fiqh_1",
                topic = "When to make the intention (Niyyah) for fasting",
                hanafi = "The intention must be made during the night before Fajr for each obligatory fast. Renewed daily.",
                maliki = "One intention at the beginning of Ramadan suffices for the entire month unless interrupted.",
                shafii = "Intention must be made before Fajr for each fast. Can be made anytime during the night.",
                hanbali = "Intention must be made before Fajr. One intention for entire Ramadan is acceptable if not interrupted."
            ),
            FiqhDifference(
                id = "sawm_fiqh_2",
                topic = "Pregnant and nursing women",
                hanafi = "If they fear for themselves, they only make up the fasts. If they fear for the baby, they make up fasts AND feed a poor person per day.",
                maliki = "They only need to make up the fasts later, regardless of the reason for breaking fast.",
                shafii = "Similar to Hanafi - make up fasts if fearing for self; make up fasts and feed poor if fearing for baby.",
                hanbali = "If fearing for the baby, they feed a poor person per day and don't have to make up. If fearing for themselves, they only make up."
            )
        ),
        kidsHadiths = emptyList()
    )
    
    val hajjAdult = Pillar(
        id = "hajj",
        number = 5,
        name = "Hajj",
        nameArabic = "الحج",
        icon = "🕋",
        colorHex = "#F44336",
        worldEmoji = "✈️",
        description = "Pilgrimage to Makkah - the ultimate journey of submission",
        storyEpisodes = hajjStories,
        miniGames = emptyList(),
        definition = """
            Hajj is the fifth pillar of Islam, an obligatory pilgrimage to the sacred city of Makkah performed during the Islamic month of Dhul-Hijjah. Every adult Muslim who is physically and financially able must perform Hajj at least once in their lifetime.
            
            Key Rituals of Hajj:
            1. Ihram - Entering state of consecration, wearing simple white garments
            2. Tawaf - Circumambulating the Kaaba seven times
            3. Sa'i - Walking seven times between hills of Safa and Marwah
            4. Standing at Arafah - The most important ritual, on 9th Dhul-Hijjah
            5. Muzdalifah - Overnight stay and collecting pebbles
            6. Rami - Stoning the pillars representing Satan
            7. Sacrifice - Offering an animal in commemoration of Prophet Ibrahim
            8. Tawaf al-Ifadah - Circumambulation after leaving Arafah
            9. Days of Tashreeq - Spending days in Mina
            10. Farewell Tawaf - Final circumambulation before leaving
            
            Conditions for Hajj being obligatory:
            - Islam, sanity, and maturity
            - Physical ability to undertake the journey
            - Financial means (beyond one's debts and dependents' needs)
            - Safety of the route
            - For women: accompanied by mahram (according to majority)
        """.trimIndent(),
        quranEvidence = listOf(
            Evidence(
                id = "hajj_quran_1",
                arabic = "وَلِلَّهِ عَلَى النَّاسِ حِجُّ الْبَيْتِ مَنِ اسْتَطَاعَ إِلَيْهِ سَبِيلًا",
                translation = "And [due] to Allah from the people is a pilgrimage to the House - for whoever is able to find thereto a way.",
                reference = "Surah Al-Imran (3:97)"
            ),
            Evidence(
                id = "hajj_quran_2",
                arabic = "وَأَذِّن فِي النَّاسِ بِالْحَجِّ يَأْتُوكَ رِجَالًا وَعَلَىٰ كُلِّ ضَامِرٍ يَأْتِينَ مِن كُلِّ فَجٍّ عَمِيقٍ",
                translation = "And proclaim to the people the Hajj; they will come to you on foot and on every lean camel; they will come from every distant pass.",
                reference = "Surah Al-Hajj (22:27)"
            ),
            Evidence(
                id = "hajj_quran_3",
                arabic = "الْحَجُّ أَشْهُرٌ مَّعْلُومَاتٌ",
                translation = "Hajj is [during] well-known months.",
                reference = "Surah Al-Baqarah (2:197)"
            )
        ),
        hadithEvidence = listOf(
            Evidence(
                id = "hajj_hadith_1",
                arabic = "مَنْ حَجَّ فَلَمْ يَرْفُثْ وَلَمْ يَفْسُقْ رَجَعَ كَيَوْمِ وَلَدَتْهُ أُمُّهُ",
                translation = "Whoever performs Hajj and does not commit any obscenity or evil will return [free from sins] as on the day his mother gave birth to him.",
                reference = "Sahih al-Bukhari 1521, Sahih Muslim 1350"
            ),
            Evidence(
                id = "hajj_hadith_2",
                arabic = "الْحَجُّ الْمَبْرُورُ لَيْسَ لَهُ جَزَاءٌ إِلَّا الْجَنَّةُ",
                translation = "An accepted Hajj has no reward except Paradise.",
                reference = "Sahih al-Bukhari 1773, Sahih Muslim 1349"
            ),
            Evidence(
                id = "hajj_hadith_3",
                arabic = "تَابِعُوا بَيْنَ الْحَجِّ وَالْعُمْرَةِ فَإِنَّهُمَا يَنْفِيَانِ الْفَقْرَ وَالذُّنُوبَ",
                translation = "Perform Hajj and Umrah consecutively, for they remove poverty and sin.",
                reference = "Sunan an-Nasa'i 2631"
            )
        ),
        wisdom = """
            The profound wisdom of Hajj includes:
            
            Spiritual Transformation:
            - Complete submission to Allah's will
            - Revival of faith through physical sacrifice
            - Seeking Allah's forgiveness and mercy
            - Starting life anew, purified from sins
            - Following footsteps of prophets Ibrahim and Muhammad
            
            Unity and Equality:
            - Muslims from all races, nations, and classes gather as equals
            - Simple white garments remove all distinctions
            - Same rituals performed by everyone
            - Demonstration of Islamic brotherhood
            - Preview of gathering on Day of Judgment
            
            Historical Connection:
            - Commemorating Prophet Ibrahim's sacrifice
            - Remembering Hajar's trust in Allah
            - Walking where prophets walked
            - Connecting to Islamic heritage
            - Renewing covenant with Allah
            
            Personal Development:
            - Testing patience and perseverance
            - Developing humility and simplicity
            - Learning to cope with hardship
            - Strengthening determination and resolve
            - Building character through challenge
            
            Social Benefits:
            - Global Muslim gathering and networking
            - Cultural exchange and understanding
            - Economic benefit to host country
            - Opportunity for education and learning
        """.trimIndent(),
        practicalApplication = """
            Preparing for Hajj:
            
            Financial Preparation (Years Before):
            - Save regularly for Hajj expenses
            - Ensure all debts are paid
            - Provide for dependents during absence
            - Choose reputable Hajj operator
            - Obtain necessary travel documents
            
            Knowledge Preparation (Months Before):
            - Study Hajj rituals thoroughly
            - Learn from experienced pilgrims
            - Watch educational videos
            - Read authentic Hajj guides
            - Understand common mistakes to avoid
            
            Physical Preparation (Weeks Before):
            - Build stamina through exercise
            - Practice walking long distances
            - Get required vaccinations
            - Prepare for hot weather
            - Pack appropriate clothing and supplies
            
            Spiritual Preparation:
            - Repent from all sins
            - Seek forgiveness from those wronged
            - Increase acts of worship
            - Make list of duas to make
            - Study the virtues of Hajj sites
            
            During Hajj:
            - Maintain state of ihram
            - Avoid arguments and disputes
            - Be patient with crowds
            - Help fellow pilgrims
            - Make abundant dua
            - Pray in Masjid al-Haram frequently
            - Complete rituals with knowledge
            - Stay hydrated and healthy
            
            After Hajj:
            - Maintain spiritual momentum
            - Share experiences with others
            - Help prepare future pilgrims
            - Continue good deeds
            - Avoid returning to sins
            - Be grateful for opportunity
        """.trimIndent(),
        scenarios = listOf(
            Scenario(
                id = "hajj_scenario_1",
                question = "I have savings for Hajj, but also have some debt. Should I pay off the debt first or perform Hajj?",
                answer = "The general principle: debts must be paid first before Hajj becomes obligatory. If you have debt, Hajj is not yet obligatory upon you until you're free from debt and have sufficient wealth beyond your needs and dependents' needs. However, if: 1) The debt is long-term (like a manageable mortgage) and creditor doesn't require immediate payment, 2) You can afford both debt payments and Hajj, 3) Performing Hajj won't harm your ability to pay debt - then some scholars permit performing Hajj. Always prioritize debt payment, as fulfilling financial obligations is extremely important in Islam.",
                category = "Fiqh"
            ),
            Scenario(
                id = "hajj_scenario_2",
                question = "As a woman, can I perform Hajj without a mahram if traveling with a group of women?",
                answer = "This is a matter of scholarly difference. Majority view (Hanafi, Maliki, Shafi'i, Hanbali): A woman must travel with a mahram (unmarriageable male relative) or husband. She cannot travel for Hajj without one, even in a group. The distance limit varies between schools. Alternative view (some contemporary scholars like Sheikh Yusuf al-Qaradawi): If traveling with a trustworthy group of women in safe conditions, it may be permissible based on changed circumstances. However, the safer position is to follow the majority and travel with a mahram. If no mahram is available and you have the means, you may sponsor someone to perform Hajj on your behalf (Hajj Badal).",
                category = "Fiqh"
            ),
            Scenario(
                id = "hajj_scenario_3",
                question = "I can afford Hajj, but I'm elderly and worried about physical difficulty. Should I still go?",
                answer = "If you're physically unable to perform Hajj due to age, chronic illness, or disability, Hajj is not obligatory upon you. However, if you have the financial means, you should arrange for someone else to perform Hajj on your behalf (Hajj Badal). This person must have already performed their own obligatory Hajj. If you're uncertain about your capability: 1) Consult a doctor about specific risks. 2) Consider performing Hajj during less crowded periods (though Dhul-Hijjah is required). 3) Modern accommodations and services make Hajj easier for elderly. 4) The reward for striving despite difficulty is immense. Make istikharah prayer and decide based on genuine capability.",
                category = "Contemporary Issues"
            )
        ),
        fiqhDifferences = listOf(
            FiqhDifference(
                id = "hajj_fiqh_1",
                topic = "Woman traveling for Hajj",
                hanafi = "A woman must have a mahram for any journey exceeding approximately 78 km (48 miles). Without a mahram, Hajj is not obligatory.",
                maliki = "A woman must travel with a mahram, husband, or a group of trustworthy women for distances of one day's journey or more.",
                shafii = "A woman must be accompanied by her husband or a mahram for any journey longer than one day and night (approximately 80 km).",
                hanbali = "Similar to Hanafi - a woman must travel with a mahram for any journey exceeding approximately 80 km."
            ),
            FiqhDifference(
                id = "hajj_fiqh_2",
                topic = "Penalty for violating Ihram restrictions",
                hanafi = "Penalties include: sacrificing a sheep for major violations, feeding six poor people or fasting three days for medium violations, giving charity for minor violations.",
                maliki = "Similar structure: major violations require animal sacrifice, lesser violations require feeding poor or fasting.",
                shafii = "Graduated penalties based on severity: sacrifice for major violations (like intercourse), choice of sacrifice/fasting/feeding for medium violations.",
                hanbali = "Penalties are similar to Shafi'i school - graduated based on the severity of the violation."
            ),
            FiqhDifference(
                id = "hajj_fiqh_3",
                topic = "Time for standing at Arafah",
                hanafi = "Must be present at Arafah for any moment between noon of 9th Dhul-Hijjah until Fajr of 10th Dhul-Hijjah.",
                maliki = "Must be at Arafah from noon on 9th until Fajr of 10th. Missing it entirely invalidates Hajj.",
                shafii = "Must be present at Arafah for any moment from noon on 9th until Fajr of 10th. Even a brief presence suffices.",
                hanbali = "Similar to Shafi'i - any presence at Arafah during the prescribed time, even momentarily, fulfills the obligation."
            )
        ),
        kidsHadiths = emptyList()
    )
    
    // Function to get all adult content
    fun getAdultPillars(): List<Pillar> {
        return listOf(
            shahadaAdult,
            salahAdult,
            zakatAdult,
            sawmAdult,
            hajjAdult
        )
    }
    
    fun getPillarById(id: String): Pillar? {
        return getAdultPillars().find { it.id == id }
    }
}
