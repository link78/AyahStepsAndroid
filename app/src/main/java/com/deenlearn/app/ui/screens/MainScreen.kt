package com.deenlearn.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.deenlearn.app.ui.components.DeenBottomNavBar
import com.deenlearn.app.ui.components.DeenTopBar
import com.deenlearn.app.ui.navigation.TabDestination

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
                TabDestination.PILLARS.route -> PillarsScreen(
                    isKidsMode = isKidsMode,
                    onNavigateToDetail = onNavigateToDetail
                )
                TabDestination.QURAN.route -> QuranScreen(
                    isKidsMode = isKidsMode,
                    onNavigateToDetail = onNavigateToDetail
                )
                TabDestination.PRAYER.route -> PrayerScreen(
                    isKidsMode = isKidsMode,
                    onNavigateToDetail = onNavigateToDetail
                )
                TabDestination.ARABIC.route -> ArabicScreen(
                    isKidsMode = isKidsMode,
                    onNavigateToDetail = onNavigateToDetail
                )
                TabDestination.PROFILE.route -> ProfileScreen(
                    isKidsMode = isKidsMode
                )
                TabDestination.HADITH.route -> HadithScreen(
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

@Composable
private fun PillarsScreen(
    isKidsMode: Boolean,
    onNavigateToDetail: (String) -> Unit
) {
    PlaceholderScreen(
        title = "Five Pillars of Islam",
        description = "Learn about the fundamental practices of Islam"
    )
}

@Composable
private fun QuranScreen(
    isKidsMode: Boolean,
    onNavigateToDetail: (String) -> Unit
) {
    PlaceholderScreen(
        title = "Quran",
        description = "Read, listen, and learn the Holy Quran"
    )
}

@Composable
private fun PrayerScreen(
    isKidsMode: Boolean,
    onNavigateToDetail: (String) -> Unit
) {
    PlaceholderScreen(
        title = "Prayer Times",
        description = "View prayer times and learn how to pray"
    )
}

@Composable
private fun ArabicScreen(
    isKidsMode: Boolean,
    onNavigateToDetail: (String) -> Unit
) {
    PlaceholderScreen(
        title = "Learn Arabic",
        description = "Master the language of the Quran"
    )
}

@Composable
private fun ProfileScreen(
    isKidsMode: Boolean
) {
    PlaceholderScreen(
        title = "My Profile",
        description = "Track your progress and achievements"
    )
}

@Composable
private fun HadithScreen(
    isKidsMode: Boolean,
    onNavigateToDetail: (String) -> Unit
) {
    PlaceholderScreen(
        title = "Hadith",
        description = "Study the teachings of Prophet Muhammad (PBUH)"
    )
}

@Composable
private fun PlaceholderScreen(
    title: String,
    description: String
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Column(
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
            Text(
                text = "Coming soon...",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
