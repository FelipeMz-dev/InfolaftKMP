package infolaft.composeApp.ui.flow.guide.tab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import infolaft.composeApp.core.extension.isDark
import infolaft.composeApp.core.extension.shimmerEffect
import infolaft.composeApp.core.manager.ColorManager
import infolaft.composeApp.core.manager.StringManager
import infolaft.composeApp.data.AreaEntity

data class GuideTab(
    private val index: UShort,
    private val areaEntity: AreaEntity,
) : Tab {

    override val options: TabOptions
        @Composable
        get() = remember {
            TabOptions(
                index = index,
                title = areaEntity.title
            )
        }

    override val key: ScreenKey
        get() = "GuideTab-$index"

    @Composable
    override fun Content() {

        val description = remember { areaEntity.content }

        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {

            description?.forEach { content ->

                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {

                    if (content.subtitle.isNotBlank()) {
                        TitleColor(
                            modifier = Modifier.fillMaxWidth(),
                            text = content.subtitle,
                            isFirst = content == description.first(),
                            color = ColorManager.fromHexString(areaEntity.color),
                        )
                    }

                    content.files?.firstOrNull()?.file?.let {
                        if (it.isNotBlank()) {
                            ImageContent(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(14.dp)
                                    .clip(RoundedCornerShape(8.dp)),
                                imageUrl = it
                            )
                        }
                    }

                    if (content.paragraphs.isNotBlank()) {
                        Text(
                            modifier = Modifier.padding(horizontal = 14.dp),
                            text = StringManager.formatBold(content.paragraphs),
                            style = MaterialTheme.typography.body2
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun TitleColor(
        modifier: Modifier,
        text: String,
        isFirst: Boolean,
        color: Color,
    ) {
        Text(
            modifier = modifier
                .background(color = color)
                .padding(
                    vertical = 4.dp,
                    horizontal = 12.dp
                ),
            text = text,
            style = if (isFirst) MaterialTheme.typography.h4 else MaterialTheme.typography.h5,
            fontWeight = FontWeight.ExtraBold,
            color = if (color.isDark()) Color.White else Color.Black,
        )
    }

    @Composable
    private fun ImageContent(
        modifier: Modifier,
        imageUrl: String,
    ) {

        val image = rememberAsyncImagePainter(imageUrl)

        Image(
            modifier = modifier
                .fillMaxWidth()
                .padding(14.dp)
                .aspectRatio(4f / 3f)
                .clip(RoundedCornerShape(8.dp))
                .then(
                    if (image.state !is AsyncImagePainter.State.Success) Modifier.shimmerEffect()
                    else Modifier.background(color = MaterialTheme.colors.surface)
                ),
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
    }
}