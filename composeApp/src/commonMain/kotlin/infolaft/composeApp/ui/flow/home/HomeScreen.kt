package infolaft.composeApp.ui.flow.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import infolaft.composeApp.data.InfolaftService
import infolaft.composeApp.data.InfolaftRepository
import infolaft.composeApp.ui.flow.NavigationFlow
import infolaft.composeApp.ui.flow.guide.MapScreen
import infolaft.composeApp.ui.common.CommonButton
import infolaft.composeApp.ui.common.CommonScreen
import infolaft.composeApp.ui.flow.login.LoginScreen
import infolaftappkmp.composeapp.generated.resources.Res
import infolaftappkmp.composeapp.generated.resources.api_key
import infolaftappkmp.composeapp.generated.resources.copy_compliance_story
import infolaftappkmp.composeapp.generated.resources.copy_digital_guide
import infolaftappkmp.composeapp.generated.resources.copy_full_guide
import infolaftappkmp.composeapp.generated.resources.copy_short_guide
import infolaftappkmp.composeapp.generated.resources.copy_welcome_app
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

class HomeScreen : CommonScreen(NavigationFlow.Home) {

    @Composable
    override fun Body() {

        val navigator = LocalNavigator.current
        val viewModel = koinViewModel<HomeViewModel>()
        val state = viewModel.state

        LaunchedEffect(Unit) {
            viewModel.init()
        }

        LaunchedEffect(state.userImage) {
            userImage = state.userImage
        }

        LaunchedEffect(viewModel.logout.value, logoutState.value) {
            if (viewModel.logout.value || logoutState.value) {
                viewModel.clearSession()
                navigator?.replaceAll(LoginScreen)
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colors.primary),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                state.user?.let {
                    BodyTitledParagraph(
                        modifier = Modifier.fillMaxWidth(),
                        title = it.title,
                        subtitle = it.subtitle,
                        description = it.description
                    )
                }

                ButtonsNavigation(modifier = Modifier.fillMaxWidth())
            }
        }
    }


    @Composable
    private fun BodyTitledParagraph(
        modifier: Modifier,
        title: String,
        subtitle: String,
        description: String,
    ) {

        Column(
            modifier = modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h4,
                color = MaterialTheme.colors.onPrimary,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.h5,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.onPrimary,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(vertical = 16.dp),
                text = description,
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.onPrimary,
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    private fun ButtonsNavigation(modifier: Modifier) {

        val navigator = LocalNavigator.current

        Column(
            modifier = modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            CommonButton(text = stringResource(Res.string.copy_short_guide)) {
                navigator?.push(MapScreen(false))
            }

            CommonButton(text = stringResource(Res.string.copy_full_guide)) {
                navigator?.push(MapScreen(true))
            }
        }
    }
}