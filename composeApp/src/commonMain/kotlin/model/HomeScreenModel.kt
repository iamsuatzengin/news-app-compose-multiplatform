package model

import data.model.news.ArticleApiModel

data class HomeScreenModel(
    val breakingNews: List<ArticleApiModel>,
    val newsByCategory: List<ArticleApiModel>
)
