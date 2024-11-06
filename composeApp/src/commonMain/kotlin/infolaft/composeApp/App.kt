package com.infolaft.composeApp

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import infolaft.composeApp.ui.InfolaftTheme
import infolaft.composeApp.ui.flow.login.LoginScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App() {

    InfolaftTheme {
        setSingletonImageLoaderFactory { context ->
            ImageLoader.Builder(context)
                .crossfade(true)
                .build()
        }

        Navigator(
            screen = LoginScreen
        ) { navigator ->
            SlideTransition(
                navigator = navigator,
            )
        }
    }
}