package infolaft.composeApp.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.russhwolf.settings.Settings
import infolaft.composeApp.core.KEY_SESSION

class InfolaftCache {

    private val settings:Settings by lazy { Settings() }
    private val user: MutableState<UserEntity?> = mutableStateOf(null)
    private val account: MutableState<AccountEntity?> = mutableStateOf(null)

    fun saveUser(userEntity: UserEntity) {
        user.value = userEntity
    }

    fun getUser(): UserEntity? {
        return user.value
    }

    fun saveAccount(accountEntity: AccountEntity) {
        account.value = accountEntity
    }

    fun getAccount(): AccountEntity? {
        return account.value
    }

    fun saveSession(sessionEntity: SessionEntity) {
        settings.putString(KEY_SESSION, sessionEntity.toString())
    }

    fun loadSession() {
        if (user.value == null || account.value == null) {
            val session = settings.getStringOrNull(KEY_SESSION)
            session?.let {
                val sessionEntity = SessionEntity().fromString(session)
                val user = sessionEntity.user
                val account = sessionEntity.account
                if (user != null && account != null) {
                    saveUser(user)
                    saveAccount(account)
                }
            }
        }
    }

    fun clearSession() {
        settings.remove(KEY_SESSION)
        user.value = null
        account.value = null
    }
}