# DeenLearn Android Feature Screens

This document describes the 6 main feature screens of the DeenLearn Android application, located in `app/src/main/java/com/deenlearn/app/ui/screens/`.

## Overview

All screens support dual modes:
- **Kids Mode**: Simplified UI with emojis, fun language, and gamification
- **Adults Mode**: Comprehensive content with detailed information and advanced features

## Screen Descriptions

### 1. QuranScreen.kt

**Purpose**: Complete Quran learning experience with reading, memorization, and study tools.

**Features**:
- **Surah List**: Browse all Juz Amma surahs (30th Juz)
  - Arabic text with English names
  - Verse counts and meanings
  - Search functionality
  - Kids mode shows emoji for each surah
  
- **Memorization Tracking**:
  - Progress statistics (surahs memorized, reviews, streak)
  - Practice cards for each surah
  - Last practiced timestamps
  - Visual progress indicators
  
- **Bookmarks**:
  - Save favorite verses
  - Add notes
  - Quick access to marked content
  
- **Tajweed Rules** (Adults):
  - List of recitation rules
  - Audio pronunciation examples
  - Detailed explanations
  
- **Games** (Kids):
  - Match the Surah
  - Ayah Memory
  - Word Scramble
  - Quiz Challenge

**Navigation**:
```kotlin
QuranScreen(
    isKidsMode: Boolean,
    onNavigateToSurah: (Int) -> Unit
)
```

**Models Used**:
- `Surah`, `Ayah`, `QuranWord`
- `MemorizationProgress`
- `QuranBookmark`
- `QuranData` (static data)

---

### 2. PrayerScreen.kt

**Purpose**: Comprehensive prayer training covering Wudu, Salah, Duas, and common mistakes.

**Features**:
- **Wudu Steps**:
  - 10 step-by-step ablution guide
  - Arabic names and English translations
  - Visual emoji indicators (Kids mode)
  - Video demonstrations (tap to watch)
  
- **Salah Positions**:
  - 5 main prayer positions
  - Recitations for each position
  - Interactive position cards
  - Full prayer video walkthrough
  
- **Duas by Category**:
  - 7 categories (Morning, Evening, After Prayer, Protection, Travel, Healing, General)
  - Organized collections
  - Arabic text with translations
  - Audio playback
  
- **Common Mistakes** (Adults):
  - Categorized by type (Wudu, Salah, Recitation, Posture)
  - Corrections and guidance
  - Fiqh notes

**Navigation**:
```kotlin
PrayerScreen(
    isKidsMode: Boolean,
    onNavigateToDetail: (String) -> Unit
)
```

**Models Used**:
- `WuduStep`, `SalahStep`, `SalahPosition`
- `Recitation`, `DuaAfterPrayer`
- `DuaCategory`, `PrayerMistake`
- `MistakeCategory`

---

### 3. PillarsScreen.kt

**Purpose**: Interactive learning about the Five Pillars of Islam.

**Features**:
- **Pillars Overview**:
  - All 5 pillars with Arabic names
  - Visual emoji/icon for each
  - Progress tracking
  - Color-coded cards
  
- **Learn Mode**:
  - Deep dive into each pillar
  - Quranic and Hadith evidence
  - Practical applications
  - Fiqh differences across schools
  
- **Stories** (Kids):
  - Story episodes for each pillar
  - Fun narratives
  - Character-based learning
  
- **Games** (Kids):
  - Match Pillars game
  - Build a Pillar
  - Order the Pillars
  - Quiz Challenge
  
- **Quiz Mode** (Adults):
  - Multiple difficulty levels
  - Progress tracking
  - Score history
  - Retake capability

**Navigation**:
```kotlin
PillarsScreen(
    isKidsMode: Boolean,
    onNavigateToPillar: (String) -> Unit
)
```

**Models Used**:
- `Pillar`, `StoryEpisode`
- `MiniGame`, `Evidence`
- `Scenario`, `FiqhDifference`

---

### 4. ArabicScreen.kt

**Purpose**: Complete Arabic language learning from alphabet to sentences.

**Features**:
- **Alphabet**:
  - All 28 Arabic letters
  - 4 letter forms (isolated, initial, medial, final)
  - Pronunciation guides
  - Example words with icons
  - Audio for each letter
  - Progress tracking (14/28 learned)
  
- **Vocabulary by Category**:
  - 6 themed categories:
    - Salah Words (Prayer terms)
    - Masjid Objects
    - Family members
    - Animals
    - Feelings/Emotions
    - Nature
  - Icon-based learning
  - Word counts per category
  
- **Grammar** (Adults):
  - Articles (al-)
  - Pronouns
  - Verb forms
  - Sentence structure
  - Plurals
  
- **Practice Exercises** (Adults):
  - Letter recognition
  - Word building
  - Translation
  - Dictation
  - Reading passages
  - Performance tracking
  
- **Games** (Kids):
  - Match Icon to Word
  - Sound Recognition
  - Build Sentence

**Navigation**:
```kotlin
ArabicScreen(
    isKidsMode: Boolean,
    onNavigateToLesson: (String) -> Unit
)
```

**Models Used**:
- `ArabicLetter`, `VocabularyWord`
- `VocabularyCategory`
- `ArabicMiniGameType`
- `ArabicData` (static data)

---

### 5. ProfileScreen.kt

**Purpose**: User profile management, statistics, achievements, and settings.

**Features**:
- **Profile View**:
  - User avatar (emoji or image)
  - Name and email
  - Key stats (Points/Stars, Streak, Level)
  - Daily goals with progress bars
  - Weekly activity chart
  
- **Statistics**:
  - Overall numbers (total minutes, lessons, days)
  - Category breakdown (Quran, Arabic, Prayer, Pillars)
  - Time spent per category
  - Visual analytics
  
- **Achievements**:
  - Badge collection (12 of 24 unlocked)
  - Locked/unlocked states
  - Achievement descriptions
  - Earning requirements
  - Categories: Quran, Prayer, Arabic, Pillars, Streaks
  
- **Settings** (Adults):
  - Account settings (profile, email, password)
  - Preferences (language, theme, notifications)
  - Learning options (goals, reminders, display)
  - About section (version, privacy, terms)

**Navigation**:
```kotlin
ProfileScreen(
    isKidsMode: Boolean
)
```

**Models Used**:
- `UserProfile`, `ChildProfile`
- `LearningGoal`, `Achievement`
- `ProgressAnalytics`, `DailyProgress`
- `CategoryProgress`

---

### 6. HadithScreen.kt

**Purpose**: Access to hadith collections with different experiences for kids and adults.

**Features**:
- **Kids Mode - Story View**:
  - Hadith as fun stories
  - Large emojis
  - Simplified meanings
  - Fun facts
  - Interactive cards
  
- **Adults Mode - Collections**:
  - 8 major hadith collections:
    - Sahih Bukhari (7,563)
    - Sahih Muslim (7,470)
    - Sunan Abu Dawood (5,274)
    - Jami' at-Tirmidhi (3,956)
    - Sunan an-Nasa'i (5,758)
    - Sunan Ibn Majah (4,341)
    - 40 Hadith Nawawi (42)
    - Riyadh as-Salihin (1,896)
  
- **Daily Hadith**:
  - Featured hadith of the day
  - Arabic text with translation
  - Reference citation
  - Share and audio options
  - Kids version with fun facts
  
- **Categories**:
  - Browse by topic:
    - Faith & Belief
    - Prayer
    - Character
    - Knowledge
    - Family
    - Business
    - Social Relations
    - Manners
  
- **Bookmarks/Favorites**:
  - Save favorite hadith
  - Kids: Simple favorites list
  - Adults: Full bookmark management

**Navigation**:
```kotlin
HadithScreen(
    isKidsMode: Boolean,
    onNavigateToDetail: (String) -> Unit
)
```

**Models Used**:
- `KidsHadith`
- Hadith models from API services

---

## Common Patterns

### State Management
All screens use Compose state management:
```kotlin
var selectedTab by remember { mutableStateOf(0) }
var searchQuery by remember { mutableStateOf("") }
var showSearch by remember { mutableStateOf(false) }
```

### Tab Navigation
Screens with multiple views use `ScrollableTabRow`:
```kotlin
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
```

### Card Components
Consistent use of custom card components:
- `ElevatedDeenCard` - Main content cards
- `OutlinedDeenCard` - Secondary/bordered cards
- `FeatureCard` - Special feature highlights

### Kids vs Adults Mode
All screens adapt based on `isKidsMode` parameter:
- **Kids**: Emojis, simplified language, games, colorful UI
- **Adults**: Detailed info, search, filtering, advanced features

### Loading & Error States
Screens are designed for easy integration with ViewModels:
```kotlin
// Ready for:
when (uiState) {
    is Loading -> LoadingView()
    is Success -> ContentView(data = uiState.data)
    is Error -> ErrorView(message = uiState.message)
}
```

### Navigation
All screens accept navigation callbacks:
```kotlin
onNavigateToDetail: (String) -> Unit
onNavigateToSurah: (Int) -> Unit
```

## Integration Points

### Services
Screens are ready to integrate with:
- `QuranAPIService` - Quran data
- `HadithAPIService` - Hadith collections
- `IslamicAPIService` - Prayer times, qibla
- `TextToSpeechService` - Audio playback
- `QuranDataService` - Offline Quran data

### Navigation Graph
Add to `NavGraph.kt`:
```kotlin
composable(Screen.Quran.route) {
    QuranScreen(
        isKidsMode = appState.isKidsMode,
        onNavigateToSurah = { id ->
            navController.navigate(Screen.QuranReader.createRoute(id.toString()))
        }
    )
}
// ... similar for other screens
```

### ViewModel Integration
Example ViewModel structure:
```kotlin
class QuranViewModel : ViewModel() {
    private val _surahs = MutableStateFlow<List<Surah>>(emptyList())
    val surahs: StateFlow<List<Surah>> = _surahs.asStateFlow()
    
    private val _memorization = MutableStateFlow<List<MemorizationProgress>>(emptyList())
    val memorization: StateFlow<List<MemorizationProgress>> = _memorization.asStateFlow()
    
    fun loadSurahs() { /* API call */ }
    fun updateMemorization(surahId: Int) { /* Update progress */ }
}
```

## Testing

Each screen can be tested individually:

```kotlin
@Composable
@Preview(showBackground = true)
fun PreviewQuranScreenKids() {
    DeenLearnTheme {
        QuranScreen(
            isKidsMode = true,
            onNavigateToSurah = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewQuranScreenAdults() {
    DeenLearnTheme {
        QuranScreen(
            isKidsMode = false,
            onNavigateToSurah = {}
        )
    }
}
```

## Next Steps

1. **ViewModel Implementation**: Create ViewModels for each screen
2. **API Integration**: Connect screens to backend services
3. **Detail Screens**: Build detail views for each feature
4. **Offline Support**: Implement local caching
5. **Analytics**: Add event tracking
6. **Accessibility**: Enhance screen reader support
7. **Localization**: Add multi-language support
8. **Testing**: Write unit and UI tests

## File Structure

```
app/src/main/java/com/deenlearn/app/ui/screens/
├── QuranScreen.kt        (19 KB, ~600 lines)
├── PrayerScreen.kt       (16 KB, ~500 lines)
├── PillarsScreen.kt      (20 KB, ~650 lines)
├── ArabicScreen.kt       (20 KB, ~650 lines)
├── ProfileScreen.kt      (20 KB, ~650 lines)
├── HadithScreen.kt       (26 KB, ~800 lines)
├── HomeScreen.kt         (existing)
├── MainScreen.kt         (existing)
└── WelcomeScreen.kt      (existing)
```

Total: **~120 KB** of UI code, **~3,850 lines**

---

## Contact & Support

For questions or issues with these screens, please refer to:
- Main project README: `/README.md`
- Conversion guide: `/SWIFT_TO_KOTLIN_CONVERSION.md`
- UI patterns: `/UI_LAYER_README.md`
