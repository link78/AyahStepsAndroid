# CI Cache and Build Troubleshooting Guide

## Overview
This document provides guidance for troubleshooting CI/CD cache and build issues in the AyahStepsAndroid project.

## Recent Improvements (2026-02-13)

### Cache Configuration Updates
- **Updated cache action**: v3 → v4 for latest features and bug fixes
- **Added cache status display**: Shows whether cache was hit or missed
- **Reordered workflow steps**: Validation happens before cache for security
- **Added cache ID tracking**: Enables cache status monitoring

### Workflow Step Order
The optimized workflow order is:
1. Checkout code
2. Set up JDK 17 (with built-in Gradle cache)
3. Grant execute permission for gradlew
4. Validate Gradle wrapper (security check)
5. Cache Gradle packages (with status tracking)
6. Display cache status
7. Setup Android SDK
8. Display build environment
9. Check network connectivity
10. Download dependencies
11. Build, test, lint steps

## Common Issues and Solutions

### Issue 1: Cache Restoration Fails

**Symptoms:**
- "Display cache status" shows "Building with fresh Gradle cache"
- Build takes 8-12 minutes instead of 4-6 minutes
- No error messages, just slower build

**Causes:**
- Cache key mismatch (Gradle files changed)
- Cache expired (default 7-day retention)
- First build on a branch
- Cache storage limit reached

**Solutions:**
1. **Normal behavior**: If Gradle files changed, cache should miss
2. **Wait for cache**: Next successful build will create new cache
3. **Manual clear**: Go to Actions → Caches → Delete specific cache
4. **Check storage**: Verify cache storage quota not exceeded

### Issue 2: Gradle Wrapper Validation Fails

**Symptoms:**
- Build fails at "Validate Gradle wrapper" step
- Error: "Gradle wrapper validation failed"

**Causes:**
- Corrupted gradle-wrapper.jar
- Modified wrapper files
- Security issue with wrapper

**Solutions:**
1. **Regenerate wrapper**:
   ```bash
   ./gradlew wrapper --gradle-version 8.2
   ```
2. **Commit new wrapper files**:
   ```bash
   git add gradle/wrapper/
   git commit -m "Regenerate Gradle wrapper"
   ```
3. **Verify wrapper files**:
   ```bash
   ls -la gradle/wrapper/
   # Should show: gradle-wrapper.jar, gradle-wrapper.properties
   ```

### Issue 3: Permission Denied on gradlew

**Symptoms:**
- Error: "Permission denied: ./gradlew"
- Build fails early

**Causes:**
- gradlew file not executable
- File permissions lost during checkout

**Solutions:**
1. **Already handled**: Workflow includes `chmod +x gradlew`
2. **Commit with permissions**:
   ```bash
   git add --chmod=+x gradlew
   git commit -m "Fix gradlew permissions"
   ```
3. **Verify locally**:
   ```bash
   ls -la gradlew
   # Should show: -rwxr-xr-x
   ```

### Issue 4: Dependency Download Failures

**Symptoms:**
- Build fails at dependency resolution
- Network errors or timeouts
- "Could not resolve" errors

**Causes:**
- Maven Central or Google Maven temporary outage
- Network connectivity issues
- Firewall blocking

**Solutions:**
1. **Retry workflow**: Transient network issues often resolve
2. **Check network step**: Review "Check network connectivity" output
3. **Use dependency cache**: Ensure cache is working (see Issue 1)
4. **Verify repositories**: Check build.gradle.kts repository URLs
5. **Refresh dependencies**: Workflow includes `--refresh-dependencies`

### Issue 5: License Agreement Errors

**Symptoms:**
- Build stops at license acceptance
- SDK tools not available
- License-related error messages

**Causes:**
- Android SDK licenses not accepted
- Missing SDK components
- Restricted/evaluation licenses

**Solutions:**
1. **Already handled**: `android-actions/setup-android@v3` handles licenses
2. **Check SDK setup**: Review "Setup Android SDK" step output
3. **Verify build tools**: Ensure required SDK components available
4. **No manual licenses**: GitHub Actions runners come pre-configured

### Issue 6: Build Succeeds Locally but Fails in CI

**Symptoms:**
- `./gradlew build` works locally
- CI build fails with errors
- Environment differences

**Causes:**
- Different Java versions
- Missing environment variables
- Local cache vs CI cache
- Different Gradle versions

**Solutions:**
1. **Check Java version**:
   - CI uses: JDK 17 (Temurin)
   - Verify local: `java -version`
2. **Match Gradle version**:
   - CI uses wrapper: 8.2
   - Local: `./gradlew --version`
3. **Clean build locally**:
   ```bash
   ./gradlew clean build --no-daemon
   ```
4. **Review build environment step**: Check CI output
5. **Check --info logs**: CI runs with `--stacktrace --info`

## Cache Key Strategy

### How Cache Keys Work

**Primary Key Format:**
```
${{ runner.os }}-gradle-${{ hashFiles('**/*.gradle*', '**/gradle-wrapper.properties') }}
```

**Example:**
```
Linux-gradle-abc123def456  # Hash of all Gradle files
```

**Restore Keys (Fallback):**
```
Linux-gradle-
```

### When Cache Hits
- Exact match on primary key
- All Gradle files unchanged
- Fast build (4-6 minutes)

### When Cache Misses
- Any Gradle file changed
- Dependency versions updated
- Fresh build (8-12 minutes)
- New cache created for next run

## Monitoring Cache Performance

### Check Cache Status
Look for "Display cache status" step in workflow:
- ✓ **Cache hit**: `✓ Gradle cache restored successfully`
- ℹ **Cache miss**: `ℹ Building with fresh Gradle cache`

### Cache Metrics
Monitor these in workflow runs:
- Build time with cache: 4-6 minutes
- Build time without cache: 8-12 minutes
- Cache size: ~200-500 MB (varies)
- Cache retention: 7 days (GitHub default)

### Cache Health Indicators
**Healthy:**
- Regular cache hits on repeat builds
- Consistent build times
- No restoration errors

**Unhealthy:**
- Frequent cache misses on same commits
- Inconsistent build times
- Cache restoration errors

## Best Practices

### For Developers
1. **Don't commit build artifacts**: Use .gitignore
2. **Test before pushing**: Run `./gradlew build` locally
3. **Keep dependencies updated**: Regular dependency reviews
4. **Match CI environment**: Use JDK 17 locally
5. **Monitor CI logs**: Check for warnings

### For CI Maintenance
1. **Monitor cache hit rate**: Should be >70%
2. **Clear stale caches**: If performance degrades
3. **Update actions regularly**: Keep workflow actions current
4. **Review logs**: Check --info output for issues
5. **Test cache changes**: Verify on feature branch first

### For Cache Optimization
1. **Keep Gradle files stable**: Avoid frequent changes
2. **Use consistent versions**: Pin dependency versions
3. **Clean unnecessary files**: Remove unused dependencies
4. **Monitor cache size**: Keep under 500 MB if possible
5. **Use restore-keys wisely**: Enable partial cache hits

## Debugging Commands

### Local Testing
```bash
# Clean build like CI
./gradlew clean build --no-daemon --stacktrace --info

# Check Gradle version
./gradlew --version

# Validate wrapper
./gradlew wrapper --validate

# List dependencies
./gradlew dependencies

# Refresh dependencies
./gradlew dependencies --refresh-dependencies
```

### CI Log Analysis
```bash
# Search for errors in workflow logs
grep -i "error" workflow.log

# Find failed steps
grep -i "failed" workflow.log

# Check cache restoration
grep "cache" workflow.log

# Review dependency resolution
grep "Resolving" workflow.log
```

## GitHub Actions Cache Management

### View Caches
1. Go to repository on GitHub
2. Click "Actions" tab
3. Click "Caches" in left sidebar
4. View all active caches

### Delete Cache
1. Navigate to Actions → Caches
2. Find the cache to delete
3. Click trash icon
4. Confirm deletion
5. Next build creates fresh cache

### Cache Limits
- **Default retention**: 7 days
- **Size limit**: 10 GB per repository
- **Automatic cleanup**: Oldest unused caches deleted first

## Additional Resources

### Documentation
- [GitHub Actions Cache](https://docs.github.com/en/actions/using-workflows/caching-dependencies-to-speed-up-workflows)
- [Gradle Build Cache](https://docs.gradle.org/current/userguide/build_cache.html)
- [Android CI/CD](https://developer.android.com/studio/projects/continuous-integration)

### Related Files
- `.github/workflows/android-build.yml` - Main CI workflow
- `gradle/wrapper/gradle-wrapper.properties` - Wrapper config
- `build.gradle.kts` - Root build configuration
- `app/build.gradle.kts` - App build configuration

### Support
- Check existing issues on GitHub
- Review CI_CD_DOCUMENTATION.md for general CI info
- Review CI_BUILD_IMPROVEMENTS.md for recent changes
- Create issue for persistent problems

## Change Log

### 2026-02-13
- Updated cache action from v3 to v4
- Added cache status display step
- Reordered workflow steps for security
- Added cache ID for status tracking
- Moved wrapper validation before cache
- Created this troubleshooting guide

---

**Last Updated**: 2026-02-13  
**Maintained By**: Development Team  
**Version**: 1.0
