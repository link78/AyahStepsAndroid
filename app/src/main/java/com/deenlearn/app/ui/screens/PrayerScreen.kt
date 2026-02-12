package com.deenlearn.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
fun PrayerScreen(
    isKidsMode: Boolean,
    onNavigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) }
    
    val tabs = if (isKidsMode) {
        listOf("Wudu 💧", "Prayer 🤲", "Duas 🌟")
    } else {
        listOf("Wudu", "Salah", "Duas", "Mistakes")
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isKidsMode) "🤲 Prayer" else "Prayer") }
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
                0 -> WuduView(isKidsMode = isKidsMode)
                1 -> SalahView(isKidsMode = isKidsMode)
                2 -> DuasView(isKidsMode = isKidsMode)
                3 -> if (!isKidsMode) MistakesView()
            }
        }
    }
}

@Composable
private fun WuduView(isKidsMode: Boolean) {
    val wuduSteps = remember {
        listOf(
            Triple("Intention", "نية", "💭"),
            Triple("Say Bismillah", "بسم الله", "🤲"),
            Triple("Wash Hands", "غسل اليدين", "✋"),
            Triple("Rinse Mouth", "المضمضة", "👄"),
            Triple("Rinse Nose", "الاستنشاق", "👃"),
            Triple("Wash Face", "غسل الوجه", "😊"),
            Triple("Wash Arms", "غسل الذراعين", "💪"),
            Triple("Wipe Head", "مسح الرأس", "👨"),
            Triple("Wipe Ears", "مسح الأذنين", "👂"),
            Triple("Wash Feet", "غسل القدمين", "🦶")
        )
    }
    
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
                        text = if (isKidsMode) "Learn Wudu! 💧" else "Wudu (Ablution)",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isKidsMode)
                            "Follow these steps to get clean and ready for prayer!"
                        else
                            "Learn the proper steps to perform ablution before prayer",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        items(wuduSteps.size) { index ->
            WuduStepCard(
                stepNumber = index + 1,
                name = wuduSteps[index].first,
                arabic = wuduSteps[index].second,
                emoji = wuduSteps[index].third,
                isKidsMode = isKidsMode
            )
        }
    }
}

@Composable
private fun WuduStepCard(
    stepNumber: Int,
    name: String,
    arabic: String,
    emoji: String,
    isKidsMode: Boolean
) {
    ElevatedDeenCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = { }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isKidsMode) emoji else stepNumber.toString(),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = arabic,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Watch",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun SalahView(isKidsMode: Boolean) {
    val salahPositions = remember {
        listOf(
            Triple("Standing", "قيام", "🧍"),
            Triple("Bowing", "ركوع", "🙇"),
            Triple("Prostration", "سجود", "🧎"),
            Triple("Sitting", "جلوس", "🪑"),
            Triple("Tashahhud", "تشهد", "☝️")
        )
    }
    
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
                        text = if (isKidsMode) "How to Pray! 🤲" else "Salah Positions",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isKidsMode)
                            "Learn each position and what to say!"
                        else
                            "Master the positions and recitations of prayer",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        items(salahPositions) { (name, arabic, emoji) ->
            SalahPositionCard(
                name = name,
                arabic = arabic,
                emoji = emoji,
                isKidsMode = isKidsMode
            )
        }
        
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (isKidsMode) "Watch Full Prayer 📹" else "Watch Full Prayer")
            }
        }
    }
}

@Composable
private fun SalahPositionCard(
    name: String,
    arabic: String,
    emoji: String,
    isKidsMode: Boolean
) {
    ElevatedDeenCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = { }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = emoji,
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.size(48.dp),
                textAlign = TextAlign.Center
            )
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = arabic,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Details",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun DuasView(isKidsMode: Boolean) {
    val duaCategories = remember {
        DuaCategory.values().toList()
    }
    
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
                        text = if (isKidsMode) "Daily Duas 🌟" else "Daily Duas & Adhkar",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isKidsMode)
                            "Special words to say every day!"
                        else
                            "Learn and practice daily supplications",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        items(duaCategories) { category ->
            DuaCategoryCard(
                category = category,
                isKidsMode = isKidsMode
            )
        }
    }
}

@Composable
private fun DuaCategoryCard(
    category: DuaCategory,
    isKidsMode: Boolean
) {
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
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = category.displayName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = when (category) {
                        DuaCategory.MORNING_ADHKAR -> "Start your day with blessings"
                        DuaCategory.EVENING_ADHKAR -> "End your day with peace"
                        DuaCategory.AFTER_PRAYER -> "After each salah"
                        DuaCategory.PROTECTION -> "Seeking Allah's protection"
                        DuaCategory.TRAVEL -> "For journeys and travel"
                        DuaCategory.HEALING -> "For health and wellness"
                        DuaCategory.GENERAL -> "For daily situations"
                    },
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Open",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun MistakesView() {
    val mistakeCategories = remember {
        MistakeCategory.values().toList()
    }
    
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
                        text = "Common Mistakes",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Learn to avoid common errors in prayer and ablution",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        items(mistakeCategories) { category ->
            MistakeCategoryCard(category = category)
        }
    }
}

@Composable
private fun MistakeCategoryCard(category: MistakeCategory) {
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(32.dp)
                )
                Column {
                    Text(
                        text = "${category.displayName} Mistakes",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Common errors to avoid",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Open",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
