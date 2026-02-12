package com.deenlearn.app.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.deenlearn.app.ui.screens.MainScreen
import com.deenlearn.app.ui.screens.WelcomeScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = Screen.Welcome.route
) {
    var isKidsMode by remember { mutableStateOf(false) }
    
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onModeSelected = { kidsMode ->
                    isKidsMode = kidsMode
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Welcome.route) { inclusive = true }
                    }
                }
            )
        }
        
        composable(Screen.Main.route) {
            MainScreen(
                isKidsMode = isKidsMode,
                onNavigateToDetail = { route ->
                    navController.navigate(route)
                }
            )
        }
        
        // Detail screens with arguments
        composable(
            route = Screen.PillarDetail.route,
            arguments = listOf(navArgument("pillarId") { type = NavType.StringType })
        ) { backStackEntry ->
            val pillarId = backStackEntry.arguments?.getString("pillarId")
            // Placeholder - will be implemented later
            PlaceholderDetailScreen(
                title = "Pillar Details",
                id = pillarId ?: "",
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = Screen.QuranReader.route,
            arguments = listOf(navArgument("surahId") { type = NavType.StringType })
        ) { backStackEntry ->
            val surahId = backStackEntry.arguments?.getString("surahId")
            PlaceholderDetailScreen(
                title = "Quran Reader",
                id = surahId ?: "",
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = Screen.PrayerDetail.route,
            arguments = listOf(navArgument("prayerId") { type = NavType.StringType })
        ) { backStackEntry ->
            val prayerId = backStackEntry.arguments?.getString("prayerId")
            PlaceholderDetailScreen(
                title = "Prayer Details",
                id = prayerId ?: "",
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = Screen.ArabicLesson.route,
            arguments = listOf(navArgument("lessonId") { type = NavType.StringType })
        ) { backStackEntry ->
            val lessonId = backStackEntry.arguments?.getString("lessonId")
            PlaceholderDetailScreen(
                title = "Arabic Lesson",
                id = lessonId ?: "",
                onBack = { navController.popBackStack() }
            )
        }
        
        composable(
            route = Screen.HadithDetail.route,
            arguments = listOf(navArgument("hadithId") { type = NavType.StringType })
        ) { backStackEntry ->
            val hadithId = backStackEntry.arguments?.getString("hadithId")
            PlaceholderDetailScreen(
                title = "Hadith Details",
                id = hadithId ?: "",
                onBack = { navController.popBackStack() }
            )
        }
    }
}

@Composable
private fun PlaceholderDetailScreen(
    title: String,
    id: String,
    onBack: () -> Unit
) {
    androidx.compose.foundation.layout.Column(
        modifier = androidx.compose.ui.Modifier.fillMaxSize()
    ) {
        com.deenlearn.app.ui.components.DeenTopBar(
            title = title,
            onBackClick = onBack
        )
        androidx.compose.foundation.layout.Box(
            modifier = androidx.compose.ui.Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = androidx.compose.ui.Alignment.Center
        ) {
            androidx.compose.material3.Text(
                text = "Detail screen for ID: $id\n\nThis will be implemented in a future update.",
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}
