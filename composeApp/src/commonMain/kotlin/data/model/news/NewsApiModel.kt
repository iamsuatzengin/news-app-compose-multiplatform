package data.model.news

import kotlinx.serialization.Serializable

@Serializable
data class NewsApiModel(
    val status: String? = null,
    val totalResults: Int? = null,
    val articles: List<ArticleApiModel>? = null
)
