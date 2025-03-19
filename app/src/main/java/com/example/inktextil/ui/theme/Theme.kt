package com.example.inktextil.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Definimos los colores para el modo claro (Light Blue)
private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF03A9F4),  // Azul 500
    primaryContainer = Color(0xFF29B6F6),  // Azul 400
    secondary = Color(0xFF81D4FA),  // Azul 200
    background = Color(0xFFE1F5FE),  // Azul 50
    surface = Color(0xFFB3E5FC),  // Azul 100
    onPrimary = Color.White,
    onBackground = Color.Black
)

// Definimos los colores para el modo oscuro (Light Blue 800-900 con detalles en Cyan)
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF0277BD),  // Azul 800
    primaryContainer = Color(0xFF01579B),  // Azul 900
    secondary = Color(0xFF00BCD4),  // Cyan 500 (detalle)
    background = Color(0xFF01579B),  // Azul 900
    surface = Color(0xFF0277BD),  // Azul 800
    onPrimary = Color.White,
    onBackground = Color.LightGray
)

@Composable
fun InkTextilTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
