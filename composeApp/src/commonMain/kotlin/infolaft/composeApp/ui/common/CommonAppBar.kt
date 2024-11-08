package infolaft.composeApp.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import infolaftappkmp.composeapp.generated.resources.Res
import infolaftappkmp.composeapp.generated.resources.ic_title_infolaft
import org.jetbrains.compose.resources.painterResource

@Composable
fun CommonAppBar(onClick: () -> Unit) {
    TopAppBar(
        title = {
            Image(
                painter = painterResource(Res.drawable.ic_title_infolaft),
                contentDescription = null,
            )
        },
        actions = {
            IconButton(onClick = onClick) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        },
        backgroundColor = Color(128, 180, 198)
    )
}