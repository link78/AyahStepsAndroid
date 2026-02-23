package com.deenlearn.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.deenlearn.app.ui.components.DeenBottomNavBar
import com.deenlearn.app.ui.components.DeenTopBar
import com.deenlearn.app.ui.navigation.Screen
import com.deenlearn.app.ui.navigation.TabDestination

// Import actual screen implementations
import com.deenlearn.app.ui.screens.PillarsScreen as ActualPillarsScreen
import com.deenlearn.app.ui.screens.QuranScreen as ActualQuranScreen
import com.deenlearn.app.ui.screens.PrayerScreen as ActualPrayerScreen
import com.deenlearn.app.ui.screens.HadithScreen as ActualHadithScreen
import com.deenlearn.app.ui.screens.ArabicScreen as ActualArabicScreen
import com.deenlearn.app.ui.screens.ProfileScreen as ActualProfileScreen

@Composable
fun MainScreen(
    isKidsMode: Boolean,
    onNavigateToDetail: (String) -> Unit
) {
    var currentTab by remember { mutableStateOf(TabDestination.HOME.route) }
    
    Scaffold(
        topBar = {
            DeenTopBar(
                title = getScreenTitle(currentTab),
                actions = {
                    com.deenlearn.app.ui.components.DefaultTopBarActions()
                }
            )
        },
        bottomBar = {
            DeenBottomNavBar(
                currentRoute = currentTab,
                onNavigate = { route -> currentTab = route },
                isKidsMode = isKidsMode
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when (currentTab) {
                TabDestination.HOME.route -> HomeScreen(
                    isKidsMode = isKidsMode,
                    onNavigateToDetail = onNavigateToDetail
                )
                TabDestination.PILLARS.route -> ActualPillarsScreen(
                    isKidsMode = isKidsMode,
                    onNavigateToPillar = { pillarId -> onNavigateToDetail(Screen.PillarDetail.createRoute(pillarId)) }
                )
                TabDestination.QURAN.route -> ActualQuranScreen(
                    isKidsMode = isKidsMode,
                    onNavigateToSurah = { surahId -> onNavigateToDetail(Screen.QuranReader.createRoute(surahId.toString())) }
                )
                TabDestination.PRAYER.route -> ActualPrayerScreen(
                    isKidsMode = isKidsMode,
                    onNavigateToDetail = onNavigateToDetail
                )
                TabDestination.ARABIC.route -> ActualArabicScreen(
                    isKidsMode = isKidsMode,
                    onNavigateToLesson = onNavigateToDetail
                )
                TabDestination.PROFILE.route -> ActualProfileScreen(
                    isKidsMode = isKidsMode
                )
                TabDestination.HADITH.route -> ActualHadithScreen(
                    isKidsMode = isKidsMode,
                    onNavigateToDetail = onNavigateToDetail
                )
            }
        }
    }
}

private fun getScreenTitle(route: String): String {
    return when (route) {
        TabDestination.HOME.route -> "DeenLearn"
        TabDestination.PILLARS.route -> "Five Pillars of Islam"
        TabDestination.QURAN.route -> "Quran"
        TabDestination.PRAYER.route -> "Prayer Times"
        TabDestination.ARABIC.route -> "Learn Arabic"
        TabDestination.PROFILE.route -> "My Profile"
        TabDestination.HADITH.route -> "Hadith"
        else -> "DeenLearn"
    }
}
