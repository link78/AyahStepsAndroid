package com.deenlearn.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import com.deenlearn.app.ui.components.SmallSpeakerButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PillarsScreen(
    isKidsMode: Boolean,
    onNavigateToPillar: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) }
    
    val tabs = if (isKidsMode) {
        listOf("Learn 🕌", "Stories 📖", "Games 🎮")
    } else {
        listOf("Overview", "Learn", "Quiz")
    }
    
    val pillars = remember {
        listOf(
            PillarInfo("1", "Shahada", "الشهادة", "Faith", "☝️", "#4CAF50"),
            PillarInfo("2", "Salah", "الصلاة", "Prayer", "🤲", "#2196F3"),
            PillarInfo("3", "Zakat", "الزكاة", "Charity", "💝", "#FF9800"),
            PillarInfo("4", "Sawm", "الصوم", "Fasting", "🌙", "#9C27B0"),
            PillarInfo("5", "Hajj", "الحج", "Pilgrimage", "🕋", "#F44336")
        )
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isKidsMode) "🕌 Pillars" else "Five Pillars of Islam") }
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
                0 -> PillarsOverviewView(
                    pillars = pillars,
                    isKidsMode = isKidsMode,
                    onPillarClick = onNavigateToPillar
                )
                1 -> if (isKidsMode) {
                    StoriesView(pillars = pillars)
                } else {
                    PillarsLearnView(pillars = pillars, onPillarClick = onNavigateToPillar)
                }
                2 -> if (isKidsMode) {
                    GamesView()
                } else {
                    QuizView()
                }
            }
        }
    }
}

data class PillarInfo(
    val id: String,
    val name: String,
    val arabic: String,
    val description: String,
    val emoji: String,
    val color: String
)

@Composable
private fun PillarsOverviewView(
    pillars: List<PillarInfo>,
    isKidsMode: Boolean,
    onPillarClick: (String) -> Unit
) {
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
                        text = if (isKidsMode)
                            "The 5 Super Pillars! 🌟"
                        else
                            "The Five Pillars",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isKidsMode)
                            "These are the 5 most important things every Muslim should know and do!"
                        else
                            "The Five Pillars of Islam are the foundation of Muslim life and practice.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    if (isKidsMode) {
                        LinearProgressIndicator(
                            progress = 0.6f,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = "3 of 5 completed! Keep going! 🎉",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
        
        items(pillars) { pillar ->
            PillarCard(
                pillar = pillar,
                isKidsMode = isKidsMode,
                onClick = { onPillarClick(pillar.id) }
            )
        }
    }
}

@Composable
private fun PillarCard(
    pillar: PillarInfo,
    isKidsMode: Boolean,
    onClick: () -> Unit
) {
    ElevatedDeenCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
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
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = pillar.emoji,
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center
                )
            }
            
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = pillar.id,
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = pillar.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = pillar.arabic,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = pillar.description,
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
private fun PillarsLearnView(
    pillars: List<PillarInfo>,
    onPillarClick: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Deep Dive into Each Pillar",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        items(pillars) { pillar ->
            LearnCard(pillar = pillar, onClick = { onPillarClick(pillar.id) })
        }
    }
}

@Composable
private fun LearnCard(pillar: PillarInfo, onClick: () -> Unit) {
    ElevatedDeenCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = pillar.emoji,
                    style = MaterialTheme.typography.displaySmall
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = pillar.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = pillar.arabic,
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            Text(
                text = "Learn about the ${pillar.description.lowercase()} in Islam",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssistChip(
                    onClick = { },
                    label = { Text("Evidence") },
                    leadingIcon = { Icon(Icons.Default.Book, null, modifier = Modifier.size(18.dp)) }
                )
                AssistChip(
                    onClick = { },
                    label = { Text("Stories") },
                    leadingIcon = { Icon(Icons.Default.AutoStories, null, modifier = Modifier.size(18.dp)) }
                )
                AssistChip(
                    onClick = { },
                    label = { Text("Practice") },
                    leadingIcon = { Icon(Icons.Default.PlayArrow, null, modifier = Modifier.size(18.dp)) }
                )
            }
        }
    }
}

@Composable
private fun StoriesView(pillars: List<PillarInfo>) {
    val allStories = remember { PillarData.getAllStories() }
    var expandedStory by remember { mutableStateOf<String?>(null) }
    
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
                        text = "Story Time! 📖",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Listen to amazing stories about each pillar!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        allStories.forEach { (pillarName, stories) ->
            item {
                Text(
                    text = "$pillarName Stories",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
            
            items(stories) { story ->
                StoryDetailCard(
                    story = story,
                    isExpanded = expandedStory == story.id,
                    onToggleExpand = {
                        expandedStory = if (expandedStory == story.id) null else story.id
                    }
                )
            }
        }
    }
}

@Composable
private fun StoryDetailCard(
    story: StoryEpisode,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    ElevatedDeenCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = onToggleExpand
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = story.emoji,
                    style = MaterialTheme.typography.displaySmall
                )
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = story.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Narrated by ${story.narrator} • ${story.duration / 60} min",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            if (isExpanded) {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
                Text(
                    text = story.content,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.5f
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SmallSpeakerButton(
                        text = story.content,
                        contentDescription = "Listen to story"
                    )
                    AssistChip(
                        onClick = { },
                        label = { Text("Share") },
                        leadingIcon = { Icon(Icons.Default.Share, null, modifier = Modifier.size(18.dp)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun StoryCard(pillar: PillarInfo) {
    ElevatedDeenCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = { }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = pillar.emoji,
                style = MaterialTheme.typography.displayMedium
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Story of ${pillar.name}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "A fun story about ${pillar.description.lowercase()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

@Composable
private fun GamesView() {
    val games = remember {
        listOf(
            "Match Pillars" to "🎯",
            "Build a Pillar" to "🏗️",
            "Order the Pillars" to "🔢",
            "Quiz Challenge" to "❓"
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
                        text = "Fun Games! 🎮",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Play games to learn about the pillars!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        items(games) { (name, emoji) ->
            GameCard(name = name, emoji = emoji)
        }
    }
}

@Composable
private fun GameCard(name: String, emoji: String) {
    ElevatedDeenCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = { }
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
                    text = "Tap to play!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Play",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun QuizView() {
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
                        text = "Test Your Knowledge",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Take quizzes to reinforce your learning",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        QuizStat("Completed", "12")
                        QuizStat("Average", "85%")
                        QuizStat("Best", "100%")
                    }
                }
            }
        }
        
        item {
            Text(
                text = "Available Quizzes",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        items(5) { index ->
            QuizCard(
                level = listOf("Beginner", "Intermediate", "Advanced", "Expert", "Master")[index],
                questions = 10,
                completed = index < 3
            )
        }
    }
}

@Composable
private fun QuizStat(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineSmall,
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
private fun QuizCard(
    level: String,
    questions: Int,
    completed: Boolean
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
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$level Quiz",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    if (completed) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Completed",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                Text(
                    text = "$questions questions",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Button(
                onClick = { }
            ) {
                Text(if (completed) "Retake" else "Start")
            }
        }
    }
}
