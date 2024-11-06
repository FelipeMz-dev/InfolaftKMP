package infolaft.composeApp.use_case

import infolaft.composeApp.core.ErrorResponse
import infolaft.composeApp.core.ResultResponse
import infolaft.composeApp.data.InfolaftRepository
import infolaft.composeApp.data.ProgressEntity

class SaveProgressUseCase(
    private val repository: InfolaftRepository
) {
    suspend operator fun invoke(
        areaId: Int,
        isFull: Boolean,
    ): ResultResponse<ProgressEntity> {
        val account = repository.getAccount()
            ?: return ResultResponse.ResultError(ErrorResponse("Account not found"))
        return repository.saveProgress(account, areaId, isFull)
    }
}