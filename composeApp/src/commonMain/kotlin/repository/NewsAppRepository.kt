package repository

import data.model.news.CategoryType
import data.model.news.NewsApiModel
import data.model.gemini.GeminiRequestModel
import data.model.geminiresponse.GeminiResponse
import data.network.NewsApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import model.HomeScreenModel
import util.Resource

class NewsAppRepository(
    private val apiService: NewsApiService
) {
    fun getBreakingNews(): Flow<NewsApiModel> = apiService.getBreakingNews()

    fun getNewsByCategory(category: String): Flow<NewsApiModel> =
        apiService.getNewsByCategory(category)

    fun getBreakingNewsAndCategories(): Flow<Resource<HomeScreenModel>> = combine(
        getBreakingNews(),
        getNewsByCategory(CategoryType.BUSINESS.name.lowercase())
    ) { breakingNews, newsByCategory ->
        Resource.Success(
            data = HomeScreenModel(
                breakingNews = breakingNews.articles.orEmpty(),
                newsByCategory = newsByCategory.articles.orEmpty()
            )
        ) as Resource<HomeScreenModel>

    }.catch { throwable ->
        emit(Resource.Error(message = throwable))
    }.onStart {
        emit(Resource.Loading)
    }

    fun generateContentWithAI(text: String): Flow<Resource<GeminiResponse>> {
        val model = GeminiRequestModel.textModel(text = text)

        return apiService.generateContentWithAI(model)
    }
}
