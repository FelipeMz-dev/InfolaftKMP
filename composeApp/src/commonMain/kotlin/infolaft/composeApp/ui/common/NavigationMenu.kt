package infolaft.composeApp.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import infolaft.composeApp.core.extension.shimmerEffect
import infolaft.composeApp.ui.flow.NavigationFlow
import infolaftappkmp.composeapp.generated.resources.Res
import infolaftappkmp.composeapp.generated.resources.copy_infolaft_copyright
import infolaftappkmp.composeapp.generated.resources.copy_infolaft_email
import infolaftappkmp.composeapp.generated.resources.copy_museum_address
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource

@Composable
fun NavigationMenu(
    scaffoldState: ScaffoldState,
    flow: NavigationFlow,
    logoutState: MutableState<Boolean>,
    imageUrl: String?,
) {

    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        imageUrl?.let {
            ImageUser(
                modifier = Modifier.fillMaxWidth(),
                imageUrl = it
            )
        }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colors.primaryVariant)
                .weight(1f)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Icon(
                modifier = Modifier
                    .alpha(0.5f)
                    .padding(8.dp)
                    .size(40.dp)
                    .clickable {
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    },
                imageVector = Icons.Default.Close,
                tint = MaterialTheme.colors.onPrimary,
                contentDescription = null,
            )

            NavigationFlow.entries.forEach {
                ButtonBodyNavigation(
                    modifier = Modifier.fillMaxWidth(),
                    scaffoldState = scaffoldState,
                    flow = flow,
                    item = it
                ) { logoutState.value = true }
            }
        }

        NavigationMenuFooter(Modifier.fillMaxWidth())
    }
}

@Composable
private fun ImageUser(
    modifier: Modifier,
    imageUrl: String
) {

    val image = rememberAsyncImagePainter(imageUrl)

    Image(
        modifier = modifier
            .height(100.dp)
            .padding(14.dp)
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

@Composable
private fun ButtonBodyNavigation(
    modifier: Modifier,
    scaffoldState: ScaffoldState,
    flow: NavigationFlow,
    item: NavigationFlow,
    onLogout: () -> Unit,
) {

    val navigator = LocalNavigator.currentOrThrow
    val scope = rememberCoroutineScope()

    TextButton(
        modifier = modifier,
        onClick = {
            scope.launch {
                scaffoldState.drawerState.close()
                when (item) {
                    NavigationFlow.Logout -> {
                        onLogout()
                    }
                    else -> if (flow != item) {
                        navigator.push(item.getScreen())
                    }
                }
            }
        }
    ) {
        Text(
            modifier = Modifier.alpha(if (flow == item) 1f else 0.5f),
            text = item.title,
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h6,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun NavigationMenuFooter(modifier: Modifier) {
    Column(modifier = modifier.padding(8.dp)) {

        Text(
            text = stringResource(Res.string.copy_museum_address),
            color = MaterialTheme.colors.primaryVariant
        )

        Text(
            text = stringResource(Res.string.copy_infolaft_email),
            color = MaterialTheme.colors.primaryVariant
        )

        Text(
            text = stringResource(Res.string.copy_infolaft_copyright),
            color = MaterialTheme.colors.primaryVariant
        )
    }
}