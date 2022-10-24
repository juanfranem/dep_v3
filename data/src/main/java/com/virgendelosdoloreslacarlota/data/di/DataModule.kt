@file:OptIn(ExperimentalSerializationApi::class, ExperimentalSerializationApi::class)

package com.virgendelosdoloreslacarlota.data.di

import android.content.Context
import android.util.Log
import com.virgendelosdoloreslacarlota.data.BuildConfig
import com.virgendelosdoloreslacarlota.data.repository.*
import com.virgendelosdoloreslacarlota.data.repository.BurialRepositoryImpl
import com.virgendelosdoloreslacarlota.data.repository.LegalRepositoryImpl
import com.virgendelosdoloreslacarlota.data.repository.MassRepositoryImpl
import com.virgendelosdoloreslacarlota.data.repository.NewsRepositoryImpl
import com.virgendelosdoloreslacarlota.domain.burial.BurialRepository
import com.virgendelosdoloreslacarlota.domain.home.HomeRepository
import com.virgendelosdoloreslacarlota.domain.legal.LegalRepository
import com.virgendelosdoloreslacarlota.domain.mass.MassRepository
import com.virgendelosdoloreslacarlota.domain.news.NewsRepository
import com.virgendelosdoloreslacarlota.domain.sponsor.SponsorRepository
import com.virgendelosdoloreslacarlota.domain.token.TokenRepository
import com.virgendelosdoloreslacarlota.domain.translations.TranslationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideJson(): Json = Json {
        isLenient = true
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    @Singleton
    @Provides
    fun provideHttpClient(json: Json): HttpClient = HttpClient(Android) {
        expectSuccess = true
        install(Logging) {
            logger = object: Logger {
                override fun log(message: String) {
                    Log.d("AppHttp", message)
                }
            }
            level = LogLevel.ALL
        }
        engine {}
        defaultRequest {
            url {
                protocol = URLProtocol.HTTPS
                host = BuildConfig.HTTP_URL_BASE
            }
            headers {
                contentType(ContentType.Application.Json)
            }
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }
    }

    @Singleton
    @Provides
    fun provideBurialRepository(httpClient: HttpClient): BurialRepository =
        BurialRepositoryImpl(httpClient)

    @Singleton
    @Provides
    fun provideMassRepository(httpClient: HttpClient): MassRepository =
        MassRepositoryImpl(httpClient)

    @Singleton
    @Provides
    fun provideLegalRepository(httpClient: HttpClient): LegalRepository =
        LegalRepositoryImpl(httpClient)

    @Singleton
    @Provides
    fun provideNewsRepository(httpClient: HttpClient): NewsRepository =
        NewsRepositoryImpl(httpClient)

    @Singleton
    @Provides
    fun provideSponsorRepository(httpClient: HttpClient): SponsorRepository =
        SponsorRepositoryImpl(httpClient)

    @Singleton
    @Provides
    fun provideTokenRepository(httpClient: HttpClient): TokenRepository =
        TokenRepositoryImpl(httpClient)

    @Singleton
    @Provides
    fun provideHomeRepository(httpClient: HttpClient): HomeRepository =
        HomeRepositoryImpl(httpClient)

    @Singleton
    @Provides
    fun provideTranslationRepository(
        @ApplicationContext context: Context,
        httpClient: HttpClient): TranslationRepository =
        TranslationRepositoryImpl(context, httpClient)
}