package com.example.paperroll_123.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.isSystemInDarkTheme

private val LightColors = lightColorScheme(
    primary = RedPrimary,
    onPrimary = OnRedPrimary,
    primaryContainer = RedPrimaryContainer,
    onPrimaryContainer = Cream,

    secondary = DarkGray,
    onSecondary = Cream,

    background = Cream,
    onBackground = DarkGray,

    surface = Cream,
    onSurface = DarkGray,

    error = RedPrimary,
    onError = Cream
)

private val DarkColors = darkColorScheme(
    primary = RedPrimary,
    onPrimary = DarkGray,
    primaryContainer = RedPrimaryContainer,
    onPrimaryContainer = DarkGray,

    secondary = Cream,
    onSecondary = DarkGray,

    background = DarkGray,
    onBackground = Cream,

    surface = DarkGray,
    onSurface = Cream,

    error = RedPrimary,
    onError = DarkGray
)

@Composable
fun Paperroll123Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme =
        if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        } else {
            if (darkTheme) DarkColors else LightColors
        }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography, // 👈 comes from Typography.kt
        content = content
    )
}
