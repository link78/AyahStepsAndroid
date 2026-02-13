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
import com.deenlearn.app.ui.components.OutlinedDeenCard
import com.deenlearn.app.ui.components.SmallSpeakerButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HadithScreen(
    isKidsMode: Boolean,
    onNavigateToDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    var showSearch by remember { mutableStateOf(false) }
    
    val tabs = if (isKidsMode) {
        listOf("Stories 📚", "Today 🌟", "Favorites ❤️")
    } else {
        listOf("Collections", "Daily", "Categories", "Bookmarks")
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isKidsMode) "📚 Hadith" else "Hadith") },
                actions = {
                    if (!isKidsMode) {
                        IconButton(onClick = { showSearch = !showSearch }) {
                            Icon(
                                imageVector = if (showSearch) Icons.Default.Close else Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (showSearch && !isKidsMode) {
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
                0 -> if (isKidsMode) {
                    KidsHadithStoriesView(onStoryClick = onNavigateToDetail)
                } else {
                    CollectionsView(onCollectionClick = onNavigateToDetail)
                }
                1 -> DailyHadithView(isKidsMode = isKidsMode)
                2 -> if (isKidsMode) {
                    FavoritesView(isKidsMode = true)
                } else {
                    CategoriesView(onCategoryClick = onNavigateToDetail)
                }
                3 -> if (!isKidsMode) BookmarksView()
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
        placeholder = { Text("Search hadith...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null)
        },
        singleLine = true,
        shape = MaterialTheme.shapes.medium
    )
}

@Composable
private fun KidsHadithStoriesView(onStoryClick: (String) -> Unit) {
    val kidsHadiths = remember { HadithData.kidsHadithList }
    val categories = remember { HadithData.kidsHadithCategories.keys.toList() }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    
    val displayHadiths = remember(selectedCategory) {
        if (selectedCategory != null) {
            HadithData.getHadithsByCategory(selectedCategory!!)
        } else {
            kidsHadiths
        }
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
                        text = "Hadith Stories! 📚",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Learn from the teachings of Prophet Muhammad ﷺ through fun stories!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        // Category filters
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedCategory == null,
                    onClick = { selectedCategory = null },
                    label = { Text("All") }
                )
                categories.forEach { category ->
                    FilterChip(
                        selected = selectedCategory == category,
                        onClick = { selectedCategory = category },
                        label = { Text(category) }
                    )
                }
            }
        }
        
        items(displayHadiths) { hadith ->
            KidsHadithCard(hadith = hadith, onClick = { onStoryClick(hadith.id) })
        }
    }
}

@Composable
private fun KidsHadithCard(hadith: KidsHadith, onClick: () -> Unit) {
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
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = hadith.emoji,
                    style = MaterialTheme.typography.displayMedium
                )
                
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = hadith.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = hadith.simpleMeaning,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text = "Fun Fact: ${hadith.funFact}",
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(8.dp),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
            
            // Speaker buttons row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SmallSpeakerButton(
                    text = hadith.arabicText,
                    contentDescription = "Listen to Arabic",
                    isArabic = true
                )
                SmallSpeakerButton(
                    text = hadith.simpleMeaning,
                    contentDescription = "Listen to meaning"
                )
            }
        }
    }
}

@Composable
private fun CollectionsView(onCollectionClick: (String) -> Unit) {
    val collections = remember {
        listOf(
            Triple("Sahih Bukhari", "7,563 hadith", "📖"),
            Triple("Sahih Muslim", "7,470 hadith", "📗"),
            Triple("Sunan Abu Dawood", "5,274 hadith", "📘"),
            Triple("Jami' at-Tirmidhi", "3,956 hadith", "📙"),
            Triple("Sunan an-Nasa'i", "5,758 hadith", "📕"),
            Triple("Sunan Ibn Majah", "4,341 hadith", "📔"),
            Triple("40 Hadith Nawawi", "42 hadith", "⭐"),
            Triple("Riyadh as-Salihin", "1,896 hadith", "🌟")
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
                        text = "Hadith Collections",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Browse authentic collections of hadith",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        items(collections) { (name, count, icon) ->
            CollectionCard(
                name = name,
                count = count,
                icon = icon,
                onClick = { onCollectionClick(name) }
            )
        }
    }
}

@Composable
private fun CollectionCard(
    name: String,
    count: String,
    icon: String,
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
            Text(
                text = icon,
                style = MaterialTheme.typography.displaySmall
            )
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = count,
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
private fun DailyHadithView(isKidsMode: Boolean) {
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
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = if (isKidsMode) "Today's Story! 🌟" else "Hadith of the Day",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        if (!isKidsMode) {
                            IconButton(onClick = { }) {
                                Icon(
                                    imageVector = Icons.Default.Bookmark,
                                    contentDescription = "Bookmark"
                                )
                            }
                        }
                    }
                    
                    if (isKidsMode) {
                        Text(
                            text = "😊",
                            style = MaterialTheme.typography.displayLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = if (isKidsMode) "Arabic:" else "Arabic Text:",
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Text(
                                text = "تَبَسُّمُكَ فِي وَجْهِ أَخِيكَ صَدَقَةٌ",
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (isKidsMode) "What it means:" else "Translation:",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (isKidsMode)
                                "Smiling at someone is charity! The Prophet ﷺ loved to smile and make people happy."
                            else
                                "Your smile in the face of your brother is charity.",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    
                    if (!isKidsMode) {
                        Divider()
                        
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Jami' at-Tirmidhi 1956",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                IconButton(onClick = { }) {
                                    Icon(Icons.Default.Share, "Share")
                                }
                                IconButton(onClick = { }) {
                                    Icon(Icons.Default.VolumeUp, "Listen")
                                }
                            }
                        }
                    }
                }
            }
        }
        
        if (isKidsMode) {
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
                            text = "Fun Fact! 💡",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "The Prophet ﷺ smiled so much that his friends said his face was like the full moon!",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoriesView(onCategoryClick: (String) -> Unit) {
    val categories = remember {
        listOf(
            Triple("Faith & Belief", "Iman", "☝️"),
            Triple("Prayer", "Salah", "🤲"),
            Triple("Character", "Akhlaq", "💎"),
            Triple("Knowledge", "Ilm", "📚"),
            Triple("Family", "Usrah", "👨‍👩‍👧‍👦"),
            Triple("Business", "Tijara", "💼"),
            Triple("Social Relations", "Muamalat", "🤝"),
            Triple("Manners", "Adab", "🌟")
        )
    }
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(
                text = "Browse by Category",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        items(categories) { (name, arabic, icon) ->
            CategoryCard(
                name = name,
                arabic = arabic,
                icon = icon,
                onClick = { onCategoryClick(name) }
            )
        }
    }
}

@Composable
private fun CategoryCard(
    name: String,
    arabic: String,
    icon: String,
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
            Text(
                text = icon,
                style = MaterialTheme.typography.displaySmall
            )
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = arabic,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
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
private fun FavoritesView(isKidsMode: Boolean) {
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
                        text = if (isKidsMode) "My Favorites ❤️" else "Bookmarked Hadith",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = if (isKidsMode)
                            "Your favorite stories are saved here!"
                        else
                            "Access your saved hadith anytime",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
        
        items(3) { index ->
            FavoriteHadithCard(
                title = listOf("The Smiling Prophet", "Being Kind", "Thank You Allah")[index],
                collection = listOf("Tirmidhi", "Bukhari", "Muslim")[index],
                isKidsMode = isKidsMode
            )
        }
    }
}

@Composable
private fun FavoriteHadithCard(
    title: String,
    collection: String,
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
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = collection,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorite",
                tint = MaterialTheme.colorScheme.error
            )
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
                text = "Bookmarked Hadith",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        
        items(5) { index ->
            BookmarkCard(
                hadithNumber = listOf(1, 255, 45, 78, 120)[index],
                collection = listOf("Bukhari", "Muslim", "Tirmidhi", "Abu Dawood", "Nawawi")[index],
                topic = listOf("Prayer", "Faith", "Character", "Charity", "Knowledge")[index]
            )
        }
    }
}

@Composable
private fun BookmarkCard(
    hadithNumber: Int,
    collection: String,
    topic: String
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
                    text = "$collection $hadithNumber",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = topic,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            
            IconButton(onClick = { }) {
                Icon(
                    imageVector = Icons.Default.BookmarkRemove,
                    contentDescription = "Remove bookmark",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
