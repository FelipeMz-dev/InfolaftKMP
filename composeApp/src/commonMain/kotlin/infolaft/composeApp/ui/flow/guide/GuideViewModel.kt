package infolaft.composeApp.ui.flow.guide

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import infolaft.composeApp.core.ResultResponse
import infolaft.composeApp.data.AreaEntity
import infolaft.composeApp.use_case.GetAreasUseCase
import infolaft.composeApp.use_case.SaveProgressUseCase
import infolaft.composeApp.core.EMPTY_STRING
import infolaft.composeApp.core.delegate.UserViewModelDelegate
import infolaft.composeApp.core.delegate.UserViewModelDelegateImpl
import kotlinx.coroutines.launch

class GuideViewModel(
    private val getAreasUseCase: GetAreasUseCase,
    private val saveProgressUseCase: SaveProgressUseCase,
    userViewModelDelegate: UserViewModelDelegateImpl
) : ViewModel(), UserViewModelDelegate by userViewModelDelegate {

    var state by mutableStateOf(UiState())
        private set

    fun init(isFull: Boolean) {
        userInit()
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            getAreasUseCase(isFull).run {
                state = when (this) {
                    is ResultResponse.Success -> state.copy(data = data)
                    is ResultResponse.ResultError -> state.copy(error = "Error al obtener las areas del museo")
                    is ResultResponse.Error -> state.copy(error = error.message ?: EMPTY_STRING)
                }.copy(isLoading = false)
            }
        }
    }

    fun saveProgress(area: AreaEntity) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            saveProgressUseCase(area.id, area.full_guide).run {
                state = when (this) {
                    is ResultResponse.Success -> state
                    is ResultResponse.ResultError -> state.copy(error = "Error al guardar el progreso")
                    is ResultResponse.Error -> state.copy(error = error.message ?: EMPTY_STRING)
                }.copy(isLoading = false)
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val error: String = EMPTY_STRING,
        val data: List<AreaEntity> = emptyList()
    )
}