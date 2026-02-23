# DeenLearn Android UI Layer

This document provides an overview of the Jetpack Compose UI layer created for the DeenLearn Android application.

## Directory Structure

```
app/src/main/java/com/deenlearn/app/
├── MainActivity.kt                 # App entry point
└── ui/
    ├── theme/
    │   ├── Color.kt               # Material 3 color schemes (Kids & Adults modes)
    │   ├── Typography.kt          # Text styles and Arabic typography
    │   └── Theme.kt               # Main theme composable with dark mode
    ├── navigation/
    │   ├── Screen.kt              # Sealed class for navigation routes
    │   └── NavGraph.kt            # Navigation graph implementation
    ├── screens/
    │   ├── WelcomeScreen.kt       # Mode selection screen (Kids/Adults)
    │   ├── MainScreen.kt          # Tab navigation container
    │   └── HomeScreen.kt          # Personalized dashboard
    └── components/
        ├── TopBar.kt              # Reusable app bar
        ├── BottomNavBar.kt        # Bottom navigation bar
        ├── Card.kt                # Custom card components
        └── Button.kt              # Custom button styles
```

## Features

### Theme System

**Dual Mode Color Schemes:**
- **Kids Mode**: Bright, playful colors (purple, pink, orange)
- **Adults Mode**: Professional, elegant colors (green, teal, blue)
- Both modes support dark theme variants

**Typography:**
- Material 3 typography scale
- Special Arabic text styles for Quranic content
- Responsive font sizes

### Navigation

**Main Routes:**
- Welcome Screen: Mode selection
- Main Screen: Tab-based navigation
- 7 Tab Destinations:
  - Home: Dashboard with progress tracking
  - Pillars: Five Pillars of Islam
  - Quran: Quran reader and study
  - Prayer: Prayer times and guidance
  - Arabic: Arabic language learning
  - Profile: User profile and settings
  - Hadith: Hadith collection

**Detail Routes:**
- Pillar Detail
- Quran Reader
- Prayer Detail
- Arabic Lesson
- Hadith Detail

### Screens

#### WelcomeScreen
- Animated entrance
- Beautiful mode selection cards
- Clear descriptions for each mode

#### MainScreen
- Bottom navigation with 7 tabs
- Dynamic top bar with screen titles
- Settings and menu actions

#### HomeScreen
- Personalized greeting
- Daily progress card with visual indicators
- Quick action shortcuts
- Today's lesson highlight
- Recent activity feed
- Different content for Kids vs Adults mode

### Reusable Components

#### TopBar
- Material 3 app bar
- Back navigation support
- Action buttons
- Customizable colors

#### BottomNavBar
- Material 3 navigation bar
- Icon-based navigation
- Selected state indicators
- Mode-aware colors

#### Card Components
- `DeenCard`: Base card with variants
- `ElevatedDeenCard`: Card with elevation
- `OutlinedDeenCard`: Card with border
- `FeatureCard`: Pre-styled card for features

#### Button Components
- `DeenFilledButton`: Primary actions
- `DeenOutlinedButton`: Secondary actions
- `DeenTextButton`: Tertiary actions
- `DeenElevatedButton`: Alternative primary
- `DeenFilledTonalButton`: Subtle emphasis

## Design Principles

1. **Material 3 Design**: Following latest Material Design guidelines
2. **Responsive Layout**: Adapts to different screen sizes
3. **State Hoisting**: Proper state management in composables
4. **Accessibility**: Semantic content descriptions
5. **Dark Mode Support**: All colors and components support dark theme
6. **Performance**: Efficient recomposition and lazy loading

## Customization

### Adding New Colors
Edit `ui/theme/Color.kt` and add colors to the appropriate mode:
```kotlin
val NewColor = Color(0xFFHEXVAL)
```

### Adding New Routes
1. Add route to `Screen` sealed class in `ui/navigation/Screen.kt`
2. Add composable to NavGraph in `ui/navigation/NavGraph.kt`

### Creating New Screens
Place screen composables in `ui/screens/` directory and follow the pattern:
```kotlin
@Composable
fun YourScreen(
    isKidsMode: Boolean,
    onNavigateToDetail: (String) -> Unit
) {
    // Screen implementation
}
```

## Next Steps

The following screens need full implementation:
- [ ] Pillars detailed content
- [ ] Quran reader with audio
- [ ] Prayer times with location
- [ ] Arabic lessons
- [ ] Profile management
- [ ] Hadith reader
- [ ] Settings screen
- [ ] Onboarding flow

## Dependencies

All required dependencies are already included in `app/build.gradle.kts`:
- Compose BOM 2023.10.01
- Material 3
- Navigation Compose
- Accompanist (System UI Controller)

## Usage

The app starts with `MainActivity` which sets up the `DeenLearnTheme` and `NavGraph`. The navigation begins at the `WelcomeScreen` where users select their learning mode (Kids or Adults), then proceeds to the `MainScreen` with tab navigation.

```kotlin
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeenLearnApp()
        }
    }
}
```
