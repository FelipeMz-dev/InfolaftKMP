package infolaft.composeApp.use_case

import infolaft.composeApp.data.AccountEntity
import infolaft.composeApp.data.InfolaftRepository

class GetAccountUseCase(
    private val repository: InfolaftRepository
) {
    operator fun invoke(): AccountEntity? {
        return repository.getAccount()
    }
}