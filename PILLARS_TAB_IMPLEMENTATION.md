# PILLARS TAB - Implementation Guide

## Overview

This document provides a comprehensive guide for implementing the enhanced PILLARS TAB features for both Kids and Adult modes as specified in the requirements.

---

## Table of Contents

1. [Requirements Summary](#requirements-summary)
2. [Current Implementation Status](#current-implementation-status)
3. [Data Models (Complete)](#data-models-complete)
4. [UI Implementation Needed](#ui-implementation-needed)
5. [Implementation Phases](#implementation-phases)
6. [Technical Specifications](#technical-specifications)
7. [Content Inventory](#content-inventory)
8. [Success Metrics](#success-metrics)

---

## Requirements Summary

### Purpose
Teach the 5 pillars of Islam through engaging content for both children and adults.

### Top-Level Structure
The PILLARS TAB covers:
1. **Shahada** (Declaration of Faith) ☝️
2. **Salah** (Prayer) 🤲
3. **Zakat** (Charity) 💝
4. **Sawm** (Fasting in Ramadan) 🌙
5. **Hajj** (Pilgrimage) 🕋

### Kids Mode Requirements

#### World Map UI
- Each pillar displayed as a themed "world"
- Visual map showing progression
- Animations and character mascots
- Unlock progression system

#### Story Episodes
- Short narrated stories
- Character-based storytelling
- Duration indicators
- Audio narration support

#### Mini-Games
- **Match pillar → meaning**: Drag-and-drop game
- **Fix the broken pillar**: Puzzle game
- Interactive and educational
- Star rewards on completion

#### Rewards System
- ⭐ Stars earned for activities
- 🏆 Badges and achievements
- 🦜 Character upgrades
- Progress tracking

### Adult Mode Requirements

#### Lesson Cards
Structured content with:
- **Definition**: Clear explanation
- **Qur'an Evidence**: Verses with Arabic and translation
- **Hadith Evidence**: Authentic narrations
- **Wisdom**: Spiritual and practical benefits
- **Practical Application**: Real-life guidance

#### Scenario-Based Learning
- Real-world questions
- Scholarly answers
- Examples:
  - "What if I miss a prayer?"
  - "How to calculate Zakat?"
  - "Can I give Zakat to family?"

---

## Current Implementation Status

### ✅ Foundation Complete (100%)

All data models and content are ready:

#### 1. Pillar Model (`Pillar.kt`)
```kotlin
@Serializable
data class Pillar(
    val id: String,
    val number: Int,
    val name: String,
    val nameArabic: String,
    val icon: String,
    val colorHex: String,
    val worldEmoji: String,
    val description: String,
    val storyEpisodes: List<StoryEpisode>,
    val miniGames: List<MiniGame>,
    val definition: String,
    val quranEvidence: List<Evidence>,
    val hadithEvidence: List<Evidence>,
    val wisdom: String,
    val practicalApplication: String,
    val scenarios: List<Scenario>,
    val fiqhDifferences: List<FiqhDifference>,
    val kidsHadiths: List<KidsHadith>
)
```

**Content Available**:
- 10+ story episodes
- 4 mini-game types per pillar
- Complete scholarly content for adults
- 15 real-world scenarios
- Quran and Hadith evidences

#### 2. WorldTheme Model (`WorldTheme.kt`)
5 themed worlds defined:

| World | Pillar | Mascot | Emoji | Theme |
|-------|--------|--------|-------|-------|
| Shahada Island | 1 | 🦜 Parrot | 🏝️ | Faith blooms |
| Salah City | 2 | 🕊️ Dove | 🌆 | Peaceful prayer |
| Zakat Village | 3 | 🐑 Sheep | 🏘️ | Caring & sharing |
| Ramadan Mountain | 4 | 🦅 Eagle | ⛰️ | Climb patience |
| Hajj Desert Journey | 5 | 🐪 Camel | 🏜️ | Sacred pilgrimage |

#### 3. RewardSystem Model (`RewardSystem.kt`)
Complete gamification system:
- Stars: 3 per story, 5 per game
- 5 Achievements defined
- 5 Character upgrades
- Level progression formula
- Daily streak tracking

#### 4. PillarsScreen (`PillarsScreen.kt`)
Basic structure exists:
- Tab-based navigation
- Kids tabs: Learn 🕌, Stories 📖, Games 🎮
- Adult tabs: Overview, Learn, Quiz
- Pillar list display
- Click navigation

### 🔄 UI Enhancement Needed

The following UI components need to be implemented or enhanced:

#### Kids Mode
- [ ] World Map visual display
- [ ] Enhanced story reading interface
- [ ] Mini-game mechanics
- [ ] Rewards display integration

#### Adult Mode
- [ ] Structured lesson card layout
- [ ] Interactive scenario cards
- [ ] Expandable evidence sections

---

## Data Models (Complete)

### Story Episode Structure
```kotlin
@Serializable
data class StoryEpisode(
    val id: String,
    val title: String,
    val narrator: String,
    val content: String,
    val emoji: String,
    val duration: Int // in seconds
)
```

**Example Story**: "The Words That Changed the World"
- Narrator: Teacher Ali
- Content: Story of Bilal and the Shahada
- Duration: 180 seconds
- Emoji: ☝️

### Mini-Game Structure
```kotlin
@Serializable
data class MiniGame(
    val id: String,
    val title: String,
    val type: MiniGameType,
    val description: String,
    val icon: String
)

@Serializable
enum class MiniGameType {
    MATCH_PILLAR_MEANING,
    FIX_BROKEN_PILLAR,
    ORDER_THE_PILLARS,
    QUIZ_TIME
}
```

### Evidence Structure
```kotlin
@Serializable
data class Evidence(
    val id: String,
    val arabic: String,
    val translation: String,
    val reference: String
)
```

**Example**:
- Arabic: "إِنَّمَا الْأَعْمَالُ بِالنِّيَّاتِ"
- Translation: "Actions are judged by intentions"
- Reference: "Sahih Bukhari 1"

### Scenario Structure
```kotlin
@Serializable
data class Scenario(
    val id: String,
    val question: String,
    val answer: String,
    val category: String
)
```

**Example Scenarios**:
1. "I overslept and missed Fajr. What should I do?"
2. "How do I calculate Zakat on my savings?"
3. "Can I give Zakat to my parents?"

---

## UI Implementation Needed

### Phase 1: Kids Mode World Map UI (High Priority)

#### WorldMapView Component
**Purpose**: Visual map showing 5 pillar worlds

**Features**:
- Grid or path layout
- World cards with:
  - Mascot character
  - World name and emoji
  - Progress percentage
  - Lock/unlock indicator
- Click to enter world
- Visual journey path

**Implementation**:
```kotlin
@Composable
fun WorldMapView(
    worlds: List<WorldTheme>,
    userProgress: UserProgress,
    onWorldClick: (String) -> Unit
)
```

**Design Elements**:
- Use `backgroundGradient` from WorldTheme
- Display mascot character prominently
- Show completion: "3/5 stars collected"
- Lock icon for locked worlds
- Glowing effect for current world

### Phase 2: Enhanced Story Display (High Priority)

#### StoryDetailView Component
**Purpose**: Engaging story reading interface

**Features**:
- Story title with emoji
- Narrator information
- Duration display
- Story content with good typography
- "Tap to listen" audio button
- Next/Previous navigation
- Completion checkmark

**Implementation**:
```kotlin
@Composable
fun StoryDetailView(
    story: StoryEpisode,
    onComplete: () -> Unit,
    onAudioPlay: () -> Unit
)
```

**Design Elements**:
- Large emoji at top
- Readable font size for kids
- Paragraph breaks
- Audio speaker button
- Progress indicator

### Phase 3: Mini-Games (Medium Priority)

#### MatchPillarGame Component
**Purpose**: Drag-and-drop matching game

**Features**:
- 5 pillar names on left
- 5 meanings on right
- Drag from left to right
- Check answers
- Award 5 stars on completion

**Implementation**:
```kotlin
@Composable
fun MatchPillarGame(
    pillars: List<PillarInfo>,
    onComplete: (stars: Int) -> Unit
)
```

#### FixBrokenPillarGame Component
**Purpose**: Puzzle game to "fix" a pillar

**Features**:
- Pillar with missing pieces
- Drag pieces to correct positions
- Multiple difficulty levels
- Visual feedback on correct placement

**Implementation**:
```kotlin
@Composable
fun FixBrokenPillarGame(
    pillarId: String,
    difficulty: Int,
    onComplete: (stars: Int) -> Unit
)
```

### Phase 4: Rewards Display (Medium Priority)

#### RewardsPanel Component
**Purpose**: Show earned rewards

**Features**:
- Total stars count
- Earned badges/achievements
- Character collection
- Level progress bar
- Recent rewards

**Implementation**:
```kotlin
@Composable
fun RewardsPanel(
    userProgress: UserProgress,
    achievements: List<Achievement>
)
```

**Display**:
- ⭐ Stars: 45/100
- 🏆 Achievements: 2/5 unlocked
- 🦜 Characters: 3/5 collected
- 📊 Level: 5 (Progress to Level 6)

### Phase 5: Adult Lesson Cards (High Priority)

#### LessonCardView Component
**Purpose**: Structured lesson display

**Features**:
- Tabbed or expandable sections
- Definition with clear typography
- Quran evidence with Arabic
- Hadith evidence with sources
- Wisdom section
- Practical application

**Implementation**:
```kotlin
@Composable
fun LessonCardView(
    pillar: Pillar,
    isExpanded: Boolean,
    onToggle: () -> Unit
)
```

**Section Layout**:

```
┌─────────────────────────────────┐
│ Definition                      │
│ Clear explanation...            │
├─────────────────────────────────┤
│ Qur'an Evidence (3)            │
│ ┌───────────────────────────┐  │
│ │ Arabic text               │  │
│ │ Translation               │  │
│ │ Reference: Quran 2:21     │  │
│ └───────────────────────────┘  │
├─────────────────────────────────┤
│ Hadith Evidence (3)            │
│ Similar format...               │
├─────────────────────────────────┤
│ Wisdom & Benefits              │
│ Spiritual and practical...      │
├─────────────────────────────────┤
│ Practical Application          │
│ Real-life guidance...           │
└─────────────────────────────────┘
```

### Phase 6: Scenario Cards (Medium Priority)

#### ScenarioCardView Component
**Purpose**: Interactive case-based learning

**Features**:
- Question in highlighted box
- Category badge
- "Think about it" prompt
- Expandable answer
- Scholarly explanation

**Implementation**:
```kotlin
@Composable
fun ScenarioCardView(
    scenario: Scenario,
    isAnswerVisible: Boolean,
    onToggleAnswer: () -> Unit
)
```

**Layout**:
```
┌─────────────────────────────────┐
│ 📚 Salah Category              │
├─────────────────────────────────┤
│ ❓ What if I miss Fajr?        │
│                                 │
│ [Think About It] [Show Answer] │
│                                 │
│ ✅ Answer:                     │
│ Make it up as soon as...        │
│                                 │
│ 📖 Evidence: Hadith reference   │
└─────────────────────────────────┘
```

---

## Implementation Phases

### Phase 1: World Map UI (Week 1)
**Priority**: High  
**Effort**: 2-3 days

**Deliverables**:
- WorldMapView composable
- World card components
- Progress indicators
- Navigation integration

**Data**: WorldTheme models (ready)

### Phase 2: Enhanced Story Display (Week 1)
**Priority**: High  
**Effort**: 2-3 days

**Deliverables**:
- StoryDetailView composable
- Story list view
- Audio integration
- Completion tracking

**Data**: StoryEpisode in PillarData (ready)

### Phase 3: Adult Lesson Cards (Week 1-2)
**Priority**: High  
**Effort**: 3-4 days

**Deliverables**:
- LessonCardView composable
- Section tabs/expansion
- Evidence display
- Scroll and navigation

**Data**: Pillar model content (ready)

### Phase 4: Scenario Cards (Week 2)
**Priority**: Medium  
**Effort**: 2 days

**Deliverables**:
- ScenarioCardView composable
- Answer reveal animation
- Category filtering
- List view

**Data**: Scenario in Pillar model (ready)

### Phase 5: Rewards Display (Week 2)
**Priority**: Medium  
**Effort**: 2-3 days

**Deliverables**:
- RewardsPanel composable
- Achievement display
- Character showcase
- Progress bars

**Data**: UserProgress, RewardSystem (ready)

### Phase 6: Mini-Games (Week 3-4)
**Priority**: Lower  
**Effort**: 5-7 days

**Deliverables**:
- MatchPillarGame
- FixBrokenPillarGame
- Game selection UI
- Score tracking

**Data**: MiniGame definitions (ready)

---

## Technical Specifications

### File Structure
```
ui/screens/
├── PillarsScreen.kt (main screen - enhance)
└── components/
    ├── WorldMapView.kt (NEW)
    ├── StoryDetailView.kt (NEW)
    ├── LessonCardView.kt (NEW)
    ├── ScenarioCardView.kt (NEW)
    ├── RewardsPanel.kt (NEW)
    └── games/
        ├── MatchPillarGame.kt (NEW)
        └── FixBrokenPillarGame.kt (NEW)
```

### Dependencies
No new dependencies needed. Current stack:
- Jetpack Compose
- Material3
- Kotlin Coroutines
- Kotlin Serialization

### State Management
```kotlin
// In ViewModel or Screen
var selectedWorld by remember { mutableStateOf<WorldTheme?>(null) }
var userProgress by remember { mutableStateOf(UserProgress()) }
var selectedStory by remember { mutableStateOf<StoryEpisode?>(null) }
var showAnswer by remember { mutableStateOf(false) }
```

### Navigation
```kotlin
// From WorldMap to Pillar Detail
onWorldClick = { worldId ->
    val pillarId = worlds.find { it.id == worldId }?.pillarId
    onNavigateToPillar(pillarId)
}

// From Story List to Story Detail
onStoryClick = { story ->
    selectedStory = story
    // Show full story view
}
```

---

## Content Inventory

### Kids Mode Content (Ready)

#### 5 Themed Worlds ✅
1. 🏝️ Shahada Island - Parrot mascot
2. 🌆 Salah City - Dove mascot
3. 🏘️ Zakat Village - Sheep mascot
4. ⛰️ Ramadan Mountain - Eagle mascot
5. 🏜️ Hajj Desert Journey - Camel mascot

#### 10+ Story Episodes ✅
- Shahada: 2 stories
- Salah: 2 stories
- Zakat: 2 stories
- Sawm: 2 stories
- Hajj: 2 stories

Example titles:
- "The Words That Changed the World"
- "Bilal's Beautiful Call"
- "The Well of Giving"

#### Mini-Games (4 types per pillar) ✅
- Match Pillar → Meaning
- Fix the Broken Pillar
- Order the Pillars
- Quiz Time

#### Rewards ✅
- 5 Achievements
- 5 Character Upgrades
- Star system (3, 5, 2 per activity)
- Level progression

### Adult Mode Content (Ready)

#### 5 Complete Pillars ✅
Each pillar includes:
- Scholarly definition
- 3 Quran verses (Arabic + English)
- 3 Authentic hadiths
- Wisdom and benefits
- Practical application guide
- 3 real scenarios
- Fiqh differences across madhahib

#### 15 Scenarios ✅
Examples:
1. "I overslept and missed Fajr prayer"
2. "How to calculate Zakat on savings"
3. "Can I give Zakat to my parents?"
4. "What if I break my fast accidentally?"
5. "Is Hajj required if I have debt?"
...and 10 more

#### Evidence Collection ✅
- 15 Quran verses total
- 15 Authentic hadiths total
- All with proper references
- Arabic text included

---

## Success Metrics

### Kids Mode Metrics
- 🎮 **Engagement**: World completion rate
- 📖 **Story Reading**: Stories completed per user
- ⭐ **Stars Earned**: Average stars per session
- 🏆 **Achievements**: Unlock rate
- 🦜 **Characters**: Collection completion %

**Target Goals**:
- 80% complete at least 1 world
- Average 3+ stories read per user
- 50+ stars earned on average
- 2+ achievements unlocked
- 3/5 characters collected

### Adult Mode Metrics
- 📚 **Lesson Engagement**: Time spent per pillar
- 💡 **Evidence Study**: Evidence section views
- 🎯 **Scenario Completion**: Scenarios read
- 📝 **Application**: Practical tips viewed
- 🔄 **Return Rate**: Repeat visits

**Target Goals**:
- 5+ minutes per pillar lesson
- 90% view at least 1 evidence
- 5+ scenarios explored
- 70% view practical application
- 60% return within 7 days

---

## Implementation Checklist

### Phase 1: World Map UI
- [ ] Create WorldMapView composable
- [ ] Design world card layout
- [ ] Add progress indicators
- [ ] Implement lock/unlock logic
- [ ] Add mascot displays
- [ ] Integrate with navigation
- [ ] Test on different screen sizes

### Phase 2: Enhanced Stories
- [ ] Create StoryDetailView composable
- [ ] Add narrator information
- [ ] Format story content
- [ ] Integrate audio button
- [ ] Add pagination
- [ ] Track completion
- [ ] Test story flow

### Phase 3: Adult Lessons
- [ ] Create LessonCardView composable
- [ ] Design section layout
- [ ] Add Definition display
- [ ] Add Quran evidence section
- [ ] Add Hadith evidence section
- [ ] Add Wisdom section
- [ ] Add Practical application
- [ ] Test expandable sections

### Phase 4: Scenario Cards
- [ ] Create ScenarioCardView composable
- [ ] Design question display
- [ ] Add answer reveal
- [ ] Add category badges
- [ ] Implement filtering
- [ ] Test user interaction

### Phase 5: Rewards
- [ ] Create RewardsPanel composable
- [ ] Display stars count
- [ ] Show achievements
- [ ] Display characters
- [ ] Add level progress
- [ ] Test reward awarding

### Phase 6: Mini-Games
- [ ] Create game selection UI
- [ ] Implement MatchPillarGame
- [ ] Implement FixBrokenPillarGame
- [ ] Add game instructions
- [ ] Award stars on completion
- [ ] Track game progress

---

## Conclusion

**Current Status**: Foundation 100% Complete ✅

All data models, content, and basic navigation are ready. The implementation can proceed with UI components that bring these features to life.

**Next Actions**:
1. Start with World Map UI (high visual impact)
2. Implement Adult Lesson Cards (high value)
3. Enhance Story Display (engagement)
4. Add Scenario Cards (learning)
5. Integrate Rewards (gamification)
6. Build Mini-Games (interaction)

**Estimated Timeline**: 3-4 weeks for complete implementation

**Resources Needed**:
- UI/UX Designer (for visual design)
- Android Developer (for implementation)
- Content Reviewer (for accuracy)
- QA Tester (for validation)

---

**Document Version**: 1.0  
**Last Updated**: 2026-02-16  
**Status**: Foundation Complete, UI Implementation Pending
