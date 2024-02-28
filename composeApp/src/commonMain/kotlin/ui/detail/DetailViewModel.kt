package ui.detail

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import repository.NewsAppRepository
import util.Resource
import util.extension.or

class DetailViewModel(
    private val repository: NewsAppRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow(DetailUiState())
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val _errorEvent = MutableSharedFlow<String>()
    val errorEvent: SharedFlow<String> = _errorEvent.asSharedFlow()

    fun generateContentGemini(text: String) {
        screenModelScope.launch {

            repository.generateContentWithAI(
                text = "Bu metne göre devamını İngilizce dilinde yazar mısın?:\n$text"
            ).collect { response ->
                when (response) {
                    is Resource.Error -> {
                        _uiState.update { it.copy(isLoading = false) }
                        _errorEvent.emit(response.message.message or "Try again!")
                    }

                    Resource.Loading -> {
                        _uiState.update { it.copy(isLoading = true) }
                    }

                    is Resource.Success -> {
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                generatedText = response.data.responseAsText
                            )
                        }
                    }
                }
            }
        }
    }
}
