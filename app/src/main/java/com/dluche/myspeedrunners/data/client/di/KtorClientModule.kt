package com.dluche.myspeedrunners.data.client.di


import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object KtorClientModule {

    private const val BASE_URL = "https://www.speedrun.com/api/v1"

    @Singleton
    @Provides
    fun provideKtorClient(): HttpClient{
        return HttpClient(Android) {
            expectSuccess = true

            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.ALL
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            defaultRequest {
                url(BASE_URL)
                contentType(ContentType.Application.Json)
            }
            //Adiciona tratativa de exception customizada a todas as resquest.
            HttpResponseValidator {
//                handleResponseExceptionWithRequest { cause, _ ->
//                    throw if (cause is ClientRequestException) {
//                        val errorMessage = cause.response.bodyAsText()
//                        NetworkException.ApiException(errorMessage, cause.response.status.value)
//                    } else {
//                        NetworkException.UnknownNetworkException(cause)
//                    }
//                }
            }
        }
    }


}