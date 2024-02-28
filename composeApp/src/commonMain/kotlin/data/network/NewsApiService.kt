package data.network

import data.model.news.NewsApiModel
import data.model.gemini.GeminiRequestModel
import data.model.geminiresponse.GeminiResponse
import kotlinx.coroutines.flow.Flow
import util.Resource

interface NewsApiService {
    fun getBreakingNews(): Flow<NewsApiModel>
    fun getNewsByCategory(category: String): Flow<NewsApiModel>

    fun generateContentWithAI(text: GeminiRequestModel): Flow<Resource<GeminiResponse>>
}
