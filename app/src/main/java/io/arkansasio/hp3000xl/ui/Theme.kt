package io.arkansasio.hp3000xl.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Theme for the HP 3000 XL Color Mobile Edition.
 *
 * "classic-blue" palette: navy title bars, silver window chrome and a
 * mid-day desktop blue, matching the Windows CE handheld look of the
 * original device. A dark variant is provided for the Control Panel
 * "Use Dark Theme" toggle.
 */

// Classic blue palette (light)
val HpNavy = Color(0xFF123B63)
val HpNavyDark = Color(0xFF0A223A)
val HpSilver = Color(0xFFC7CCD2)
val HpWindow = Color(0xFFF4F6FA)
val HpDesktopBlue = Color(0xFF5B8FD9)
val HpChrome = Color(0xFFE7EAF0)
val HpBorder = Color(0xFF6E7785)
val HpAccentGreen = Color(0xFF357A50)
val HpBootGreen = Color(0xFF9FE870)
val HpBootAmber = Color(0xFFFFC857)

// Dark variant
val HpDarkSurface = Color(0xFF141C28)
val HpDarkChrome = Color(0xFF1D2633)
val HpDarkWindow = Color(0xFF10161F)
val HpDarkText = Color(0xFFD8DEE9)

private val LightScheme = lightColorScheme(
    primary = HpNavy,
    onPrimary = Color.White,
    primaryContainer = HpSilver,
    onPrimaryContainer = HpNavyDark,
    secondary = HpNavyDark,
    onSecondary = Color.White,
    surface = HpWindow,
    onSurface = Color(0xFF10202C),
    surfaceVariant = HpChrome,
    onSurfaceVariant = HpNavyDark,
    background = HpDesktopBlue,
    onBackground = Color.White,
    error = Color(0xFFB3261E),
    outline = HpBorder,
)

private val DarkScheme = darkColorScheme(
    primary = Color(0xFF7FA7D9),
    onPrimary = HpDarkSurface,
    primaryContainer = HpNavy,
    onPrimaryContainer = HpSilver,
    secondary = HpBootAmber,
    onSecondary = HpDarkSurface,
    surface = HpDarkSurface,
    onSurface = HpDarkText,
    surfaceVariant = HpDarkChrome,
    onSurfaceVariant = HpDarkText,
    background = HpDarkChrome,
    onBackground = HpDarkText,
    error = Color(0xFFF2B8B5),
    outline = Color(0xFF4A5566),
)

/** Classic-blue Material 3 theme for the HP 3000 XL shell. */
@Composable
fun Hp3000Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkScheme else LightScheme,
        content = content,
    )
}
