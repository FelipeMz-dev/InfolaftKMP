package infolaft.composeApp.ui.flow.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import infolaft.composeApp.core.EMPTY_STRING
import infolaft.composeApp.core.ResultResponse
import infolaft.composeApp.core.delegate.UserViewModelDelegate
import infolaft.composeApp.core.delegate.UserViewModelDelegateImpl
import infolaft.composeApp.core.manager.DateTimeManager
import infolaft.composeApp.data.UserEntity
import infolaft.composeApp.use_case.LoginUseCase
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    userViewModelDelegate: UserViewModelDelegateImpl
) : ViewModel(), UserViewModelDelegate by userViewModelDelegate {

    var state by mutableStateOf(UiState())
        private set

    init {
        userInit()
        state = state.copy(loginState = if (user.value == null) LoginState.Idle else LoginState.Success)
    }

    fun login() {
        state = state.copy(loginState = LoginState.Loading)
        validateVoidFields(
            ifTrue = {
                state = state.copy(
                    loginState = LoginState.Error("Campos vacíos")
                )
            },
            ifFalse = {
                loginRequest()
            }
        )
    }

    fun clear() {
        clearSession()
    }

    private fun loginRequest() {
        viewModelScope.launch {
            loginUseCase(state.account, state.code).run {
                state = when (this) {
                    is ResultResponse.Success -> state.copy(
                        loginState = validateDate(data)
                    )
                    is ResultResponse.ResultError -> state.copy(
                        loginState = LoginState.Error("Museo no se encontra o esta desactivado")
                    )
                    is ResultResponse.Error -> state.copy(
                        loginState = LoginState.Error("Error al conectarse con Infolaft")
                    )
                }
            }
        }
    }

    private fun validateDate(user: UserEntity): LoginState {
        return if (DateTimeManager.isDateAfterCurrent(user.end_date)) {
            LoginState.Success
        } else {
            LoginState.Error("El usuario ha expirado")
        }
    }

    private fun validateVoidFields(
        ifTrue: () -> Unit,
        ifFalse: () -> Unit,
    ) {
        if (state.account.isEmpty() || state.code.isEmpty()) ifTrue()
        else ifFalse()
    }

    fun setAccount(account: String) {
        state = state.copy(account = account)
    }

    fun setCode(code: String) {
        state = state.copy(code = code)
    }

    data class UiState(
        val loginState: LoginState = LoginState.Loading,
        val account: String = EMPTY_STRING,
        val code: String = EMPTY_STRING,
    )
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    object Success : LoginState()
    data class Error(val message: String) : LoginState()
}