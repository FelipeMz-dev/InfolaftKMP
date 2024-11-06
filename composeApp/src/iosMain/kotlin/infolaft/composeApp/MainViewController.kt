package com.infolaft.composeApp

import androidx.compose.ui.window.ComposeUIViewController
import infolaft.composeApp.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) { App() }