package com.deenlearn.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.deenlearn.app.DeenLearnApplication
import com.deenlearn.app.models.*
import com.deenlearn.app.services.VerseAudio
import com.deenlearn.app.ui.components.ElevatedDeenCard
import com.deenlearn.app.ui.components.MiniAudioPlayer
import com.deenlearn.app.ui.components.OutlinedDeenCard
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuranScreen(
    isKidsMode: Boolean,
    onNavigateToSurah: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val audioService = remember { 
        (context.applicationContext as DeenLearnApplication).quranAudioService 
    }
    val currentPlaying by audioService.currentPlaying.collectAsState()
    
    var selectedTab by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    var showSearch by remember { mutableStateOf(false) }
    var showFullPlayer by remember { mutableStateOf(false) }
    
    val tabs = if (isKidsMode) {
        listOf("Juz Amma 📖", "Memorize 🌟", "Games 🎮")
    } else {
        listOf("Reading", "Memorization", "Bookmarks", "Tajweed")
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isKidsMode) "📖 Quran" else "Quran") },
                actions = {
                    IconButton(onClick = { showSearch = !showSearch }) {
                        Icon(
                            imageVector = if (showSearch) Icons.Default.Close else Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                }
            )
        },
        bottomBar = {
            if (currentPlaying != null) {
                MiniAudioPlayer(
                    onExpand = { showFullPlayer = true }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                if (showSearch) {
                    SearchBar(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
                
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
                    0 -> SurahListView(
                        isKidsMode = isKidsMode,
                        searchQuery = searchQuery,
                        onSurahClick = onNavigateToSurah
                    )
                    1 -> MemorizationView(isKidsMode = isKidsMode)
                    2 -> if (isKidsMode) {
                        QuranGamesView()
                    } else {
                        BookmarksView()
                    }
                    3 -> if (!isKidsMode) TajweedView()
                }
            }
            
            // Full player dialog
            if (showFullPlayer) {
                val context2 = LocalContext.current
                val audioService2 = remember { 
                    (context2.applicationContext as DeenLearnApplication).quranAudioService 
                }
                
                com.deenlearn.app.ui.components.FullAudioPlayer(
                    isKidsMode = isKidsMode,
                    onDismiss = { showFullPlayer = false }
                )
            }
        }
    }
}

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier,
        placeholder = { Text("Search surahs...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null)
        },
        singleLine = true,
        shape = MaterialTheme.shapes.medium
    )
}

@Composable
private fun SurahListView(
    isKidsMode: Boolean,
    searchQuery: String,
    onSurahClick: (Int) -> Unit
) {
    val surahs = remember { QuranData.juzAmmaSurahs }
    val filteredSurahs = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            surahs
        } else {
            surahs.filter {
                it.name.contains(searchQuery, ignoreCase = true) ||
                it.nameArabic.contains(searchQuery) ||
                it.englishMeaning.contains(searchQuery, ignoreCase = true)
            }
        }
    }
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            if (isKidsMode) {
                ElevatedDeenCard(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Your Progress",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "5 Surahs Memorized! 🌟",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        CircularProgressIndicator(
                            progress = 0.13f,
                            modifier = Modifier.size(48.dp),
                            strokeWidth = 4.dp
                        )
                    }
                }
            }
        }
        
        items(filteredSurahs) { surah ->
            SurahCard(
                surah = surah,
                isKidsMode = isKidsMode,
                onClick = { onSurahClick(surah.id) }
            )
        }
    }
}

@Composable
private fun SurahCard(
    surah: Surah,
    isKidsMode: Boolean,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val audioService = remember { 
        (context.applicationContext as DeenLearnApplication).quranAudioService 
    }
    val scope = rememberCoroutineScope()
    val quranAPIService = remember { 
        (context.applicationContext as DeenLearnApplication).quranAPIService 
    }
    
    var showStory by remember { mutableStateOf(false) }
    val story = remember(surah.id) { QuranData.getSurahStory(surah.id) }
    var isLoadingAudio by remember { mutableStateOf(false) }
    
    ElevatedDeenCard(
        modifier = Modifier.fillMaxWidth(),
        onClick = {
            if (isKidsMode && story != null) {
                showStory = !showStory
            } else {
                onClick()
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isKidsMode) surah.kidsEmoji else surah.id.toString(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                    
                    Column {
                        Text(
                            text = surah.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = surah.nameArabic,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "${surah.englishMeaning} • ${surah.verseCount} verses",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                Icon(
                    imageVector = if (showStory) Icons.Default.ExpandLess else Icons.Default.ChevronRight,
                    contentDescription = if (showStory) "Collapse" else "Open",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (showStory && story != null && isKidsMode) {
                HorizontalDivider()
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Story title
                    Text(
                        text = story.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    
                    // Kids explanation
                    Text(
                        text = story.kidsExplanation,
                        style = MaterialTheme.typography.bodyMedium,
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.5f
                    )
                    
                    // Fun Fact
                    ElevatedDeenCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "🎉 Fun Fact!",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Text(
                                text = story.funFact,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    
                    // Lesson
                    ElevatedDeenCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "📚 Lesson",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                            Text(
                                text = story.lesson,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    
                    // Memory Tips
                    ElevatedDeenCard(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "💡 Memory Tips",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = story.memorytips,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                    
                    // Action buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AssistChip(
                            onClick = onClick,
                            label = { Text("Read Surah") },
                            leadingIcon = { Icon(Icons.Default.Book, null, modifier = Modifier.size(18.dp)) }
                        )
                        AssistChip(
                            onClick = {
                                // Play full surah audio
                                scope.launch {
                                    isLoadingAudio = true
                                    val result = quranAPIService.fetchSurah(surah.id)
                                    isLoadingAudio = false
                                    
                                    result.onSuccess { surahDetail ->
                                        val verseAudios = surahDetail.verses.map { verse ->
                                            VerseAudio(
                                                verseNumber = verse.number.inSurah,
                                                audioUrl = verse.audio.primary
                                            )
                                        }
                                        audioService.playSurah(
                                            audioUrls = verseAudios,
                                            surahId = surah.id,
                                            surahName = surah.name
                                        )
                                    }
                                }
                            },
                            label = { Text(if (isLoadingAudio) "Loading..." else "Listen") },
                            leadingIcon = { 
                                if (isLoadingAudio) {
                                    CircularProgressIndicator(modifier = Modifier.size(18.dp), strokeWidth = 2.dp)
                                } else {
                                    Icon(Icons.Default.PlayArrow, null, modifier = Modifier.size(18.dp))
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun MemorizationView(isKidsMode: Boolean) {
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
                        text = if (isKidsMode) "My Stars ⭐" else "Memorization Progress",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        StatItem("Memorized", "5", "surahs")
                        StatItem("Reviewed", "12", "times")
                        StatItem("Streak", "7", "days")
                    }
                }
            }
        }
        
        item {
            Text(
                text = if (isKidsMode) "Practice Today 📚" else "Continue Practice",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        items(3) { index ->
            MemorizationCard(
                surahName = listOf("Al-Ikhlas", "Al-Falaq", "An-Nas")[index],
                progress = listOf(1.0f, 0.8f, 0.4f)[index],
                lastPracticed = listOf("Today", "Yesterday", "3 days ago")[index],
                isKidsMode = isKidsMode
            )
        }
    }
}

@Composable
private fun StatItem(label: String, value: String, unit: String) {
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
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = unit,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MemorizationCard(
    surahName: String,
    progress: Float,
    lastPracticed: String,
    isKidsMode: Boolean
) {
    ElevatedDeenCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = surahName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${(progress * 100).toInt()}%",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Last practiced: $lastPracticed",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Button(
                    onClick = { },
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(if (isKidsMode) "Practice 🎯" else "Practice")
                }
            }
        }
    }
}

@Composable
private fun BookmarksView() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Your Bookmarks",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        items(3) { index ->
            BookmarkCard(
                surahName = listOf("Al-Baqarah", "Al-Kahf", "Ya-Sin")[index],
                ayahNumber = listOf(255, 10, 1)[index],
                note = "Important verse to memorize"
            )
        }
    }
}

@Composable
private fun BookmarkCard(
    surahName: String,
    ayahNumber: Int,
    note: String
) {
    ElevatedDeenCard(
        modifier = Modifier.fillMaxWidth()
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
                    text = "$surahName : $ayahNumber",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = note,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.Default.Bookmark,
                contentDescription = "Bookmark",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun TajweedView() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Tajweed Rules",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        val rules = listOf(
            "Qalqalah" to "Echoing sound",
            "Ghunnah" to "Nasal sound",
            "Idgham" to "Merging",
            "Ikhfa" to "Concealing",
            "Iqlab" to "Converting"
        )
        
        items(rules) { (name, description) ->
            TajweedRuleCard(name = name, description = description)
        }
    }
}

@Composable
private fun TajweedRuleCard(name: String, description: String) {
    ElevatedDeenCard(
        modifier = Modifier.fillMaxWidth()
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
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Play",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun QuranGamesView() {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Fun Games 🎮",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        val games = listOf(
            "Match the Surah" to "🎯",
            "Ayah Memory" to "🧠",
            "Word Scramble" to "🔤",
            "Quiz Challenge" to "❓"
        )
        
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = emoji,
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
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
