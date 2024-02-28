package ui.home

import data.model.news.ArticleApiModel

data class HomeUiState(
    val isLoading: Boolean = false,
    val breakingNews: List<ArticleApiModel> = emptyList(),
    val newsByCategory: List<ArticleApiModel> = emptyList(),
)
