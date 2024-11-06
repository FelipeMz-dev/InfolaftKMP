package infolaft.composeApp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import infolaft.composeApp.ui.flow.NavigationFlow
import kotlinx.coroutines.launch

open class CommonScreen(private val flow: NavigationFlow) : Screen {

    open var isLoading by mutableStateOf(false)
    open var userImage by mutableStateOf<String?>(null)
    open val logoutState = mutableStateOf(false)

    @Composable
    override fun Content() {

        val scope = rememberCoroutineScope()
        val scaffoldState = rememberScaffoldState()

        Scaffold(
            scaffoldState = scaffoldState,
            modifier = Modifier.fillMaxSize(),
            topBar = {
                CommonAppBar {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            },
            drawerContent = {
                NavigationMenu(
                    scaffoldState = scaffoldState,
                    flow = flow,
                    imageUrl = userImage,
                    logoutState = logoutState
                )
            }
        ) {
            Box(modifier = Modifier.fillMaxSize()) {

                Body()

                if (isLoading) {
                    Loading(Modifier.fillMaxSize())
                }
            }
        }
    }

    @Composable
    private fun Loading(
        modifier: Modifier
    ) {
        if (isLoading) {
            Box(
                modifier = modifier.background(MaterialTheme.colors.surface.copy(alpha = 0.8f)),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()

                    Text(
                        text = "Loading...",
                        style = MaterialTheme.typography.h6
                    )
                }
            }
        }
    }

    @Composable
    open fun Body() {
        /**Content of screen**/
    }
}