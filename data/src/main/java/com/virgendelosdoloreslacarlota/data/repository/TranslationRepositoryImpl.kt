package com.virgendelosdoloreslacarlota.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.virgendelosdoloreslacarlota.data.HttpConstants
import com.virgendelosdoloreslacarlota.data.domain.DataResponse
import com.virgendelosdoloreslacarlota.data.domain.DataTranslation
import com.virgendelosdoloreslacarlota.data.storage.dataStore
import com.virgendelosdoloreslacarlota.domain.exception.AppHttpException
import com.virgendelosdoloreslacarlota.domain.exception.AppNoInternetException
import com.virgendelosdoloreslacarlota.domain.translations.TranslationRepository
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import java.io.IOException

internal class TranslationRepositoryImpl(
    private val context: Context,
    private val httpClient: HttpClient):
    TranslationRepository {
    override suspend fun getTranslations() {
        try {
            httpClient.get<List<DataTranslation>>(HttpConstants.GET_TRANSLATIONS)
                .forEach { translation ->
                    context.dataStore.edit {
                        it[stringPreferencesKey(translation.key)] = translation.translation
                    }
                }
        } catch (e: ClientRequestException) {
            throw AppHttpException(404)
        } catch (e: ServerResponseException) {
            throw AppHttpException(500)
        } catch (e: IOException) {
            throw AppNoInternetException()
        }
    }

    override fun getTranslation(key: String): String {
        return runBlocking {
            context.dataStore.data.map {
                it[stringPreferencesKey(key)] ?: key
            }.first()
        }
    }
}