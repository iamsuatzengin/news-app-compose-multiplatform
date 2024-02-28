package di

import data.network.NewsApiService
import data.network.NewsApiServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module
import repository.NewsAppRepository
import ui.detail.DetailViewModel
import ui.home.HomeViewModel
import util.Constants.NEWS_BASE_URL

val networkModule = module {
    single {
        HttpClient {
            defaultRequest {
                url(NEWS_BASE_URL)
            }

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    prettyPrint = true
                })
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.BODY
            }
        }
    }

    single<NewsApiService> { NewsApiServiceImpl(get()) }
    factory <HomeViewModel> { HomeViewModel(get()) }
    factory <DetailViewModel> { DetailViewModel(get()) }
    single { NewsAppRepository(get()) }
}
