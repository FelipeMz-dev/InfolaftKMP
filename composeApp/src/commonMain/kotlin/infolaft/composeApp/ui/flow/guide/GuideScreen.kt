package infolaft.composeApp.ui.flow.guide

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset
import cafe.adriel.voyager.core.annotation.InternalVoyagerApi
import cafe.adriel.voyager.core.screen.ScreenKey
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.internal.BackHandler
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabDisposable
import cafe.adriel.voyager.navigator.tab.TabNavigator
import infolaft.composeApp.data.AreaEntity
import infolaft.composeApp.ui.common.CommonScreen
import infolaft.composeApp.ui.flow.NavigationFlow
import infolaft.composeApp.ui.flow.guide.components.BottomCircleButton
import infolaft.composeApp.ui.flow.guide.components.LastBottomCircleButton
import infolaft.composeApp.ui.flow.guide.tab.GuideTab
import infolaft.composeApp.ui.flow.guide.tab.MainGuideTab
import infolaft.composeApp.ui.flow.login.LoginScreen
import org.koin.compose.viewmodel.koinViewModel

class MapScreen(private val isFull: Boolean) : CommonScreen(
    flow = if (isFull) NavigationFlow.FullGuide else NavigationFlow.ShortGuide,
) {

    override val key: ScreenKey
        get() = super.key + "_full:$isFull"

    private val guideTabs = mutableListOf<Tab>()

    @Composable
    override fun Body() {

        val viewModel = koinViewModel<GuideViewModel>()
        val state = viewModel.state
        val navigator = LocalNavigator.current

        LaunchedEffect(Unit) {
            viewModel.init(isFull)
        }

        LaunchedEffect(state.isLoading) {
            isLoading = state.isLoading
        }

        LaunchedEffect(viewModel.logout.value, logoutState.value) {
            if (viewModel.logout.value || logoutState.value) {
                viewModel.clearSession()
                navigator?.replaceAll(LoginScreen)
            }
        }

        LaunchedEffect(viewModel.user.value) {
            userImage = viewModel.user.value?.files
        }

        LaunchedEffect(state.data) {
            state.data.getOrNull(0)?.let {
                guideTabs.add(MainGuideTab(state.data))
            }
            guideTabs.addAll(state.data.mapIndexed { index, areaEntity ->
                GuideTab(
                    index = (index + 1).toUShort(),
                    areaEntity = areaEntity
                )
            })
        }

        TabContent(
            state = state,
            viewModel = viewModel
        )
    }

    @Composable
    private fun TabContent(
        state: GuideViewModel.UiState,
        viewModel: GuideViewModel,
    ) {

        val currentNavigator = LocalNavigator.current
        var isForward by remember { mutableStateOf(true) }

        guideTabs.firstOrNull()?.let {

            TabNavigator(
                tab = it,
                tabDisposable = { navigator ->
                    TabDisposable(navigator, guideTabs)
                }
            ) { navigator ->

                OnBackPress(navigator) { isForward = false }

                guideTabs.forEach { tab ->

                    SlideTransition(
                        enable = navigator.current == tab,
                        isForward = isForward
                    ) {

                        LazyColumn(Modifier.fillMaxSize()) {

                            item {
                                tab.Content()
                            }

                            item {
                                guideTabs.getOrNull(tab.options.index.toInt() + 1)?.let {
                                    BottomContent(state.data.getOrNull(tab.options.index.toInt())) { area ->
                                        isForward = true
                                        navigator.current = it
                                        viewModel.saveProgress(area)

                                    }
                                } ?: LastBottomCircleButton(Modifier.fillMaxWidth()) {
                                    currentNavigator?.pop()
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun SlideTransition(
        enable: Boolean,
        isForward: Boolean,
        content: @Composable AnimatedVisibilityScope.() -> Unit,
    ) {
        AnimatedVisibility(
            visible = enable,
            enter = slideIn { IntOffset(it.width * if (isForward) 1 else -1, 0) },
            exit = slideOut { IntOffset.Zero },
            content = content
        )
    }

    @OptIn(InternalVoyagerApi::class)
    @Composable
    private fun OnBackPress(
        navigator: TabNavigator,
        onBack: () -> Unit,
    ) {

        var currentIndex by remember { mutableStateOf(guideTabs.indexOf(navigator.current)) }

        LaunchedEffect(navigator.current) {
            currentIndex = guideTabs.indexOf(navigator.current)
        }

        BackHandler(
            enabled = currentIndex > 0
        ) {
            if (currentIndex > 0) {
                onBack()
                navigator.current = guideTabs[currentIndex - 1]
            }
        }
    }

    @Composable
    private fun BottomContent(
        areaEntity: AreaEntity?,
        onClick: (AreaEntity) -> Unit,
    ) {
        areaEntity?.let {
            BottomCircleButton(
                modifier = Modifier.fillMaxWidth(),
                areaEntity = it,
                onClick = { onClick(it) }
            )
        }
    }
}