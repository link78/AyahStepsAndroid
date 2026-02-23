# GitHub Actions Workflows

This directory contains the CI/CD workflows for the DeenLearn Android app.

## Workflows Overview

### 📦 android-build.yml
**Purpose**: Complete build, test, and artifact creation

**Triggers**:
- 🔄 Push to `main` branch
- 🔄 Push to `copilot/**` branches  
- 🔄 Pull requests to `main`
- 🎯 Manual trigger (workflow_dispatch)

**Steps**:
```
1. Checkout Code
2. Setup JDK 17 (with Gradle cache)
3. Setup Android SDK
4. Validate Gradle Wrapper
5. Build Project (./gradlew build)
6. Run Tests (./gradlew test)
7. Run Lint (./gradlew lint)
8. Assemble Debug APK (./gradlew assembleDebug)
9. Upload Artifacts:
   - Debug APK (30 days)
   - Test Results (7 days)
   - Lint Results (7 days)
   - Build Reports on failure (7 days)
```

**Duration**: ~8-10 minutes (first run), ~3-5 minutes (cached)

---

### ✅ pr-checks.yml
**Purpose**: Quick validation for pull requests

**Triggers**:
- 🔄 Pull requests to `main`

**Steps**:
```
1. Checkout Code
2. Setup JDK 17 (with Gradle cache)
3. Setup Android SDK
4. Check Code Formatting (ktlint)
5. Run Lint
6. Compile Debug Variant
7. Upload Lint Results (7 days)
```

**Duration**: ~2-3 minutes

---

## Workflow Status

Check the status of workflows:
- 🟢 **Passing**: All checks successful
- 🔴 **Failing**: One or more checks failed
- 🟡 **Running**: Checks in progress

View all runs: [Actions Tab](https://github.com/link78/AyahStepsAndroid/actions)

## Artifacts

Download build outputs from completed workflow runs:

| Artifact | Description | Retention |
|----------|-------------|-----------|
| debug-apk | Installable Android APK | 30 days |
| test-results | Unit test reports (HTML) | 7 days |
| lint-results | Code quality reports | 7 days |
| build-reports | Build failure diagnostics | 7 days |

## How to Use

### Running Workflows Automatically
Workflows run automatically on:
- Every commit to `main` or `copilot/**`
- Every pull request to `main`

### Running Workflows Manually
1. Go to [Actions](https://github.com/link78/AyahStepsAndroid/actions)
2. Select "Android Build"
3. Click "Run workflow"
4. Choose branch and click "Run workflow"

### Viewing Results
1. Navigate to [Actions](https://github.com/link78/AyahStepsAndroid/actions)
2. Click on a workflow run
3. View logs by expanding each step
4. Download artifacts from the bottom of the page

## Local Testing

Run the same checks locally before pushing:

```bash
# Full build (same as CI)
./gradlew build --no-daemon

# Run tests
./gradlew test --no-daemon

# Run lint
./gradlew lint --no-daemon

# Build APK
./gradlew assembleDebug --no-daemon
```

## Troubleshooting

### Build Fails in CI but Works Locally
```bash
# Clean and rebuild (simulates CI environment)
./gradlew clean build --no-daemon
```

### Tests Pass Locally but Fail in CI
- Check for timing-dependent tests
- Look for environment-specific issues
- Review test logs in artifacts

### Timeout Issues
- Workflows have timeouts (30/20 minutes)
- Check for hanging processes
- Review dependency resolution

## Configuration

### Modifying Workflows
1. Edit YAML files in this directory
2. Validate syntax: `yamllint *.yml`
3. Test locally: `act` (requires nektos/act)
4. Commit and push changes

### Adding Secrets
For future features (signing, deployment):
1. Go to Settings > Secrets and variables > Actions
2. Add repository secrets
3. Reference in workflow: `${{ secrets.SECRET_NAME }}`

## Documentation

- **Detailed Guide**: [CI_CD_DOCUMENTATION.md](../../CI_CD_DOCUMENTATION.md)
- **Contributing**: [CONTRIBUTING.md](../../CONTRIBUTING.md)
- **Summary**: [BUILD_ACTION_SUMMARY.md](../../BUILD_ACTION_SUMMARY.md)

## Support

Issues with CI/CD? 
1. Check workflow logs
2. Download artifacts for debugging
3. Review documentation
4. Open an issue with workflow run link

---

**Last Updated**: 2026-02-12  
**Maintained by**: DeenLearn Team
