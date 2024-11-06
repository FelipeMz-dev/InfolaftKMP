package infolaft.composeApp.ui.flow

import infolaft.composeApp.ui.flow.guide.MapScreen
import infolaft.composeApp.ui.flow.home.HomeScreen
import infolaft.composeApp.ui.flow.login.LoginScreen

enum class NavigationFlow(val title: String) {
    Home(title = "Bienvenida"),
    ShortGuide(title = "Guía corta"),
    FullGuide(title = "Guía completa"),
    Logout(title = "Logout");

    fun getScreen() = when (this) {
            Logout -> LoginScreen
            Home -> HomeScreen()
            ShortGuide -> MapScreen(isFull = false)
            FullGuide -> MapScreen(isFull = true)
        }

}
