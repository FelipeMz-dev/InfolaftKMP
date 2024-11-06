package infolaft.composeApp.core.delegate

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import infolaft.composeApp.core.EMPTY_STRING
import infolaft.composeApp.core.manager.DateTimeManager
import infolaft.composeApp.data.AccountEntity
import infolaft.composeApp.data.UserEntity
import infolaft.composeApp.use_case.ClearSessionUseCase
import infolaft.composeApp.use_case.GetAccountUseCase
import infolaft.composeApp.use_case.GetUserUseCase
import infolaft.composeApp.use_case.LoadSessionUseCase
import infolaft.composeApp.use_case.SaveSessionUseCase

interface UserViewModelDelegate {

    fun userInit()

    fun getUserFile(): String?

    fun clearSession()

    val user: MutableState<UserEntity?>

    val account: MutableState<AccountEntity?>

    val logout: MutableState<Boolean>
}

class UserViewModelDelegateImpl(
    private val getUserUseCase: GetUserUseCase,
    private val getAccountUseCase: GetAccountUseCase,
    private val clearSessionUseCase: ClearSessionUseCase,
    loadSessionUseCase: LoadSessionUseCase
) : UserViewModelDelegate {

    init {
        loadSessionUseCase()
    }

    override val user: MutableState<UserEntity?> = mutableStateOf(null)
    override val account: MutableState<AccountEntity?> = mutableStateOf(null)

    override val logout: MutableState<Boolean> = mutableStateOf(false)

    override fun userInit() {
        user.value = getUserUseCase()
        account.value = getAccountUseCase()
        validateUser()
    }

    override fun getUserFile(): String? = user.value?.files

    override fun clearSession() {
        clearSessionUseCase()
    }

    private fun getEndDate(): String = user.value?.end_date ?: EMPTY_STRING

    private fun validateUser() {
        logout.value = user.value == null || !DateTimeManager.isDateAfterCurrent(getEndDate())
    }

}