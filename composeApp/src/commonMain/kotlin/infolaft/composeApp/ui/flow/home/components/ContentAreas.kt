package infolaft.composeApp.ui.flow.home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import infolaft.composeApp.core.manager.ColorManager.fromHexString
import infolaft.composeApp.data.AreaEntity

@Composable
fun ContentAreas(
    modifier: Modifier,
    areas: List<AreaEntity>,
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier.width(IntrinsicSize.Max)
        ) {
            areas.forEach {
                Text(
                    modifier = Modifier,
                    text = it.title,
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        ColumnColoredLines(
            modifier = Modifier
                .wrapContentSize()
                .fillMaxHeight(),
            areas = areas
        )
    }
}

@Composable
private fun ColumnColoredLines(
    modifier: Modifier,
    areas: List<AreaEntity>,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        areas.forEach {
            ColoredRectangleLine(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .height(IntrinsicSize.Min)
                    .padding(vertical = 8.dp),
                quantity = 7,
                spaceBy = 4.dp,
                color = fromHexString(it.color)
            )
        }
    }
}

@Composable
private fun ColoredRectangleLine(
    modifier: Modifier,
    quantity: Int,
    spaceBy: Dp,
    color: Color,
) {
    Canvas(modifier = modifier) {
        val space = spaceBy.toPx()
        val rectangleWidth = (size.width - (space * (quantity - 1))) / quantity
        val rectangleHeight = size.height

        for (i in 0 until quantity) {
            drawRect(
                color = color,
                topLeft = Offset(
                    x = (rectangleWidth + space) * i,
                    y = 0f
                ),
                size = Size(
                    width = rectangleWidth,
                    height = rectangleHeight
                )
            )
        }
    }
}