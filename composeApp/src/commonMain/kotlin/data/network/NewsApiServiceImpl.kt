package data.network

import data.model.news.NewsApiModel
import data.model.gemini.GeminiRequestModel
import data.model.geminiresponse.GeminiResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.appendPathSegments
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import util.Constants.GEMINI_API_KEY
import util.Constants.GEMINI_BASE_URL
import util.Constants.NEWS_API_KEY
import util.Resource

class NewsApiServiceImpl(
    private val client: HttpClient
) : NewsApiService {

    override fun getNewsByCategory(category: String): Flow<NewsApiModel> = flow {
        val response = client.get {
            url {
                appendPathSegments("top-headlines")
                parameters.append("country", "us")
                parameters.append("category", category)
                parameters.append("apiKey", NEWS_API_KEY)
            }
        }

        emit(response.body<NewsApiModel>())
    }.flowOn(Dispatchers.IO)

    override fun getBreakingNews(): Flow<NewsApiModel> = flow<NewsApiModel> {
        val response = client.get {
            url {
                appendPathSegments("top-headlines")
                parameters.append("sources", "bbc-news")
                parameters.append("apiKey", NEWS_API_KEY)
                parameters.append("pageSize", "10")
            }
        }

        emit(response.body())
    }.flowOn(Dispatchers.IO)

    override fun generateContentWithAI(
        text: GeminiRequestModel
    ): Flow<Resource<GeminiResponse>> = flow<Resource<GeminiResponse>> {
        val response = client.post(
            GEMINI_BASE_URL
        ) {
            url {
                appendPathSegments(
                    "v1beta",
                    "models",
                    "gemini-pro:generateContent"
                ) // use to add path parameters

                parameters.append("key", GEMINI_API_KEY) // use to add query parameters
            }

            contentType(ContentType.Application.Json)

            setBody(text)
        }

        val body = response.body<GeminiResponse>()

        if(!response.status.isSuccess()) {
            throw Throwable( "An error occurred!")
        }

        emit(Resource.Success(data = body))
    }.flowOn(Dispatchers.IO).catch { throwable ->
        emit(Resource.Error(throwable))
    }.onStart {
        emit(Resource.Loading)
    }
}
