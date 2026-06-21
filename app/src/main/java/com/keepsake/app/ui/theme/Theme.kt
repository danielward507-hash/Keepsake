package com.keepsake.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val KeepsakeColorScheme = lightColorScheme(
    primary = Wine,
    onPrimary = Color.White,
    primaryContainer = WineSoft,
    secondary = Herb,
    secondaryContainer = HerbSoft,
    background = Parchment,
    onBackground = Ink,
    surface = Card,
    onSurface = Ink,
    surfaceVariant = Line,
    onSurfaceVariant = InkSoft
)

@Composable
fun KeepsakeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = KeepsakeColorScheme,
        typography = KeepsakeTypography,
        content = content
    )
}
