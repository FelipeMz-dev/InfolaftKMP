package infolaft.composeApp.ui.flow.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import infolaft.composeApp.core.delegate.UserViewModelDelegate
import infolaft.composeApp.core.delegate.UserViewModelDelegateImpl
import infolaft.composeApp.data.UserEntity
import infolaft.composeApp.use_case.GetUserUseCase

class HomeViewModel(
    userViewModelDelegate: UserViewModelDelegateImpl
) : ViewModel(), UserViewModelDelegate by userViewModelDelegate {

    var state by mutableStateOf(UiState())
        private set

    fun init() {
        userInit()
        state = state.copy(
            user = user.value,
            userImage = getUserFile()
        )
    }

    data class UiState(
        val user: UserEntity? = null,
        val userImage: String? = null
    )
}