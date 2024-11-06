package infolaft.composeApp.core.manager

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

object StringManager {
    fun formatBold(text: String): AnnotatedString {
        val regex = Regex("""\\b(.*?)\\b""")
        val builder = AnnotatedString.Builder()

        var lastIndex = 0
        regex.findAll(text).forEach { matchResult ->
            val start = matchResult.range.first
            val end = matchResult.range.last + 1

            // Add text before the bold part
            if (start > lastIndex) {
                builder.append(text.substring(lastIndex, start))
            }

            // Add bold text
            builder.withStyle(style = SpanStyle(fontWeight = FontWeight.ExtraBold)) {
                append(matchResult.groupValues[1])
            }

            lastIndex = end
        }

        // Add remaining text after the last bold part
        if (lastIndex < text.length) {
            builder.append(text.substring(lastIndex))
        }

        return builder.toAnnotatedString()
    }
}