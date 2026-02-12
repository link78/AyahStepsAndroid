package com.deenlearn.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.deenlearn.app.ui.components.ElevatedDeenCard
import com.deenlearn.app.ui.components.FeatureCard
import com.deenlearn.app.ui.navigation.Screen

@Composable
fun HomeScreen(
    isKidsMode: Boolean,
    onNavigateToDetail: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            GreetingSection(isKidsMode = isKidsMode)
        }
        
        item {
            DailyProgressCard(isKidsMode = isKidsMode)
        }
        
        item {
            QuickActionsSection(
                isKidsMode = isKidsMode,
                onNavigateToDetail = onNavigateToDetail
            )
        }
        
        item {
            TodaysLessonSection(
                isKidsMode = isKidsMode,
                onNavigateToDetail = onNavigateToDetail
            )
        }
        
        item {
            RecentActivitySection(isKidsMode = isKidsMode)
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun GreetingSection(isKidsMode: Boolean) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = if (isKidsMode) "Assalamu Alaikum! 🌟" else "Assalamu Alaikum",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = if (isKidsMode) 
                "Ready to learn something amazing today?" 
            else 
                "Continue your Islamic learning journey",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun DailyProgressCard(isKidsMode: Boolean) {
    ElevatedDeenCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Today's Progress",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                if (isKidsMode) {
                    Text(text = "⭐ 5 stars today!", style = MaterialTheme.typography.bodyMedium)
                }
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProgressItem(
                    icon = Icons.Default.MenuBook,
                    label = "Quran",
                    value = "3/5",
                    color = MaterialTheme.colorScheme.primary
                )
                ProgressItem(
                    icon = Icons.Default.CheckCircle,
                    label = "Prayers",
                    value = "4/5",
                    color = MaterialTheme.colorScheme.tertiary
                )
                ProgressItem(
                    icon = Icons.Default.Star,
                    label = "Lessons",
                    value = "2/3",
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            
            LinearProgressIndicator(
                progress = 0.65f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = if (isKidsMode) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = "Keep up the great work! You're 65% to your daily goal.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ProgressItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    color: androidx.compose.ui.graphics.Color
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = color,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}

@Composable
private fun QuickActionsSection(
    isKidsMode: Boolean,
    onNavigateToDetail: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Quick Actions",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 0.dp)
        ) {
            item {
                QuickActionCard(
                    title = "Prayer Times",
                    icon = Icons.Default.AccessTime,
                    onClick = { onNavigateToDetail(Screen.Prayer.route) }
                )
            }
            item {
                QuickActionCard(
                    title = "Read Quran",
                    icon = Icons.Default.MenuBook,
                    onClick = { onNavigateToDetail(Screen.Quran.route) }
                )
            }
            item {
                QuickActionCard(
                    title = "Learn Arabic",
                    icon = Icons.Default.Language,
                    onClick = { onNavigateToDetail(Screen.Arabic.route) }
                )
            }
            item {
                QuickActionCard(
                    title = "Hadith",
                    icon = Icons.Default.Book,
                    onClick = { onNavigateToDetail(Screen.Hadith.route) }
                )
            }
        }
    }
}

@Composable
private fun QuickActionCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    onClick: () -> Unit
) {
    ElevatedDeenCard(
        modifier = Modifier
            .width(140.dp)
            .height(100.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(36.dp)
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun TodaysLessonSection(
    isKidsMode: Boolean,
    onNavigateToDetail: (String) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Today's Lesson",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        
        FeatureCard(
            title = if (isKidsMode) "The Five Pillars - Fun Edition! 🎨" else "The Five Pillars of Islam",
            description = if (isKidsMode)
                "Let's learn about Shahada, Salah, Zakat, Sawm, and Hajj in a fun way!"
            else
                "Understanding the fundamental practices that form the foundation of Islamic faith and practice.",
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountBalance,
                    contentDescription = "Pillars",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                )
            },
            onClick = { onNavigateToDetail(Screen.Pillars.route) }
        )
    }
}

@Composable
private fun RecentActivitySection(isKidsMode: Boolean) {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = "Recent Activity",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        
        val recentItems = if (isKidsMode) {
            listOf(
                "Completed: Surah Al-Fatiha ⭐⭐⭐",
                "Practiced: Arabic Alphabet Game 🎮",
                "Learned: How to make Wudu 💧"
            )
        } else {
            listOf(
                "Completed: Tafsir of Surah Al-Baqarah (Ayah 1-10)",
                "Studied: The Fundamentals of Tajweed",
                "Read: 40 Hadith on Faith and Belief"
            )
        }
        
        recentItems.forEach { item ->
            ActivityItem(text = item)
        }
    }
}

@Composable
private fun ActivityItem(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
