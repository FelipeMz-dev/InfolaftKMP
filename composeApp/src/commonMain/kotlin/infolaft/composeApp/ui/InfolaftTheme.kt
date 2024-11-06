package infolaft.composeApp.ui

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun InfolaftTheme(content: @Composable () -> Unit) {

    val colors = Colors(
        primary = Color(128, 180, 198),
        primaryVariant = Color(39, 98, 185),
        secondary = Color(180, 190, 65),
        secondaryVariant = Color(174, 185, 60),
        background = Color(235, 235, 235),
        surface = Color.White,
        error = Color.Red,
        onPrimary = Color.White,
        onSecondary = Color.DarkGray,
        onBackground = Color.DarkGray,
        onSurface = Color.Black,
        onError = Color.White,
        isLight = true
    )

    MaterialTheme(colors = colors) { content() }
}