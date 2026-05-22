package com.example.comparapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = ComparBlue,
    onPrimary = SurfaceColor,
    primaryContainer = ComparBlueLight,
    onPrimaryContainer = ComparBlueDark,
    background = BackgroundColor,
    surface = SurfaceColor,
    onBackground = TextLabel,
    onSurface = TextLabel,
    error = ErrorColor
)

@Composable
fun ComparAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
