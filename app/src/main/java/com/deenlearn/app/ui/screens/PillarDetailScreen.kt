package com.deenlearn.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.deenlearn.app.models.*
import com.deenlearn.app.ui.components.DeenTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PillarDetailScreen(
    pillarId: String,
    isKidsMode: Boolean = false,
    onBack: () -> Unit
) {
    val pillar = remember(pillarId) {
        PillarData.getAllPillars().find { it.id == pillarId }
    }
    
    if (pillar == null) {
        // Pillar not found
        Column(modifier = Modifier.fillMaxSize()) {
            DeenTopBar(
                title = "Pillar Not Found",
                onBackClick = onBack
            )
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Pillar not found")
            }
        }
        return
    }
    
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = if (isKidsMode) {
        listOf("📖 Stories", "🎮 Games", "📚 Learn")
    } else {
        listOf("📚 Learn", "📖 Evidence", "💡 Wisdom", "❓ Q&A")
    }
    
    Column(modifier = Modifier.fillMaxSize()) {
        DeenTopBar(
            title = "${pillar.icon} ${pillar.name}",
            onBackClick = onBack
        )
        
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }
        
        // Tab Content
        when {
            isKidsMode -> {
                when (selectedTab) {
                    0 -> StoriesTab(pillar = pillar)
                    1 -> GamesTab(pillar = pillar)
                    2 -> LearnTabKids(pillar = pillar)
                }
            }
            else -> {
                when (selectedTab) {
                    0 -> LearnTabAdult(pillar = pillar)
                    1 -> EvidenceTab(pillar = pillar)
                    2 -> WisdomTab(pillar = pillar)
                    3 -> QATab(pillar = pillar)
                }
            }
        }
    }
}

@Composable
private fun StoriesTab(pillar: Pillar) {
    if (pillar.storyEpisodes.isEmpty()) {
        EmptyStateMessage("No stories available yet")
        return
    }
    
    var selectedStory by remember { mutableStateOf<StoryEpisode?>(null) }
    
    if (selectedStory != null) {
        StoryReader(
            story = selectedStory!!,
            onBack = { selectedStory = null }
        )
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(pillar.storyEpisodes) { story ->
                StoryCard(
                    story = story,
                    onClick = { selectedStory = story }
                )
            }
        }
    }
}

@Composable
private fun StoryCard(
    story: StoryEpisode,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Emoji
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = story.emoji,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Story info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = story.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Narrator: ${story.narrator}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "${story.duration / 60} min read",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Read story",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun StoryReader(
    story: StoryEpisode,
    onBack: () -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // Story header
        Surface(
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = story.emoji,
                    style = MaterialTheme.typography.displayMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = story.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "by ${story.narrator}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        
        // Story content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentPadding = PaddingValues(16.dp)
        ) {
            item {
                Text(
                    text = story.content,
                    style = MaterialTheme.typography.bodyLarge,
                    lineHeight = MaterialTheme.typography.bodyLarge.lineHeight.times(1.5f)
                )
            }
        }
        
        // Back button
        Surface(
            color = MaterialTheme.colorScheme.surface,
            shadowElevation = 8.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Back to Stories")
            }
        }
    }
}

@Composable
private fun GamesTab(pillar: Pillar) {
    if (pillar.miniGames.isEmpty()) {
        EmptyStateMessage("No games available yet")
        return
    }
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(pillar.miniGames) { game ->
            GameCard(game = game)
        }
    }
}

@Composable
private fun GameCard(game: MiniGame) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { /* TODO: Launch game */ },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Game icon
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = game.icon,
                    style = MaterialTheme.typography.headlineMedium
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Game info
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = game.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = game.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFFFFD700)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "Earn 5 stars!",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play game",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
private fun LearnTabKids(pillar: Pillar) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Pillar icon and name
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = pillar.icon,
                    style = MaterialTheme.typography.displayLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = pillar.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = pillar.nameArabic,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        
        // Description
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "What is ${pillar.name}?",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = pillar.description,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight.times(1.5f)
                    )
                }
            }
        }
        
        // Simple definition
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "📚 Learn More",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = pillar.definition,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight.times(1.5f)
                    )
                }
            }
        }
    }
}

@Composable
private fun LearnTabAdult(pillar: Pillar) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = pillar.icon,
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    text = pillar.name,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = pillar.nameArabic,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        
        // Definition
        item {
            SectionCard(
                title = "Definition",
                content = pillar.definition
            )
        }
        
        // Description
        item {
            SectionCard(
                title = "Overview",
                content = pillar.description
            )
        }
        
        // Practical Application
        item {
            SectionCard(
                title = "Practical Application",
                content = pillar.practicalApplication
            )
        }
    }
}

@Composable
private fun EvidenceTab(pillar: Pillar) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Quran Evidence
        item {
            Text(
                text = "📖 Quran Evidence",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(pillar.quranEvidence) { evidence ->
            EvidenceCard(evidence = evidence)
        }
        
        // Hadith Evidence
        item {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "📚 Hadith Evidence",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(pillar.hadithEvidence) { evidence ->
            EvidenceCard(evidence = evidence)
        }
    }
}

@Composable
private fun EvidenceCard(evidence: Evidence) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Arabic text
            Text(
                text = evidence.arabic,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Translation
            Text(
                text = evidence.translation,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight.times(1.5f)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // Reference
            Text(
                text = "— ${evidence.reference}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun WisdomTab(pillar: Pillar) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SectionCard(
                title = "💡 Wisdom & Benefits",
                content = pillar.wisdom
            )
        }
        
        item {
            SectionCard(
                title = "🎯 Practical Application",
                content = pillar.practicalApplication
            )
        }
        
        if (pillar.fiqhDifferences.isNotEmpty()) {
            item {
                Text(
                    text = "⚖️ Fiqh Differences",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            
            items(pillar.fiqhDifferences) { fiqh ->
                FiqhDifferenceCard(fiqh = fiqh)
            }
        }
    }
}

@Composable
private fun FiqhDifferenceCard(fiqh: FiqhDifference) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = fiqh.topic,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            
            MadhabOpinion("Hanafi", fiqh.hanafi)
            Spacer(modifier = Modifier.height(8.dp))
            MadhabOpinion("Maliki", fiqh.maliki)
            Spacer(modifier = Modifier.height(8.dp))
            MadhabOpinion("Shafi'i", fiqh.shafii)
            Spacer(modifier = Modifier.height(8.dp))
            MadhabOpinion("Hanbali", fiqh.hanbali)
        }
    }
}

@Composable
private fun MadhabOpinion(madhabName: String, opinion: String) {
    Column {
        Text(
            text = madhabName,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = opinion,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun QATab(pillar: Pillar) {
    if (pillar.scenarios.isEmpty()) {
        EmptyStateMessage("No Q&A available yet")
        return
    }
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(pillar.scenarios) { scenario ->
            ScenarioCard(scenario = scenario)
        }
    }
}

@Composable
private fun ScenarioCard(scenario: Scenario) {
    var isExpanded by remember { mutableStateOf(false) }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Question
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = scenario.question,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand"
                )
            }
            
            // Category badge
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(
                    text = scenario.category,
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
            
            // Answer (if expanded)
            if (isExpanded) {
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = scenario.answer,
                    style = MaterialTheme.typography.bodyMedium,
                    lineHeight = MaterialTheme.typography.bodyMedium.lineHeight.times(1.5f),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun SectionCard(
    title: String,
    content: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight.times(1.5f)
            )
        }
    }
}

@Composable
private fun EmptyStateMessage(message: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}
