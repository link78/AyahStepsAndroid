# Contributing to DeenLearn Android

Thank you for your interest in contributing to DeenLearn! This document provides guidelines and instructions for contributing to the project.

## Table of Contents
- [Getting Started](#getting-started)
- [Development Workflow](#development-workflow)
- [Code Style](#code-style)
- [Testing](#testing)
- [Submitting Changes](#submitting-changes)
- [CI/CD](#cicd)

## Getting Started

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or later
- JDK 17 or later
- Android SDK 34
- Git

### Setting Up Your Development Environment

1. **Fork the repository**
   ```bash
   # Click "Fork" on GitHub, then clone your fork
   git clone https://github.com/YOUR_USERNAME/AyahStepsAndroid.git
   cd AyahStepsAndroid
   ```

2. **Add upstream remote**
   ```bash
   git remote add upstream https://github.com/link78/AyahStepsAndroid.git
   ```

3. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory
   - Wait for Gradle sync to complete

4. **Build the project**
   ```bash
   ./gradlew build
   ```

## Development Workflow

### Creating a Branch

Always create a new branch for your work:

```bash
# Update your main branch
git checkout main
git pull upstream main

# Create a feature branch
git checkout -b feature/your-feature-name
```

Branch naming conventions:
- `feature/` - New features
- `fix/` - Bug fixes
- `docs/` - Documentation updates
- `refactor/` - Code refactoring
- `test/` - Adding or updating tests

### Making Changes

1. **Keep changes focused** - One feature or fix per PR
2. **Follow the existing code style** - Use Android Studio's auto-formatting
3. **Write descriptive commit messages**
4. **Add tests** for new features
5. **Update documentation** if needed

### Running Checks Locally

Before submitting, run these commands:

```bash
# Build the project
./gradlew build

# Run tests
./gradlew test

# Run lint checks
./gradlew lint

# Build debug APK
./gradlew assembleDebug
```

## Code Style

### Kotlin Style Guide

Follow the [Official Kotlin Coding Conventions](https://kotlinlang.org/docs/coding-conventions.html):

- Use `camelCase` for function and variable names
- Use `PascalCase` for class names
- Use 4 spaces for indentation (not tabs)
- Maximum line length: 120 characters
- Use meaningful variable names

### Jetpack Compose Guidelines

- Composable functions should use `PascalCase`
- Use `remember` and `mutableStateOf` for local state
- Hoist state when needed
- Use `LaunchedEffect` for side effects
- Prefer `Column`/`Row` over custom layouts when possible

### Example

```kotlin
@Composable
fun UserProfileCard(
    user: User,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = user.name,
                style = MaterialTheme.typography.headlineMedium
            )
            Text(
                text = user.email,
                style = MaterialTheme.typography.bodyMedium
            )
            Button(onClick = onEditClick) {
                Text("Edit Profile")
            }
        }
    }
}
```

## Testing

### Unit Tests

Write unit tests for:
- ViewModels
- Repository classes
- Utility functions
- Business logic

Tests should be in `app/src/test/java/`:

```kotlin
class AppViewModelTest {
    @Test
    fun `updateStreak should increment streak for consecutive days`() {
        // Given
        val viewModel = AppViewModel(context)
        
        // When
        viewModel.updateStreak()
        
        // Then
        assertEquals(1, viewModel.appState.value.currentStreak)
    }
}
```

### UI Tests

Write UI tests for:
- Screen navigation
- User interactions
- UI state changes

Tests should be in `app/src/androidTest/java/`:

```kotlin
@Test
fun welcomeScreen_kidsMode_navigatesToHome() {
    composeTestRule.setContent {
        WelcomeScreen(onModeSelected = { /* ... */ })
    }
    
    composeTestRule
        .onNodeWithText("Kids Mode")
        .performClick()
    
    // Assert navigation occurred
}
```

## Submitting Changes

### Creating a Pull Request

1. **Push your branch**
   ```bash
   git push origin feature/your-feature-name
   ```

2. **Open a Pull Request on GitHub**
   - Go to your fork on GitHub
   - Click "New Pull Request"
   - Select your feature branch
   - Fill out the PR template

### PR Description Template

```markdown
## Description
Brief description of what this PR does

## Changes
- Change 1
- Change 2
- Change 3

## Testing
How did you test these changes?

## Screenshots (if applicable)
Add screenshots for UI changes

## Checklist
- [ ] Code follows the project's style guidelines
- [ ] I have performed a self-review of my code
- [ ] I have commented my code where necessary
- [ ] I have made corresponding changes to the documentation
- [ ] My changes generate no new warnings
- [ ] I have added tests that prove my fix is effective or that my feature works
- [ ] New and existing unit tests pass locally with my changes
```

### Review Process

1. **Automated Checks** - CI runs automatically
   - Build must pass
   - Tests must pass
   - Lint checks should have no errors

2. **Code Review** - Maintainers will review your PR
   - May request changes
   - May suggest improvements
   - Discuss technical decisions

3. **Merge** - Once approved:
   - Squash and merge (default)
   - Delete branch after merge

## CI/CD

### Automated Workflows

Two workflows run on every PR:

1. **Android Build** (`android-build.yml`)
   - Full build
   - Unit tests
   - Lint checks
   - Creates debug APK

2. **PR Checks** (`pr-checks.yml`)
   - Quick validation
   - Compilation check
   - Lint check

See [CI_CD_DOCUMENTATION.md](./CI_CD_DOCUMENTATION.md) for details.

### Build Status

Check the build status in your PR:
- Green checkmark ✓ - All checks passed
- Red X ✗ - Some checks failed
- Yellow circle ○ - Checks are running

Click "Details" to see logs if checks fail.

## Common Issues

### Gradle Sync Failed

```bash
# Clean and rebuild
./gradlew clean
./gradlew build --refresh-dependencies
```

### Build Failed in CI but Works Locally

```bash
# Simulate CI environment
./gradlew clean build --no-daemon
```

### Lint Errors

```bash
# See lint report
./gradlew lint
# Open app/build/reports/lint-results-debug.html
```

## Getting Help

- **Documentation**: Check the README and other docs
- **Issues**: Search existing issues on GitHub
- **Discussions**: Start a discussion for questions
- **Code Review**: Ask questions in your PR

## Code of Conduct

- Be respectful and inclusive
- Provide constructive feedback
- Focus on the code, not the person
- Help others learn and grow

## Recognition

Contributors will be:
- Listed in the project's contributors
- Mentioned in release notes for significant contributions
- Part of the community shaping DeenLearn

Thank you for contributing to DeenLearn! 🌟

---

**Questions?** Open an issue or start a discussion on GitHub.
