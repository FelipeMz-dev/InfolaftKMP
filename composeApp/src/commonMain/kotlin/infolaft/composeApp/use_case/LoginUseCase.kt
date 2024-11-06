package infolaft.composeApp.use_case

import infolaft.composeApp.core.ResultResponse
import infolaft.composeApp.data.AccountEntity
import infolaft.composeApp.data.InfolaftRepository
import infolaft.composeApp.data.SessionEntity
import infolaft.composeApp.data.UserEntity

class LoginUseCase(
    private val repository: InfolaftRepository
) {
    suspend operator fun invoke(
        email: String,
        code: String
    ): ResultResponse<UserEntity> {
        val result = repository.login(email, code)
        if (result is ResultResponse.Success) {
            repository.saveSession(
                SessionEntity(
                    user = result.data,
                    account = AccountEntity(email, code)
                )
            )
            //repository.saveUser(result.data)
            //repository.saveAccount(AccountEntity(email, code))
        }
        return result
    }
}