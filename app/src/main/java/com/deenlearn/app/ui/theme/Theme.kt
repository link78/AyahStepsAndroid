package com.deenlearn.app.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val KidsLightColorScheme = lightColorScheme(
    primary = KidsModeColors.Primary,
    onPrimary = KidsModeColors.OnPrimary,
    primaryContainer = KidsModeColors.PrimaryContainer,
    onPrimaryContainer = KidsModeColors.OnPrimaryContainer,
    secondary = KidsModeColors.Secondary,
    onSecondary = KidsModeColors.OnSecondary,
    secondaryContainer = KidsModeColors.SecondaryContainer,
    onSecondaryContainer = KidsModeColors.OnSecondaryContainer,
    tertiary = KidsModeColors.Tertiary,
    onTertiary = KidsModeColors.OnTertiary,
    tertiaryContainer = KidsModeColors.TertiaryContainer,
    onTertiaryContainer = KidsModeColors.OnTertiaryContainer,
    error = KidsModeColors.Error,
    onError = KidsModeColors.OnError,
    errorContainer = KidsModeColors.ErrorContainer,
    onErrorContainer = KidsModeColors.OnErrorContainer,
    background = KidsModeColors.Background,
    onBackground = KidsModeColors.OnBackground,
    surface = KidsModeColors.Surface,
    onSurface = KidsModeColors.OnSurface,
    surfaceVariant = KidsModeColors.SurfaceVariant,
    onSurfaceVariant = KidsModeColors.OnSurfaceVariant,
    outline = KidsModeColors.Outline,
    outlineVariant = KidsModeColors.OutlineVariant
)

private val KidsDarkColorScheme = darkColorScheme(
    primary = KidsModeDarkColors.Primary,
    onPrimary = KidsModeDarkColors.OnPrimary,
    primaryContainer = KidsModeDarkColors.PrimaryContainer,
    onPrimaryContainer = KidsModeDarkColors.OnPrimaryContainer,
    secondary = KidsModeDarkColors.Secondary,
    onSecondary = KidsModeDarkColors.OnSecondary,
    secondaryContainer = KidsModeDarkColors.SecondaryContainer,
    onSecondaryContainer = KidsModeDarkColors.OnSecondaryContainer,
    tertiary = KidsModeDarkColors.Tertiary,
    onTertiary = KidsModeDarkColors.OnTertiary,
    tertiaryContainer = KidsModeDarkColors.TertiaryContainer,
    onTertiaryContainer = KidsModeDarkColors.OnTertiaryContainer,
    error = KidsModeDarkColors.Error,
    onError = KidsModeDarkColors.OnError,
    errorContainer = KidsModeDarkColors.ErrorContainer,
    onErrorContainer = KidsModeDarkColors.OnErrorContainer,
    background = KidsModeDarkColors.Background,
    onBackground = KidsModeDarkColors.OnBackground,
    surface = KidsModeDarkColors.Surface,
    onSurface = KidsModeDarkColors.OnSurface,
    surfaceVariant = KidsModeDarkColors.SurfaceVariant,
    onSurfaceVariant = KidsModeDarkColors.OnSurfaceVariant,
    outline = KidsModeDarkColors.Outline,
    outlineVariant = KidsModeDarkColors.OutlineVariant
)

private val AdultsLightColorScheme = lightColorScheme(
    primary = AdultsModeColors.Primary,
    onPrimary = AdultsModeColors.OnPrimary,
    primaryContainer = AdultsModeColors.PrimaryContainer,
    onPrimaryContainer = AdultsModeColors.OnPrimaryContainer,
    secondary = AdultsModeColors.Secondary,
    onSecondary = AdultsModeColors.OnSecondary,
    secondaryContainer = AdultsModeColors.SecondaryContainer,
    onSecondaryContainer = AdultsModeColors.OnSecondaryContainer,
    tertiary = AdultsModeColors.Tertiary,
    onTertiary = AdultsModeColors.OnTertiary,
    tertiaryContainer = AdultsModeColors.TertiaryContainer,
    onTertiaryContainer = AdultsModeColors.OnTertiaryContainer,
    error = AdultsModeColors.Error,
    onError = AdultsModeColors.OnError,
    errorContainer = AdultsModeColors.ErrorContainer,
    onErrorContainer = AdultsModeColors.OnErrorContainer,
    background = AdultsModeColors.Background,
    onBackground = AdultsModeColors.OnBackground,
    surface = AdultsModeColors.Surface,
    onSurface = AdultsModeColors.OnSurface,
    surfaceVariant = AdultsModeColors.SurfaceVariant,
    onSurfaceVariant = AdultsModeColors.OnSurfaceVariant,
    outline = AdultsModeColors.Outline,
    outlineVariant = AdultsModeColors.OutlineVariant
)

private val AdultsDarkColorScheme = darkColorScheme(
    primary = AdultsModeDarkColors.Primary,
    onPrimary = AdultsModeDarkColors.OnPrimary,
    primaryContainer = AdultsModeDarkColors.PrimaryContainer,
    onPrimaryContainer = AdultsModeDarkColors.OnPrimaryContainer,
    secondary = AdultsModeDarkColors.Secondary,
    onSecondary = AdultsModeDarkColors.OnSecondary,
    secondaryContainer = AdultsModeDarkColors.SecondaryContainer,
    onSecondaryContainer = AdultsModeDarkColors.OnSecondaryContainer,
    tertiary = AdultsModeDarkColors.Tertiary,
    onTertiary = AdultsModeDarkColors.OnTertiary,
    tertiaryContainer = AdultsModeDarkColors.TertiaryContainer,
    onTertiaryContainer = AdultsModeDarkColors.OnTertiaryContainer,
    error = AdultsModeDarkColors.Error,
    onError = AdultsModeDarkColors.OnError,
    errorContainer = AdultsModeDarkColors.ErrorContainer,
    onErrorContainer = AdultsModeDarkColors.OnErrorContainer,
    background = AdultsModeDarkColors.Background,
    onBackground = AdultsModeDarkColors.OnBackground,
    surface = AdultsModeDarkColors.Surface,
    onSurface = AdultsModeDarkColors.OnSurface,
    surfaceVariant = AdultsModeDarkColors.SurfaceVariant,
    onSurfaceVariant = AdultsModeDarkColors.OnSurfaceVariant,
    outline = AdultsModeDarkColors.Outline,
    outlineVariant = AdultsModeDarkColors.OutlineVariant
)

@Composable
fun DeenLearnTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    isKidsMode: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        isKidsMode && darkTheme -> KidsDarkColorScheme
        isKidsMode && !darkTheme -> KidsLightColorScheme
        !isKidsMode && darkTheme -> AdultsDarkColorScheme
        else -> AdultsLightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
