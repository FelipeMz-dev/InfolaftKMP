package infolaft.composeApp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import infolaft.composeApp.core.extension.isDark

@Composable
fun CommonCircleButton(
    modifier: Modifier = Modifier,
    text: String,
    color: Color,
    hasIcon: Boolean = true,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .size(108.dp)
            .clip(CircleShape)
            .background(color)
            .clickable { onClick() }
            .padding(
                top = 8.dp,
                bottom = 14.dp,
                start = 14.dp,
                end = 14.dp
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (hasIcon) {
            Icon(
                modifier = Modifier.graphicsLayer(rotationZ = -90f),
                imageVector = Icons.Default.PlayArrow,
                contentDescription = null,
                tint = if (color.isDark()) Color.White else Color.Black
            )
        }

        Text(
            text = text.uppercase(),
            style = MaterialTheme.typography.body2,
            color = if (color.isDark()) Color.White else Color.Black,
            maxLines = 3,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )
    }
}