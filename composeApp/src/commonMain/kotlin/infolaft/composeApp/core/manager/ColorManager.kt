package infolaft.composeApp.core.manager

import androidx.compose.ui.graphics.Color

object ColorManager {
    fun fromHexString(hex: String): Color {
        val color = hex.removePrefix("#")
        val alpha: Int
        val red: Int
        val green: Int
        val blue: Int

        when (color.length) {
            8 -> {
                alpha = color.substring(0, 2).toInt(16)
                red = color.substring(2, 4).toInt(16)
                green = color.substring(4, 6).toInt(16)
                blue = color.substring(6, 8).toInt(16)
            }
            6 -> {
                alpha = 255
                red = color.substring(0, 2).toInt(16)
                green = color.substring(2, 4).toInt(16)
                blue = color.substring(4, 6).toInt(16)
            }
            else -> throw IllegalArgumentException("Invalid hex color string")
        }

        return Color(red, green, blue, alpha)
    }
}