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
}
