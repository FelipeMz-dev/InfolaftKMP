package infolaft.composeApp.ui.flow.guide.tab

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import infolaft.composeApp.data.AreaEntity
import infolaft.composeApp.ui.flow.home.components.ContentAreas
import infolaftappkmp.composeapp.generated.resources.Res
import infolaftappkmp.composeapp.generated.resources.copy_dont_touch
import infolaftappkmp.composeapp.generated.resources.copy_here_is_you_map
import infolaftappkmp.composeapp.generated.resources.copy_how_travel_answer
import infolaftappkmp.composeapp.generated.resources.copy_how_travel_ask
import infolaftappkmp.composeapp.generated.resources.copy_is_agree_photos
import infolaftappkmp.composeapp.generated.resources.copy_map_expocapacitacion
import infolaftappkmp.composeapp.generated.resources.copy_practical_guide
import infolaftappkmp.composeapp.generated.resources.copy_recommendations
import infolaftappkmp.composeapp.generated.resources.img_museum_map
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource


class MainGuideTab(private val areas: List<AreaEntity>) : Tab {

    override val options: TabOptions
        @Composable
        get() = remember {
            TabOptions(
                index = 0u,
                title = "Main Guide"
            )
        }

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.background)
                .padding(
                    top = 24.dp,
                    start = 24.dp,
                    end = 24.dp,
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TitledParagraphContent(Modifier.fillMaxWidth())

            ContentAreas(
                modifier = Modifier.fillMaxWidth(),
                areas = areas
            )

            MapContent(Modifier.fillMaxWidth())

            RecommendationsContent(Modifier.fillMaxWidth())
        }
    }

    @Composable
    private fun TitledParagraphContent(
        modifier: Modifier,
    ) {
        Column(modifier) {

            CommonTitle(
                modifier = Modifier.padding(vertical = 10.dp),
                text = stringResource(Res.string.copy_practical_guide),
                color = MaterialTheme.colors.secondary
            )

            CommonTitle(
                modifier = Modifier.padding(bottom = 8.dp),
                text = stringResource(Res.string.copy_how_travel_ask)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
                text = stringResource(Res.string.copy_how_travel_answer),
                style = MaterialTheme.typography.body2
            )
        }
    }

    @Composable
    private fun MapContent(
        modifier: Modifier,
    ) {
        Column(modifier = modifier) {

            CommonTitle(
                modifier = Modifier.padding(
                    top = 24.dp,
                    bottom = 10.dp
                ),
                text = stringResource(Res.string.copy_map_expocapacitacion)
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                text = stringResource(Res.string.copy_here_is_you_map),
                style = MaterialTheme.typography.body2
            )

            Image(
                modifier = Modifier.clip(RoundedCornerShape(8.dp)),
                painter = painterResource(Res.drawable.img_museum_map),
                contentDescription = null
            )
        }
    }

    @Composable
    private fun RecommendationsContent(
        modifier: Modifier,
    ) {
        Column(
            modifier = modifier.padding(vertical = 24.dp)
        ) {

            CommonTitle(text = stringResource(Res.string.copy_recommendations))

            Text(
                modifier = Modifier.padding(vertical = 10.dp),
                text = stringResource(Res.string.copy_dont_touch),
                style = MaterialTheme.typography.body2
            )

            Text(
                text = stringResource(Res.string.copy_is_agree_photos),
                style = MaterialTheme.typography.body2
            )
        }
    }

    @Composable
    private fun CommonTitle(
        modifier: Modifier = Modifier,
        text: String,
        color: Color = MaterialTheme.colors.onBackground,
    ) {
        Text(
            modifier = modifier,
            text = text,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold,
            color = color
        )
    }
}