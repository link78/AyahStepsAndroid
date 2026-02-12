# DeenLearn Android

DeenLearn is a comprehensive Islamic learning app that makes learning simple, visual, joyful, and accessible for every age—from 6-year-olds learning wudu (ablution) to adults memorizing the Qur'an.

## Overview

This is the Android version of DeenLearn, converted from the iOS app. It features dual learning modes (Kids & Adults) with age-appropriate content, gamification, and progressive learning paths.

## Features

### Core Features
- **Dual Learning Modes**: Kids Mode (colorful, gamified) & Adults Mode (professional, detailed)
- **Learning Modules**: Wudu, Salah, Qur'an, Islamic Manners, Pillars of Islam
- **Progress Tracking**: Points system, streaks, stars, badges, lesson completion
- **Qur'an Section**: Short surahs with Arabic text, transliteration, English translation
- **Prayer Training**: Step-by-step Salah & Wudu guides
- **Arabic Learning**: 28 Arabic letters with phonetics and vocabulary
- **Hadith Content**: Age-appropriate hadith with stories and lessons

### Advanced Features
- **AI-Powered Learning**: AI Tutor, AI Question Generator, AI Quran Coach
- **Gamification**: Streaks, points, badges, mini-games for engagement
- **Prayer Times**: Location-based prayer time calculations and notifications
- **Multi-Profile Support**: Parent controls, age-based content filtering
- **Dark Mode**: Full dark mode support
- **Text-to-Speech**: Audio support for recitations

## Technology Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material 3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Manual DI (can be upgraded to Hilt)
- **Networking**: Retrofit + OkHttp
- **Data Persistence**: SharedPreferences + kotlinx.serialization
- **Async**: Kotlin Coroutines + Flow
- **Location**: Google Play Services Location
- **Audio**: Android TextToSpeech + MediaPlayer

## Project Structure

```
app/
├── src/main/java/com/deenlearn/app/
│   ├── models/              # Data models (10 files)
│   │   ├── AppState.kt
│   │   ├── Arabic.kt
│   │   ├── Profile.kt
│   │   ├── Quran.kt
│   │   ├── Prayer.kt
│   │   ├── Pillar.kt
│   │   ├── LearningModule.kt
│   │   └── KidsHadith.kt
│   ├── services/            # API and platform services (13 files)
│   │   ├── QuranAPIService.kt
│   │   ├── PrayerTimeService.kt
│   │   ├── LocationService.kt
│   │   ├── TextToSpeechService.kt
│   │   └── AI*.kt (4 AI services)
│   ├── data/                # Data layer
│   │   └── PreferencesManager.kt
│   ├── ui/                  # UI layer
│   │   ├── theme/          # Material 3 theme
│   │   ├── screens/        # Feature screens (9 files)
│   │   ├── components/     # Reusable components
│   │   ├── navigation/     # Navigation graph
│   │   └── viewmodels/     # ViewModels
│   ├── DeenLearnApplication.kt
│   └── MainActivity.kt
└── src/main/res/           # Android resources
```

## Building the Project

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 17 or later
- Android SDK 34
- Minimum Android API 26 (Android 8.0)

### Build Instructions

1. Clone the repository:
```bash
git clone https://github.com/link78/AyahStepsAndroid.git
cd AyahStepsAndroid
```

2. Open the project in Android Studio

3. Sync Gradle files:
```bash
./gradlew build
```

4. Run the app:
```bash
./gradlew installDebug
```

Or use Android Studio's Run button.

## API Integration

The app integrates with several free Islamic APIs:

- **Quran API**: https://api.quran.gading.dev (SutanLab)
- **Prayer Times API**: https://api.aladhan.com/v1 (Aladhan)
- **Hadith API**: https://api.hadith.gading.dev (SutanLab)

No API keys are required for basic functionality.

## Development Status

### ✅ Completed
- [x] Project setup and Gradle configuration
- [x] All 10 data models converted from Swift to Kotlin
- [x] All 13 services converted and integrated
- [x] Data layer with PreferencesManager
- [x] Complete UI theme with Material 3
- [x] Navigation graph with 9 screens
- [x] All feature screens (Quran, Prayer, Pillars, Arabic, Profile, Hadith)
- [x] Reusable UI components

### 🚧 In Progress
- [ ] ViewModel integration with screens
- [ ] API service integration and data fetching
- [ ] Prayer time notifications
- [ ] AI features integration
- [ ] Testing and QA

### 📋 Planned
- [ ] Unit tests
- [ ] UI tests
- [ ] Performance optimization
- [ ] Accessibility improvements
- [ ] Multi-language support (Arabic, Urdu, etc.)

## Contributing

Contributions are welcome! Please follow these guidelines:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## Code Style

This project follows the official Kotlin coding conventions:
- Use camelCase for property names
- Use PascalCase for class names
- Use Jetpack Compose best practices
- Follow Material 3 design guidelines

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- Original iOS app: [AyahSteps](https://github.com/link78/AyahSteps)
- Quran API by [SutanLab](https://github.com/sutanlab/quran-api)
- Prayer Times API by [Aladhan](https://aladhan.com/prayer-times-api)
- Islamic content from authentic sources

## Support

For support, email support@deenlearn.com or open an issue on GitHub.

---

**Made with ❤️ for the Muslim community**