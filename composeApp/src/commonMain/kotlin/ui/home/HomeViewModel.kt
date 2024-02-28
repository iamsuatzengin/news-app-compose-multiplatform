package ui.home

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import repository.NewsAppRepository
import util.Resource

class HomeViewModel(
    private val repository: NewsAppRepository
) : ScreenModel {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getHomeNews()
    }

    private fun getHomeNews() {
        screenModelScope.launch {
            repository.getBreakingNewsAndCategories().collect { homeScreenModel ->
                when (homeScreenModel) {
                    is Resource.Success -> {
                        _uiState.update { homeUiState ->
                            homeUiState.copy(
                                isLoading = false,
                                breakingNews = homeScreenModel.data.breakingNews,
                                newsByCategory = homeScreenModel.data.newsByCategory
                            )
                        }
                    }

                    Resource.Loading -> _uiState.update {
                        it.copy(isLoading = true)
                    }

                    else -> Unit
                }
            }
        }
    }

    fun getNewsByCategory(category: String) {
        screenModelScope.launch {
            repository.getNewsByCategory(category = category).collect { model ->
                _uiState.update { it.copy(newsByCategory = model.articles.orEmpty()) }
            }
        }
    }
}
