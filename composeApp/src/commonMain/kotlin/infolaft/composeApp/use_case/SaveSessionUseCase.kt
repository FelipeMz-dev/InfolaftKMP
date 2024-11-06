package infolaft.composeApp.use_case

import infolaft.composeApp.data.AccountEntity
import infolaft.composeApp.data.InfolaftRepository
import infolaft.composeApp.data.SessionEntity
import infolaft.composeApp.data.UserEntity

class SaveSessionUseCase(
    private val repository: InfolaftRepository
) {
    operator fun invoke(
        user: UserEntity,
        account: AccountEntity,
    ) {
        repository.saveSession(
            SessionEntity(
                user = user,
                account = account
            )
        )
    }
}