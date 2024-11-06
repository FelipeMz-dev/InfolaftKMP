package infolaft.composeApp.core.extension

import androidx.compose.ui.graphics.Color

fun Color.isDark(): Boolean {
    return (red + green + blue) / 3 < 0.8f
}