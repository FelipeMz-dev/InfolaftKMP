package infolaft.composeApp.ui.flow.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import infolaft.composeApp.ui.flow.home.HomeScreen
import infolaftappkmp.composeapp.generated.resources.Res
import infolaftappkmp.composeapp.generated.resources.copy_login
import infolaftappkmp.composeapp.generated.resources.copy_company_code
import infolaftappkmp.composeapp.generated.resources.copy_continue
import infolaftappkmp.composeapp.generated.resources.copy_email
import infolaftappkmp.composeapp.generated.resources.ic_title_infolaft
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

object LoginScreen : Screen {

    @Composable
    override fun Content() {

        val viewModel = koinViewModel<LoginViewModel>()
        val state = viewModel.state
        val navigator = LocalNavigator.current

        var isPrintLogin by remember { mutableStateOf(false) }

        LaunchedEffect(state.loginState) {
            isPrintLogin = state.loginState is LoginState.Idle
        }

        if (state.loginState is LoginState.Success) {
            LaunchedEffect(Unit) {
                navigator?.replace(HomeScreen())
            }
        }

        if (isPrintLogin) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.primary),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Image(
                    modifier = Modifier.padding(24.dp),
                    painter = painterResource(Res.drawable.ic_title_infolaft),
                    contentDescription = null
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = MaterialTheme.colors.surface,
                            shape = RoundedCornerShape(18.dp)
                        )
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Body(
                        modifier = Modifier.fillMaxWidth(),
                        viewModel = viewModel,
                        state = state
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    when (state.loginState) {
                        is LoginState.Loading -> CircularProgressIndicator()
                        is LoginState.Error -> Text(
                            text = state.loginState.message,
                            color = MaterialTheme.colors.error
                        )
                        else -> {}
                    }
                }
            }
        }
    }

    @Composable
    private fun Body(
        modifier: Modifier,
        viewModel: LoginViewModel,
        state: LoginViewModel.UiState
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(Res.string.copy_login),
                style = MaterialTheme.typography.h4
            )

            InputAccount(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                viewModel = viewModel,
                state = state
            )

            Button(
                onClick = { viewModel.login() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(Res.string.copy_continue))
            }
        }
    }

    @Composable
    private fun InputAccount(
        modifier: Modifier,
        viewModel: LoginViewModel,
        state: LoginViewModel.UiState,
    ) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            OutlinedTextField(
                value = state.account,
                onValueChange = { viewModel.setAccount(it) },
                label = { Text(stringResource(Res.string.copy_email)) },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = state.code,
                onValueChange = { viewModel.setCode(it) },
                label = { Text(stringResource(Res.string.copy_company_code)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}