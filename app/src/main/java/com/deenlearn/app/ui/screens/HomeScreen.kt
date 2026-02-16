package com.deenlearn.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.deenlearn.app.services.PrayerTimeService
import com.deenlearn.app.services.LocationService
import com.deenlearn.app.ui.components.ElevatedDeenCard
import com.deenlearn.app.ui.components.FeatureCard
import com.deenlearn.app.ui.navigation.Screen

@Composable
fun HomeScreen(
    isKidsMode: Boolean,
    onNavigateToDetail: (String) -> Unit
) {
    val context = LocalContext.current
    val prayerTimeService = remember { PrayerTimeService.getInstance(context) }
    val locationService = remember { LocationService.getInstance(context) }
    
    val prayerTimes by prayerTimeService.prayerTimes.collectAsState()
    val nextPrayer by prayerTimeService.nextPrayer.collectAsState()
    val timeUntilNext by prayerTimeService.timeUntilNextPrayer.collectAsState()
    val hijriDate by prayerTimeService.hijriDate.collectAsState()
    val locationName by locationService.locationName.collectAsState()
    val isLoadingPrayer by prayerTimeService.isLoading.collectAsState()
    val qiblaDirection by prayerTimeService.qiblaDirection.collectAsState()
    
    var showQiblaDialog by remember { mutableStateOf(false) }
    
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
            PrayerTimesCard(
                nextPrayer = nextPrayer,
                timeUntilNext = timeUntilNext,
                prayerTimes = prayerTimes,
                hijriDate = hijriDate,
                locationName = locationName,
                isLoading = isLoadingPrayer,
                isKidsMode = isKidsMode,
                onQiblaClick = { showQiblaDialog = true }
            )
        }
        
        item {
            DailyProgressCard(isKidsMode = isKidsMode)
        }
        
        item {
            QuickActionsSection(
                isKidsMode = isKidsMode,
                onNavigateToDetail = onNavigateToDetail,
                onQiblaClick = { showQiblaDialog = true }
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
    
    if (showQiblaDialog) {
        QiblaCompassDialog(
            qiblaDirection = qiblaDirection,
            locationName = locationName,
            onDismiss = { showQiblaDialog = false }
        )
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
private fun PrayerTimesCard(
    nextPrayer: com.deenlearn.app.services.PrayerTime?,
    timeUntilNext: String,
    prayerTimes: List<com.deenlearn.app.services.PrayerTime>,
    hijriDate: String,
    locationName: String,
    isLoading: Boolean,
    isKidsMode: Boolean,
    onQiblaClick: () -> Unit
) {
    ElevatedDeenCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Header with Qibla button
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.WatchLater,
                            contentDescription = "Prayer Times",
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = if (isKidsMode) "🕌 Prayer Times" else "Prayer Times",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    if (locationName.isNotEmpty()) {
                        Text(
                            text = "📍 $locationName",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                // Qibla Compass Button
                FilledTonalButton(
                    onClick = onQiblaClick,
                    modifier = Modifier.height(36.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Explore,
                        contentDescription = "Qibla",
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Qibla", style = MaterialTheme.typography.labelMedium)
                }
            }
            
            if (hijriDate.isNotEmpty()) {
                Text(
                    text = "📅 $hijriDate",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Divider()
            
            // Next Prayer Highlight
            if (nextPrayer != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Next Prayer",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                            Text(
                                text = if (isKidsMode) 
                                    "${nextPrayer.name} ${getEmojiForPrayer(nextPrayer.name)}"
                                else 
                                    "${nextPrayer.name} - ${nextPrayer.arabicName}",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = timeUntilNext,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = nextPrayer.formattedTime(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }
            
            // All Prayer Times
            if (prayerTimes.isNotEmpty()) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    prayerTimes.filter { it.isPrayer }.forEach { prayer ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = getEmojiForPrayer(prayer.name),
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Column {
                                    Text(
                                        text = prayer.name,
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontWeight = FontWeight.Medium
                                    )
                                    if (!isKidsMode) {
                                        Text(
                                            text = prayer.arabicName,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                            Text(
                                text = prayer.formattedTime(),
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (prayer == nextPrayer) 
                                    MaterialTheme.colorScheme.primary 
                                else 
                                    MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            } else if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

private fun getEmojiForPrayer(prayerName: String): String {
    return when (prayerName.lowercase()) {
        "fajr" -> "🌅"
        "dhuhr" -> "☀️"
        "asr" -> "🌤️"
        "maghrib" -> "🌆"
        "isha" -> "🌙"
        else -> "🕌"
    }
}

@Composable
private fun QiblaCompassDialog(
    qiblaDirection: Double?,
    locationName: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Explore,
                    contentDescription = "Qibla",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text("Qibla Direction")
            }
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (qiblaDirection != null) {
                    // Compass visualization
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Navigation,
                            contentDescription = "Qibla Direction",
                            modifier = Modifier.size(120.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    
                    Text(
                        text = "${qiblaDirection.toInt()}° from North",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    Text(
                        text = "Direction to Ka'bah, Makkah",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    if (locationName.isNotEmpty()) {
                        Text(
                            text = "From: $locationName",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    
                    Text(
                        text = "📱 Tip: For best results, lay your phone flat and rotate yourself until the compass needle points towards the Ka'bah direction.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Calculating Qibla direction...",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
private fun QuickActionsSection(
    isKidsMode: Boolean,
    onNavigateToDetail: (String) -> Unit,
    onQiblaClick: () -> Unit
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
                    title = "Qibla Compass",
                    icon = Icons.Default.Explore,
                    onClick = onQiblaClick
                )
            }
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
