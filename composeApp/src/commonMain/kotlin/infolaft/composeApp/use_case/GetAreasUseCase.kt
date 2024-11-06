package infolaft.composeApp.use_case

import infolaft.composeApp.core.ResultResponse
import infolaft.composeApp.data.AreaEntity
import infolaft.composeApp.data.InfolaftRepository

class GetAreasUseCase(
    private val repository: InfolaftRepository
) {
    suspend operator fun invoke(isFull: Boolean): ResultResponse<List<AreaEntity>>{
        return repository.getAreas(isFull)
    }
}