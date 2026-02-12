package com.deenlearn.app.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.deenlearn.app.ui.navigation.TabDestination

@Composable
fun DeenBottomNavBar(
    currentRoute: String,
    onNavigate: (String) -> Unit,
    isKidsMode: Boolean = false
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        TabDestination.values().forEach { destination ->
            NavigationBarItem(
                selected = currentRoute == destination.route,
                onClick = { onNavigate(destination.route) },
                icon = {
                    Icon(
                        imageVector = getIconForTab(destination),
                        contentDescription = destination.title
                    )
                },
                label = {
                    Text(
                        text = destination.title,
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}

private fun getIconForTab(destination: TabDestination): ImageVector {
    return when (destination) {
        TabDestination.HOME -> Icons.Default.Home
        TabDestination.PILLARS -> Icons.Default.AccountBalance
        TabDestination.QURAN -> Icons.Default.MenuBook
        TabDestination.PRAYER -> Icons.Default.Place
        TabDestination.ARABIC -> Icons.Default.Language
        TabDestination.PROFILE -> Icons.Default.Person
        TabDestination.HADITH -> Icons.Default.Book
    }
}
