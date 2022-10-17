package com.virgendelosdoloreslacarlota.data.repository

import com.virgendelosdoloreslacarlota.data.BuildConfig
import com.virgendelosdoloreslacarlota.data.HttpConstants
import com.virgendelosdoloreslacarlota.data.domain.DataBurial
import com.virgendelosdoloreslacarlota.data.domain.DataLegal
import com.virgendelosdoloreslacarlota.data.domain.DataPage
import com.virgendelosdoloreslacarlota.data.domain.DataResponse
import com.virgendelosdoloreslacarlota.domain.exception.AppHttpException
import com.virgendelosdoloreslacarlota.domain.exception.AppNoInternetException
import com.virgendelosdoloreslacarlota.domain.legal.Legal
import com.virgendelosdoloreslacarlota.domain.legal.LegalRepository
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import java.io.IOException

internal class LegalRepositoryImpl(private val httpClient: HttpClient): LegalRepository {
    override suspend fun getLegalList(): List<Legal> {
        return try {
            httpClient.get<DataResponse<List<DataLegal>>>(HttpConstants.GET_LEGALS)
                .data.map { it.toDomain() }
        } catch (e: ClientRequestException) {
            throw AppHttpException(404)
        } catch (e: ServerResponseException) {
            throw AppHttpException(500)
        } catch (e: IOException) {
            throw AppNoInternetException()
        }
    }

    override suspend fun getLegalBySlug(slug: String): Legal? {
        return try {
            httpClient.get<DataResponse<DataLegal>>(HttpConstants.GET_LEGAL + slug)
                .data.toDomain()
        } catch (e: ClientRequestException) {
            throw AppHttpException(404)
        } catch (e: ServerResponseException) {
            throw AppHttpException(500)
        } catch (e: IOException) {
            throw AppNoInternetException()
        }
    }
}