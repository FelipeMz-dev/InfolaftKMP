package infolaft.composeApp.use_case

import infolaft.composeApp.data.InfolaftRepository

class ClearSessionUseCase(
    private val repository: InfolaftRepository
) {
    operator fun invoke() {
        repository.clearSession()
    }
}