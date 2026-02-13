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
