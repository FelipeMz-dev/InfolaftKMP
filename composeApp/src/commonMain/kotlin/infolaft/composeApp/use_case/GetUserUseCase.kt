package infolaft.composeApp.use_case

import infolaft.composeApp.data.InfolaftRepository
import infolaft.composeApp.data.UserEntity

class GetUserUseCase(
    private val repository: InfolaftRepository
) {
    operator fun invoke(): UserEntity? {
        return repository.getUser()
    }
}