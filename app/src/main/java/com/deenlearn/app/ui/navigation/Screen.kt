package com.deenlearn.app.ui.navigation

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Main : Screen("main")
    object Home : Screen("home")
    object Pillars : Screen("pillars")
    object Quran : Screen("quran")
    object Prayer : Screen("prayer")
    object Arabic : Screen("arabic")
    object Profile : Screen("profile")
    object Hadith : Screen("hadith")
    
    // Detail screens
    object PillarDetail : Screen("pillar_detail/{pillarId}") {
        fun createRoute(pillarId: String) = "pillar_detail/$pillarId"
    }
    
    object QuranReader : Screen("quran_reader/{surahId}") {
        fun createRoute(surahId: String) = "quran_reader/$surahId"
    }
    
    object PrayerDetail : Screen("prayer_detail/{prayerId}") {
        fun createRoute(prayerId: String) = "prayer_detail/$prayerId"
    }
    
    object ArabicLesson : Screen("arabic_lesson/{lessonId}") {
        fun createRoute(lessonId: String) = "arabic_lesson/$lessonId"
    }
    
    object HadithDetail : Screen("hadith_detail/{hadithId}") {
        fun createRoute(hadithId: String) = "hadith_detail/$hadithId"
    }
}

// Tab destinations for bottom navigation
enum class TabDestination(
    val route: String,
    val title: String,
    val icon: String
) {
    HOME("home", "Home", "home"),
    PILLARS("pillars", "Pillars", "account_balance"),
    QURAN("quran", "Quran", "menu_book"),
    PRAYER("prayer", "Prayer", "mosque"),
    ARABIC("arabic", "Arabic", "translate"),
    PROFILE("profile", "Profile", "person"),
    HADITH("hadith", "Hadith", "auto_stories")
}
