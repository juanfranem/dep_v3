package com.virgendelosdoloreslacarlota.data.repository

import com.virgendelosdoloreslacarlota.data.BuildConfig
import com.virgendelosdoloreslacarlota.data.HttpConstants
import com.virgendelosdoloreslacarlota.data.domain.DataBurial
import com.virgendelosdoloreslacarlota.data.domain.DataHome
import com.virgendelosdoloreslacarlota.data.domain.DataResponse
import com.virgendelosdoloreslacarlota.domain.exception.AppHttpException
import com.virgendelosdoloreslacarlota.domain.exception.AppNoInternetException
import com.virgendelosdoloreslacarlota.domain.home.Home
import com.virgendelosdoloreslacarlota.domain.home.HomeRepository
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import java.io.IOException

internal class HomeRepositoryImpl(private val httpClient: HttpClient): HomeRepository {
    override suspend fun getHome(): Home {
        return try {
            httpClient.get<DataHome>(HttpConstants.HOME)
                .toDomain()
        } catch (e: ClientRequestException) {
            throw AppHttpException(404)
        } catch (e: ServerResponseException) {
            throw AppHttpException(500)
        } catch (e: IOException) {
            throw AppNoInternetException()
        }
    }
}