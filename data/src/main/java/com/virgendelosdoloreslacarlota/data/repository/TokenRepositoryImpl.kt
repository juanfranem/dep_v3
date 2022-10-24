package com.virgendelosdoloreslacarlota.data.repository

import com.virgendelosdoloreslacarlota.data.HttpConstants
import com.virgendelosdoloreslacarlota.data.domain.DataToken
import com.virgendelosdoloreslacarlota.domain.exception.AppHttpException
import com.virgendelosdoloreslacarlota.domain.exception.AppNoInternetException
import com.virgendelosdoloreslacarlota.domain.token.TokenRepository
import com.virgendelosdoloreslacarlota.domain.token.TokenRequest
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import java.io.IOException

internal class TokenRepositoryImpl(private val httpClient: HttpClient) : TokenRepository {
    override suspend fun saveToken(tokenRequest: TokenRequest) {
        try {
            httpClient.post<Any>(
                path = HttpConstants.POST_TOKEN,
                body = DataToken(tokenRequest.token)
            )
        } catch (e: ClientRequestException) {
            throw AppHttpException(404)
        } catch (e: ServerResponseException) {
            throw AppHttpException(500)
        } catch (e: IOException) {
            throw AppNoInternetException()
        }
    }
}