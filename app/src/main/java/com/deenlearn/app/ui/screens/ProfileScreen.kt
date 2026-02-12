package com.deenlearn.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.deenlearn.app.models.*
import com.deenlearn.app.ui.components.ElevatedDeenCard
import com.deenlearn.app.ui.components.OutlinedDeenCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    isKidsMode: Boolean,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) }
    
    val tabs = if (isKidsMode) {
        listOf("Me 👤", "Stars ⭐", "Badges 🏅")
    } else {
        listOf("Profile", "Statistics", "Achievements", "Settings")
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isKidsMode) "👤 My Profile" else "Profile") }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                modifier = Modifier.fillMaxWidth(),
                edgePadding = 16.dp
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = { Text(title) }
                    )
                }
            }
            
            when (selectedTab) {
                0 -> ProfileView(isKidsMode = isKidsMode)
                1 -> StatisticsView(isKidsMode = isKidsMode)
                2 -> AchievementsView(isKidsMode = isKidsMode)
                3 -> if (!isKidsMode) SettingsView()
            }
        }
    }
}

@Composable
private fun ProfileView(isKidsMode: Boolean) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            ElevatedDeenCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isKidsMode) "🧒" else "👤",
                            style = MaterialTheme.typography.displayLarge
                        )
                    }
                    
                    Text(
                        text = if (isKidsMode) "Ahmed" else "Ahmed Ibrahim",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    if (!isKidsMode) {
                        Text(
                            text = "ahmed@example.com",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ProfileStat(
                            label = if (isKidsMode) "Stars" else "Points",
                            value = "1,245",
                            icon = "⭐"
                        )
                        ProfileStat(
                            label = "Streak",
                            value = "15",
                            icon = "🔥"
                        )
                        ProfileStat(
                            label = "Level",
                            value = "7",
                            icon = "🏆"
                        )
                    }
                }
            }
        }
        
        item {
            Text(
                text = if (isKidsMode) "Today's Goals 🎯" else "Daily Goals",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            DailyGoalsCard(isKidsMode = isKidsMode)
        }
        
        item {
            Text(
                text = if (isKidsMode) "This Week 📅" else "Weekly Progress",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            WeeklyProgressCard(isKidsMode = isKidsMode)
        }
    }
}

@Composable
private fun ProfileStat(label: String, value: String, icon: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = icon,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun DailyGoalsCard(isKidsMode: Boolean) {
    ElevatedDeenCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            GoalItem(
                name = if (isKidsMode) "Read Quran 📖" else "Quran Reading",
                progress = 0.8f,
                target = "20 minutes"
            )
            GoalItem(
                name = if (isKidsMode) "Learn Arabic 📝" else "Arabic Practice",
                progress = 0.6f,
                target = "15 minutes"
            )
            GoalItem(
                name = if (isKidsMode) "Duas 🤲" else "Prayer & Duas",
                progress = 1.0f,
                target = "5 duas"
            )
        }
    }
}

@Composable
private fun GoalItem(name: String, progress: Float, target: String) {
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = target,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth(),
            color = if (progress >= 1.0f) 
                MaterialTheme.colorScheme.tertiary 
            else 
                MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun WeeklyProgressCard(isKidsMode: Boolean) {
    ElevatedDeenCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "140 minutes this week",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                listOf(20, 35, 25, 30, 15, 10, 5).forEachIndexed { index, minutes ->
                    DayBar(
                        day = listOf("M", "T", "W", "T", "F", "S", "S")[index],
                        minutes = minutes,
                        isToday = index == 4
                    )
                }
            }
        }
    }
}

@Composable
private fun DayBar(day: String, minutes: Int, isToday: Boolean) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .width(32.dp)
                .height((minutes * 2).dp)
                .background(
                    color = if (isToday) 
                        MaterialTheme.colorScheme.primary 
                    else 
                        MaterialTheme.colorScheme.primaryContainer,
                    shape = MaterialTheme.shapes.small
                )
        )
        Text(
            text = day,
            style = MaterialTheme.typography.labelSmall,
            color = if (isToday) 
                MaterialTheme.colorScheme.primary 
            else 
                MaterialTheme.colorScheme.onSurfaceVariant,
            fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
private fun StatisticsView(isKidsMode: Boolean) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            ElevatedDeenCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = if (isKidsMode) "My Numbers 📊" else "Overall Statistics",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        StatCard("Minutes", "1,245")
                        StatCard("Lessons", "87")
                        StatCard("Days", "45")
                    }
                }
            }
        }
        
        item {
            Text(
                text = if (isKidsMode) "What I Learned 🎓" else "Learning Breakdown",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        val categories = listOf(
            Triple("Quran", 245, "📖"),
            Triple("Arabic", 180, "📝"),
            Triple("Prayer", 150, "🤲"),
            Triple("Pillars", 120, "🕌")
        )
        
        items(categories) { (name, minutes, emoji) ->
            CategoryStatCard(name = name, minutes = minutes, emoji = emoji)
        }
    }
}

@Composable
private fun StatCard(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun CategoryStatCard(name: String, minutes: Int, emoji: String) {
    ElevatedDeenCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.displaySmall
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$minutes minutes",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun AchievementsView(isKidsMode: Boolean) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            ElevatedDeenCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = if (isKidsMode) "My Badges! 🏅" else "Achievements",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isKidsMode)
                            "You've earned 12 awesome badges!"
                        else
                            "12 of 24 achievements unlocked",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        val achievements = listOf(
            Triple("First Steps", "Complete first lesson", "✅"),
            Triple("Week Warrior", "7 day streak", "🔥"),
            Triple("Quran Reader", "Read 10 surahs", "📖"),
            Triple("Arabic Master", "Learn 50 words", "📝"),
            Triple("Prayer Champion", "Complete prayer guide", "🤲"),
            Triple("Pillar Pro", "Master all 5 pillars", "🕌")
        )
        
        items(achievements) { (name, description, emoji) ->
            AchievementCard(
                name = name,
                description = description,
                emoji = emoji,
                isUnlocked = achievements.indexOf(Triple(name, description, emoji)) < 3
            )
        }
    }
}

@Composable
private fun AchievementCard(
    name: String,
    description: String,
    emoji: String,
    isUnlocked: Boolean
) {
    ElevatedDeenCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(
                        if (isUnlocked)
                            MaterialTheme.colorScheme.primaryContainer
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = emoji,
                    style = MaterialTheme.typography.headlineMedium,
                    color = if (isUnlocked)
                        MaterialTheme.colorScheme.onPrimaryContainer
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                )
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isUnlocked)
                        MaterialTheme.colorScheme.onSurface
                    else
                        MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (isUnlocked) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Unlocked",
                    tint = MaterialTheme.colorScheme.primary
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun SettingsView() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        val settingsSections = listOf(
            "Account Settings" to listOf("Edit Profile", "Email", "Password"),
            "Preferences" to listOf("Language", "Theme", "Notifications"),
            "Learning" to listOf("Daily Goals", "Reminders", "Display Options"),
            "About" to listOf("Version", "Privacy Policy", "Terms of Service")
        )
        
        settingsSections.forEach { (section, items) ->
            item {
                Text(
                    text = section,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                )
            }
            
            items(items) { item ->
                SettingItem(name = item)
            }
        }
    }
}

@Composable
private fun SettingItem(name: String) {
    ElevatedDeenCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = { }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodyLarge
            )
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Open",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
