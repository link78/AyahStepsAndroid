# GitHub Actions CI/CD Documentation

This document explains the continuous integration and continuous deployment (CI/CD) setup for the DeenLearn Android app.

## Workflows

### 1. Android Build Workflow (`android-build.yml`)

**Purpose**: Complete build, test, and artifact creation for the Android app.

**Triggers**:
- Push to `main` branch
- Push to any `copilot/**` branch
- Pull requests targeting `main` branch
- Manual trigger via GitHub Actions UI

**Steps**:
1. **Checkout Code** - Fetches the latest code from the repository
2. **Setup JDK 17** - Configures Java Development Kit (Temurin distribution)
   - Includes Gradle caching for faster builds
3. **Setup Android SDK** - Installs Android SDK and build tools
4. **Validate Gradle Wrapper** - Security check to ensure wrapper hasn't been tampered with
5. **Build** - Compiles the app using `./gradlew build`
6. **Test** - Runs unit tests using `./gradlew test`
7. **Lint** - Runs code quality checks using `./gradlew lint`
8. **Assemble APK** - Creates debug APK using `./gradlew assembleDebug`
9. **Upload Artifacts** - Uploads:
   - Debug APK (30-day retention)
   - Test results (7-day retention)
   - Lint results (7-day retention)
   - Build reports on failure (7-day retention)

**Timeout**: 30 minutes

**Optimizations**:
- Uses `--no-daemon` flag to avoid Gradle daemon in CI
- Gradle caching via setup-java action
- Parallel execution where possible

### 2. PR Checks Workflow (`pr-checks.yml`)

**Purpose**: Quick validation for pull requests to provide fast feedback.

**Triggers**:
- Pull requests targeting `main` branch

**Steps**:
1. **Checkout Code**
2. **Setup JDK 17** with Gradle caching
3. **Setup Android SDK**
4. **Code Formatting Check** - Checks Kotlin formatting (if ktlint is configured)
5. **Lint** - Runs Android lint checks
6. **Compile** - Compiles debug variant to catch compilation errors
7. **Upload Lint Results**

**Timeout**: 20 minutes

**Why Separate PR Checks?**
- Faster feedback for contributors
- Focuses on code quality and compilation
- Saves CI resources by not building full APK on every PR commit
- Still runs full build workflow on merge to main

## Artifacts

### Debug APK
- **Location**: `app/build/outputs/apk/debug/*.apk`
- **Retention**: 30 days
- **Use**: Download and install on Android devices for testing

### Test Results
- **Location**: `app/build/test-results/` and `app/build/reports/tests/`
- **Retention**: 7 days
- **Use**: View test execution results and coverage

### Lint Results
- **Location**: `app/build/reports/lint-results-*.html` and `lint-results-*.xml`
- **Retention**: 7 days
- **Use**: Review code quality issues and warnings

### Build Reports (on failure)
- **Location**: `app/build/reports/` and `build/reports/`
- **Retention**: 7 days
- **Use**: Debug build failures

## Build Status Badge

The README includes a build status badge that shows the current status of the Android Build workflow:

```markdown
[![Android Build](https://github.com/link78/AyahStepsAndroid/actions/workflows/android-build.yml/badge.svg)](https://github.com/link78/AyahStepsAndroid/actions/workflows/android-build.yml)
```

## Running Workflows

### Automatic Triggers
Workflows run automatically on:
- Every push to `main` or `copilot/**` branches
- Every pull request to `main`

### Manual Trigger
To manually run the Android Build workflow:
1. Go to the [Actions tab](https://github.com/link78/AyahStepsAndroid/actions)
2. Select "Android Build" workflow
3. Click "Run workflow" button
4. Select the branch
5. Click "Run workflow"

## Viewing Results

### Workflow Status
1. Navigate to the [Actions tab](https://github.com/link78/AyahStepsAndroid/actions)
2. Click on a workflow run to see details
3. Expand individual steps to see logs

### Downloading Artifacts
1. Open a completed workflow run
2. Scroll to the "Artifacts" section at the bottom
3. Click on an artifact name to download

### Test Results
1. Open a workflow run with test results
2. Download the "test-results" artifact
3. Extract and open `index.html` in a browser

### Lint Results
1. Download the "lint-results" artifact
2. Open the HTML file in a browser to see detailed lint issues

## Local Testing

Before pushing, you can run the same checks locally:

```bash
# Build the app
./gradlew build

# Run tests
./gradlew test

# Run lint
./gradlew lint

# Build debug APK
./gradlew assembleDebug
```

## Troubleshooting

### Build Fails in CI but Works Locally

**Common causes**:
1. **Missing files in git** - Ensure all necessary files are committed
2. **Local cache** - CI starts fresh, might expose caching issues
3. **Environment differences** - CI uses specific JDK/SDK versions

**Solution**:
```bash
# Clean build locally to simulate CI
./gradlew clean build --no-daemon
```

### Timeout Issues

If builds timeout (30 minutes for android-build, 20 for pr-checks):
1. Check for infinite loops or hanging processes
2. Review dependency resolution (might be network issues)
3. Consider optimizing build scripts

### Flaky Tests

If tests fail intermittently:
1. Check for timing-dependent tests
2. Look for shared state between tests
3. Review test logs in the artifacts

### Gradle Wrapper Validation Fails

If the wrapper validation step fails:
1. The Gradle wrapper files might have been modified
2. Regenerate wrapper: `./gradlew wrapper`
3. Commit the updated wrapper files

## Security

### Secrets
Currently, the build doesn't require any secrets as it uses:
- Free public APIs (Quran, Prayer Times, Hadith)
- No code signing for debug builds

When adding release builds:
1. Add signing keystore to GitHub Secrets
2. Add keystore password and alias to secrets
3. Reference in workflow: `${{ secrets.KEYSTORE_PASSWORD }}`

### Permissions
Workflows have default permissions:
- Read repository contents
- Write workflow artifacts

## Future Enhancements

### Planned Additions
1. **Release Build Workflow**
   - Sign APK with release keystore
   - Upload to Google Play (internal testing track)
   - Generate changelog

2. **Code Coverage**
   - JaCoCo coverage reports
   - Upload to Codecov or similar service
   - Enforce minimum coverage percentage

3. **Dependency Scanning**
   - Check for vulnerable dependencies
   - Automated dependency updates (Dependabot)

4. **UI Tests**
   - Run instrumented tests on Android emulator
   - Screenshot testing
   - Accessibility checks

5. **Performance Monitoring**
   - APK size tracking
   - Build time metrics
   - Memory usage analysis

## Best Practices

### For Contributors
1. **Run tests locally** before pushing
2. **Keep builds fast** - avoid unnecessary dependencies
3. **Fix lint issues** - don't ignore warnings
4. **Write meaningful commit messages** - helps with debugging failed builds

### For Maintainers
1. **Monitor build times** - optimize if they get too long
2. **Review failed builds** promptly
3. **Keep dependencies updated** - security and compatibility
4. **Archive old artifacts** - manage storage costs

## Resources

- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Android CI Best Practices](https://developer.android.com/studio/build/building-cmdline)
- [Gradle Build Performance](https://docs.gradle.org/current/userguide/performance.html)

## Support

For issues with CI/CD:
1. Check workflow logs in the Actions tab
2. Download and review build artifacts
3. Open an issue with workflow run link
4. Include relevant error messages

---

**Last Updated**: 2026-02-12  
**Maintained by**: DeenLearn Development Team
