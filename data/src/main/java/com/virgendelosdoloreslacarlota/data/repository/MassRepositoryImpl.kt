package com.virgendelosdoloreslacarlota.data.repository

import com.virgendelosdoloreslacarlota.data.BuildConfig
import com.virgendelosdoloreslacarlota.data.HttpConstants
import com.virgendelosdoloreslacarlota.data.domain.DataMass
import com.virgendelosdoloreslacarlota.data.domain.DataPage
import com.virgendelosdoloreslacarlota.data.domain.DataResponse
import com.virgendelosdoloreslacarlota.domain.Page
import com.virgendelosdoloreslacarlota.domain.PageRequest
import com.virgendelosdoloreslacarlota.domain.exception.AppHttpException
import com.virgendelosdoloreslacarlota.domain.exception.AppNoInternetException
import com.virgendelosdoloreslacarlota.domain.mass.Mass
import com.virgendelosdoloreslacarlota.domain.mass.MassRepository
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import java.io.IOException

internal class MassRepositoryImpl(private val httpClient: HttpClient): MassRepository {
    override suspend fun getMasses(pageRequest: PageRequest): Page<List<Mass>> {
        return try {
            httpClient.get<DataPage<List<DataMass>>>(HttpConstants.GET_MASSES)
                .toDomain {it.map { it.toDomain() }}
        } catch (e: ClientRequestException) {
            throw AppHttpException(404)
        } catch (e: ServerResponseException) {
            throw AppHttpException(500)
        } catch (e: IOException) {
            throw AppNoInternetException()
        }
    }

    override suspend fun getMassBySlug(slug: String): Mass? {
        return try {
            httpClient.get<DataResponse<DataMass>>(HttpConstants.GET_MASS + slug)
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