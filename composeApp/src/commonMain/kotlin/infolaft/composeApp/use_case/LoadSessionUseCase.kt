package infolaft.composeApp.use_case

import infolaft.composeApp.data.InfolaftRepository

class LoadSessionUseCase(
    private val repository: InfolaftRepository
) {
    operator fun invoke() {
        repository.loadSession()
    }
}