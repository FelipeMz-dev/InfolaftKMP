package infolaft.composeApp.ui.flow.guide.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import infolaft.composeApp.core.manager.ColorManager
import infolaft.composeApp.ui.common.CommonCircleButton
import infolaft.composeApp.data.AreaEntity
import infolaftappkmp.composeapp.generated.resources.Res
import infolaftappkmp.composeapp.generated.resources.copy_direct_to_circle
import infolaftappkmp.composeapp.generated.resources.copy_press_circle_finish
import infolaftappkmp.composeapp.generated.resources.copy_travel_finish
import org.jetbrains.compose.resources.stringResource

@Composable
fun BottomCircleButton(
    modifier: Modifier = Modifier,
    areaEntity: AreaEntity,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.padding(26.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = stringResource(Res.string.copy_direct_to_circle, areaEntity.color_name),
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.ExtraBold
        )

        CommonCircleButton(
            text = areaEntity.title,
            color = ColorManager.fromHexString(areaEntity.color),
            onClick = onClick
        )
    }
}

@Composable
fun LastBottomCircleButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier.padding(26.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = stringResource(Res.string.copy_press_circle_finish),
            textAlign = TextAlign.Center,
            textDecoration = TextDecoration.Underline,
            style = MaterialTheme.typography.body1,
            fontWeight = FontWeight.ExtraBold
        )

        CommonCircleButton(
            text = stringResource(Res.string.copy_travel_finish),
            color = MaterialTheme.colors.primaryVariant,
            onClick = onClick,
            hasIcon = false
        )
    }
}