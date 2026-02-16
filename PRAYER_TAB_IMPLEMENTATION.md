# PRAYER TAB Implementation Guide

## Overview

This document provides comprehensive specifications for implementing the enhanced PRAYER TAB features including Salah & Wudu Trainer, Prayer Times display, and Qibla Compass.

## Table of Contents

1. [Requirements Summary](#requirements-summary)
2. [Current Implementation Status](#current-implementation-status)
3. [Data Models](#data-models)
4. [UI Implementation Plan](#ui-implementation-plan)
5. [Technical Specifications](#technical-specifications)
6. [Content Inventory](#content-inventory)
7. [Success Metrics](#success-metrics)
8. [Implementation Checklist](#implementation-checklist)

---

## Requirements Summary

### Top-Level Structure
- **Learn Wudu**: Step-by-step ablution guide
- **Learn Salah**: Prayer positions and recitations
- **Practice Mode**: Interactive guided practice
- **Mistakes & Corrections**: Common errors to avoid
- **Duas After Prayer**: Post-prayer supplications

### Kids Mode Features
- Cartoon character performing steps
- "Copy Me!" interactive mode
- Stickers for completing prayers
- Fun emojis and animations
- Audio guidance

### Adult Mode Features
- Detailed animations/descriptions
- Audio recitation (slow + normal speed)
- Arabic/transliteration/translation toggles
- Fiqh notes and madhab differences
- Common mistakes explanations

### Prayer Times & Qibla
- **Prayer times based on location**
- **Qibla compass**
- Display prayer times on home page
- Qibla Compass button on home page
- Next prayer countdown
- Hijri date display

---

## Current Implementation Status

### ✅ Foundation Complete

#### PrayerTimeService (Fully Implemented)
**File**: `services/PrayerTimeService.kt`

**Features**:
- Location-based prayer time calculations
- Multiple calculation methods (MWL, ISNA, Egyptian, etc.)
- Asr juristic methods (Shafi'i, Hanafi)
- Real-time next prayer tracking
- Countdown timer
- Qibla direction calculation
- Hijri date conversion
- API + local calculation fallback

**Flow States**:
```kotlin
val prayerTimes: StateFlow<List<PrayerTime>>
val nextPrayer: StateFlow<PrayerTime?>
val timeUntilNextPrayer: StateFlow<String>
val currentPrayerIndex: StateFlow<Int>
val qiblaDirection: StateFlow<Double?>
val hijriDate: StateFlow<String>
```

#### Prayer Models (Fully Implemented)
**File**: `models/Prayer.kt`

**Data Structures**:
- `WuduStep`: Ablution steps with Arabic, emoji, mistakes
- `SalahStep`: Prayer positions with recitations
- `SalahPosition`: Enum of positions
- `Recitation`: Arabic + transliteration + translation + audio
- `DuaCategory`: 7 categories (After Prayer, Morning, Evening, etc.)
- `MistakeCategory`: Common error categories

#### PrayerScreen (Basic Implementation)
**File**: `screens/PrayerScreen.kt`

**Current Features**:
- Tab-based navigation (Wudu, Salah, Duas, Mistakes)
- Wudu steps display (10 steps)
- Salah positions display (5 positions)
- Duas by category
- Mistakes view structure
- Kids/Adult mode switching

### 🔄 Implementation Needed (UI Enhancements)

| Feature | Status | Priority |
|---------|--------|----------|
| Prayer Times on Home Page | ❌ Not implemented | High |
| Qibla Compass UI | ❌ Not implemented | High |
| Arabic/Transliteration/Translation Toggles | ❌ Not implemented | High |
| Audio Recitation Player | ❌ Not implemented | High |
| Practice Mode | ❌ Not implemented | Medium |
| "Copy Me!" Interactive Mode | ❌ Not implemented | Medium |
| Sticker Rewards | ❌ Not implemented | Medium |
| Speed Control (slow/normal) | ❌ Not implemented | Medium |
| Detailed Animations | ❌ Not implemented | Low |
| Video Demonstrations | ❌ Not implemented | Low |

---

## Data Models

### Existing Models (Ready to Use)

#### 1. PrayerTime
```kotlin
data class PrayerTime(
    val name: String,          // "Fajr", "Dhuhr", etc.
    val arabicName: String,    // "الفجر", "الظهر", etc.
    val time: Date,
    val icon: String,          // Icon identifier
    val isPrayer: Boolean,     // True for prayers, false for Shuruq
    val timezone: TimeZone
) {
    fun formattedTime(): String  // Returns "9:30 AM"
}
```

#### 2. WuduStep
```kotlin
data class WuduStep(
    val id: String,
    val stepNumber: Int,
    val name: String,
    val nameArabic: String,
    val description: String,
    val kidsDescription: String,
    val kidsEmoji: String,
    val duration: Int,             // Seconds
    val repetitions: Int,          // How many times (e.g., 3 for hands)
    val isSunnah: Boolean,
    val commonMistakes: List<String>,
    val fiqhNotes: String?
)
```

#### 3. SalahStep
```kotlin
data class SalahStep(
    val id: String,
    val stepNumber: Int,
    val name: String,
    val nameArabic: String,
    val description: String,
    val kidsDescription: String,
    val kidsEmoji: String,
    val position: SalahPosition,
    val recitation: Recitation?,
    val duration: Int,
    val repetitions: Int,
    val commonMistakes: List<String>,
    val fiqhNotes: String?
)
```

#### 4. Recitation
```kotlin
data class Recitation(
    val id: String,
    val arabic: String,
    val transliteration: String,
    val translation: String,
    val audioFileName: String?
)
```

### New Models Needed

#### 5. PracticeSession
```kotlin
data class PracticeSession(
    val type: PracticeType,     // WUDU or SALAH
    val currentStep: Int,
    val totalSteps: Int,
    val startTime: Date,
    val completedSteps: List<Int>,
    val earnedStickers: List<String>
)

enum class PracticeType {
    WUDU, SALAH
}
```

#### 6. ContentDisplayMode
```kotlin
enum class ContentDisplayMode {
    ARABIC_ONLY,
    TRANSLITERATION_ONLY,
    TRANSLATION_ONLY,
    ARABIC_AND_TRANSLITERATION,
    ARABIC_AND_TRANSLATION,
    ALL_THREE
}
```

#### 7. AudioSpeed
```kotlin
enum class AudioSpeed(val displayName: String, val multiplier: Float) {
    SLOW("Slow", 0.75f),
    NORMAL("Normal", 1.0f),
    FAST("Fast", 1.25f)
}
```

---

## UI Implementation Plan

### Phase 1: Prayer Times on Home Page (High Priority)

#### Component: PrayerTimesCard

**Location**: Add to `HomeScreen.kt`

**Design**:
```
┌─────────────────────────────────────┐
│  🕌 Prayer Times                    │
│  📍 New York, USA                   │
│  📅 15 Rajab 1448                   │
├─────────────────────────────────────┤
│  🌅 Fajr        5:30 AM             │
│  ☀️ Sunrise     7:02 AM             │
│  ☀️ Dhuhr      12:45 PM  [NEXT]    │
│  🌤️ Asr         4:15 PM             │
│  🌆 Maghrib     7:28 PM             │
│  🌙 Isha        8:50 PM             │
├─────────────────────────────────────┤
│  Next: Dhuhr in 2:15:43            │
└─────────────────────────────────────┘
```

**Features**:
- Real-time countdown to next prayer
- Highlight next prayer
- Show Hijri date
- Location display
- Tap to open settings
- Swipe to refresh

**Implementation**:
```kotlin
@Composable
fun PrayerTimesCard(
    prayerTimes: List<PrayerTime>,
    nextPrayer: PrayerTime?,
    countdown: String,
    hijriDate: String,
    location: String,
    onSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
)
```

**Integration**:
1. Add to HomeScreen after greeting
2. Observe PrayerTimeService flows
3. Request location permission if needed
4. Show loading state initially

### Phase 2: Qibla Compass (High Priority)

#### Component: QiblaCompassScreen

**Design**:
```
┌─────────────────────────────────────┐
│           ← Back   Qibla            │
├─────────────────────────────────────┤
│                                      │
│            🧭                        │
│          Compass                     │
│         Rotating                     │
│          Based on                    │
│       Device Sensors                 │
│                                      │
│       Direction: 45° NE             │
│                                      │
│       📍 New York, USA               │
│       Distance to Makkah: 11,000km  │
│                                      │
└─────────────────────────────────────┘
```

**Features**:
- Real compass with needle
- Device orientation tracking
- Qibla direction in degrees
- Distance to Makkah
- Calibration instructions
- Works without internet (cached direction)

**Implementation**:
```kotlin
@Composable
fun QiblaCompassScreen(
    qiblaDirection: Double?,
    deviceOrientation: Float,
    location: String,
    distanceToMakkah: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
)
```

**Required**:
- Sensor service for device orientation
- Rotation animations
- SVG/Canvas drawing for compass
- Calibration UI

#### Component: QiblaCompassButton

**Add to HomeScreen**:
```kotlin
Button(
    onClick = { navigateToQiblaCompass() },
    modifier = Modifier.fillMaxWidth()
) {
    Icon(Icons.Default.Explore, "Compass")
    Spacer(Modifier.width(8.dp))
    Text("Find Qibla Direction 🧭")
}
```

### Phase 3: Content Display Toggles (High Priority)

#### Component: ContentToggleBar

**Design**:
```
┌─────────────────────────────────────┐
│  [Arabic] [Transliteration] [Trans.] │
└─────────────────────────────────────┘
```

**Implementation**:
```kotlin
@Composable
fun ContentToggleBar(
    showArabic: Boolean,
    showTransliteration: Boolean,
    showTranslation: Boolean,
    onToggleArabic: (Boolean) -> Unit,
    onToggleTransliteration: (Boolean) -> Unit,
    onToggleTranslation: (Boolean) -> Unit,
    modifier: Modifier = Modifier
)
```

**Usage**:
- Add above Wudu/Salah content
- Save preferences
- Filter content display
- Animate transitions

#### Enhanced Step Display

**Wudu/Salah with Toggles**:
```kotlin
if (showArabic) {
    Text(
        text = step.nameArabic,
        style = MaterialTheme.typography.headlineMedium
    )
}

if (showTransliteration) {
    Text(
        text = step.transliteration,
        style = MaterialTheme.typography.titleMedium
    )
}

if (showTranslation) {
    Text(
        text = step.translation,
        style = MaterialTheme.typography.bodyLarge
    )
}
```

### Phase 4: Audio Recitation Player (High Priority)

#### Component: RecitationPlayer

**Design**:
```
┌─────────────────────────────────────┐
│  🔊 Bismillah...                    │
│  ▶️ [====------] 0:03 / 0:10       │
│  [Slow] [Normal] [Fast]             │
└─────────────────────────────────────┘
```

**Implementation**:
```kotlin
@Composable
fun RecitationPlayer(
    recitation: Recitation,
    audioSpeed: AudioSpeed,
    onSpeedChange: (AudioSpeed) -> Unit,
    modifier: Modifier = Modifier
)
```

**Features**:
- Play/pause controls
- Progress bar
- Speed selection (slow/normal/fast)
- Seek functionality
- Auto-play next (optional)

**Technical**:
- Use ExoPlayer for audio
- Apply speed multiplier
- Cache audio files
- Preload next audio

### Phase 5: Practice Mode (Medium Priority)

#### Component: PracticeModeView

**Design**:
```
┌─────────────────────────────────────┐
│  Practice: Wudu    Step 3/10        │
├─────────────────────────────────────┤
│                                      │
│     💧 Rinse Mouth                  │
│     المضمضة                         │
│                                      │
│  Rinse your mouth 3 times           │
│                                      │
│  ⏱️ Timer: 15 seconds               │
│  [===========>----------]           │
│                                      │
│  [Skip]  [I'm Done!]  [Next]        │
└─────────────────────────────────────┘
```

**Features**:
- Step-by-step guidance
- Timer for each step
- Audio instructions
- Progress tracking
- Completion celebration
- Earn rewards

**Implementation**:
```kotlin
@Composable
fun PracticeModeView(
    session: PracticeSession,
    isKidsMode: Boolean,
    onStepComplete: () -> Unit,
    onSkip: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
)
```

**State Management**:
```kotlin
class PracticeModeViewModel : ViewModel() {
    val session = MutableStateFlow<PracticeSession?>(null)
    val currentStep = MutableStateFlow(0)
    val timer = MutableStateFlow(0)
    
    fun startPractice(type: PracticeType)
    fun nextStep()
    fun skipStep()
    fun completeSession()
}
```

### Phase 6: Kids Interactive Features (Medium Priority)

#### Component: CopyMeMode

**Design** (Kids Mode):
```
┌─────────────────────────────────────┐
│  Copy Me! 🎮                        │
├─────────────────────────────────────┤
│                                      │
│      [Animated Character]           │
│      Performing Wudu Step           │
│                                      │
│  "Wash your hands like this!"       │
│  🔊 [Playing audio...]              │
│                                      │
│  ⭐⭐⭐ 3 Stars Earned!               │
│                                      │
│  [Try This Step!]                   │
└─────────────────────────────────────┘
```

**Features**:
- Animated character guide
- Auto-play audio
- Simple controls
- Immediate feedback
- Star rewards
- Celebration animations

#### Component: StickerRewardDialog

**Design**:
```
┌─────────────────────────────────────┐
│    🎉 Amazing Job! 🎉               │
├─────────────────────────────────────┤
│                                      │
│       [Sticker Image]               │
│     "Wudu Champion"                 │
│                                      │
│  You completed all Wudu steps!     │
│                                      │
│  Rewards Earned:                    │
│  ⭐ 5 Stars                         │
│  🏆 Wudu Champion Badge            │
│                                      │
│  [Awesome!]                         │
└─────────────────────────────────────┘
```

**Integration**:
- Show after practice completion
- Save to user progress
- Unlock achievements
- Share option (future)

---

## Technical Specifications

### Dependencies

#### Existing (Already in project)
```kotlin
// ExoPlayer for audio
implementation("androidx.media3:media3-exoplayer:1.2.0")
implementation("androidx.media3:media3-ui:1.2.0")

// Location services
implementation("com.google.android.gms:play-services-location:21.0.1")
```

#### New Required
```kotlin
// Sensors for compass
// (Built-in Android SDK, no new dependency)

// Optional: Lottie for animations
implementation("com.airbnb.android:lottie-compose:6.0.0")
```

### Permissions

**Already in AndroidManifest.xml**:
```xml
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.INTERNET" />
```

**May Need to Add**:
```xml
<uses-permission android:name="android.permission.ACCESS_BACKGROUND_LOCATION" />
```

### File Structure

```
app/src/main/java/com/deenlearn/app/
├── models/
│   └── Prayer.kt ✅ (exists, complete)
├── services/
│   ├── PrayerTimeService.kt ✅ (exists, complete)
│   ├── LocationService.kt ✅ (exists)
│   └── SensorService.kt 🔄 (create for compass)
├── ui/
│   ├── screens/
│   │   ├── PrayerScreen.kt ✅ (exists, enhance)
│   │   ├── HomeScreen.kt ✅ (exists, add prayer times)
│   │   └── QiblaCompassScreen.kt 🔄 (create new)
│   └── components/
│       ├── PrayerTimesCard.kt 🔄 (create new)
│       ├── QiblaCompass.kt 🔄 (create new)
│       ├── ContentToggleBar.kt 🔄 (create new)
│       ├── RecitationPlayer.kt 🔄 (create new)
│       ├── PracticeModeView.kt 🔄 (create new)
│       └── StickerRewardDialog.kt 🔄 (create new)
└── viewmodels/
    └── PracticeModeViewModel.kt 🔄 (create new)
```

---

## Content Inventory

### Prayer Times (Ready) ✅
- Fajr (Dawn)
- Sunrise (Shuruq)
- Dhuhr (Noon)
- Asr (Afternoon)
- Maghrib (Sunset)
- Isha (Night)

### Wudu Steps (10 Steps) ✅
1. Intention (Niyyah)
2. Say Bismillah
3. Wash Hands (3x)
4. Rinse Mouth (3x)
5. Rinse Nose (3x)
6. Wash Face (3x)
7. Wash Arms to Elbows (3x each)
8. Wipe Head (1x)
9. Wipe Ears (1x)
10. Wash Feet to Ankles (3x each)

### Salah Positions (5 Main) ✅
1. Standing (Qiyam)
2. Bowing (Ruku)
3. Prostration (Sujud)
4. Sitting (Jalsa)
5. Tashahhud

### Dua Categories (7 Categories) ✅
1. After Prayer
2. Morning Adhkar
3. Evening Adhkar
4. Protection
5. Travel
6. Healing
7. General

### Mistake Categories ✅
1. Wudu Mistakes
2. Salah Mistakes
3. Recitation Mistakes
4. Timing Mistakes

---

## Success Metrics

### Technical Metrics
- Prayer times accuracy: ±1 minute
- Qibla direction accuracy: ±5 degrees
- Audio playback: Smooth, no lag
- UI responsiveness: <100ms interactions
- Location fetch: <3 seconds

### User Engagement Metrics

#### Kids Mode
- 70% complete at least 1 practice session
- 50% earn a sticker
- 3+ practice sessions per week
- 80% use audio guidance

#### Adult Mode
- 80% view prayer times daily
- 60% use Qibla compass
- 40% toggle content display
- 70% use audio recitation
- 50% complete practice mode

### Learning Outcomes
- Users learn correct Wudu steps
- Users learn correct Salah positions
- Users understand common mistakes
- Users memorize key duas
- Users pray on time (with reminders)

---

## Implementation Checklist

### Phase 1: Prayer Times on Home Page ✅

**Week 1, Days 1-2**
- [ ] Create PrayerTimesCard component
- [ ] Add to HomeScreen
- [ ] Integrate with PrayerTimeService
- [ ] Handle location permissions
- [ ] Display next prayer countdown
- [ ] Show Hijri date
- [ ] Add refresh functionality
- [ ] Test on different screen sizes

### Phase 2: Qibla Compass ✅

**Week 1, Days 3-4**
- [ ] Create SensorService for orientation
- [ ] Create QiblaCompassScreen
- [ ] Implement compass needle rotation
- [ ] Show direction in degrees
- [ ] Calculate distance to Makkah
- [ ] Add calibration instructions
- [ ] Add Qibla button to HomeScreen
- [ ] Test sensor accuracy

### Phase 3: Content Display Toggles ✅

**Week 1, Day 5**
- [ ] Create ContentToggleBar component
- [ ] Add toggle state management
- [ ] Integrate with Wudu/Salah views
- [ ] Save user preferences
- [ ] Animate content transitions
- [ ] Test all toggle combinations

### Phase 4: Audio Recitation Player ✅

**Week 2, Days 1-2**
- [ ] Create RecitationPlayer component
- [ ] Implement ExoPlayer integration
- [ ] Add speed controls (slow/normal/fast)
- [ ] Add play/pause/seek
- [ ] Preload audio files
- [ ] Cache downloaded audio
- [ ] Test playback quality

### Phase 5: Practice Mode ✅

**Week 2, Days 3-4**
- [ ] Create PracticeSession model
- [ ] Create PracticeModeViewModel
- [ ] Create PracticeModeView component
- [ ] Implement step-by-step guidance
- [ ] Add timer functionality
- [ ] Track completion
- [ ] Award stars/badges
- [ ] Test full practice flow

### Phase 6: Kids Interactive Features ✅

**Week 2, Day 5**
- [ ] Create CopyMeMode component
- [ ] Add character animations
- [ ] Implement auto-play audio
- [ ] Create StickerRewardDialog
- [ ] Integrate with RewardSystem
- [ ] Add celebration animations
- [ ] Test kids user flow

### Phase 7: Adult Enhancements 🔄

**Week 3, Days 1-2**
- [ ] Add detailed step descriptions
- [ ] Add fiqh notes expansion
- [ ] Add madhab differences
- [ ] Enhance mistake explanations
- [ ] Add references/sources
- [ ] Test adult learning flow

### Phase 8: Polish & Testing 🔄

**Week 3, Days 3-5**
- [ ] Fix bugs
- [ ] Optimize performance
- [ ] Add loading states
- [ ] Add error handling
- [ ] Improve accessibility
- [ ] Test on multiple devices
- [ ] Gather user feedback
- [ ] Final QA

---

## Conclusion

The PRAYER TAB implementation requires building on the existing strong foundation (PrayerTimeService, Prayer models) by adding comprehensive UI components. The phased approach ensures:

1. **Immediate Value**: Prayer times and Qibla compass (Week 1)
2. **Enhanced Learning**: Content toggles and audio (Week 1-2)
3. **Interactive Practice**: Practice mode and kids features (Week 2)
4. **Comprehensive Experience**: Adult enhancements and polish (Week 3)

**Timeline**: 3 weeks for complete implementation
**Effort**: ~1,500 lines of new UI code
**Dependencies**: Minimal (mostly use existing)
**Risk**: Low (foundation already proven)

The result will be a world-class prayer learning and practice platform suitable for both children and adults.

---

**Alhamdulillah!** 🤲

*May this implementation help Muslims establish their prayers correctly and on time.*
