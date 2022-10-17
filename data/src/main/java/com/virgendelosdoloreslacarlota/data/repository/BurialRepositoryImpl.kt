package com.virgendelosdoloreslacarlota.data.repository

import com.virgendelosdoloreslacarlota.data.BuildConfig
import com.virgendelosdoloreslacarlota.data.HttpConstants
import com.virgendelosdoloreslacarlota.data.domain.DataBurial
import com.virgendelosdoloreslacarlota.data.domain.DataPage
import com.virgendelosdoloreslacarlota.data.domain.DataResponse
import com.virgendelosdoloreslacarlota.domain.Page
import com.virgendelosdoloreslacarlota.domain.PageRequest
import com.virgendelosdoloreslacarlota.domain.burial.Burial
import com.virgendelosdoloreslacarlota.domain.burial.BurialRepository
import com.virgendelosdoloreslacarlota.domain.exception.AppHttpException
import com.virgendelosdoloreslacarlota.domain.exception.AppNoInternetException
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import java.io.IOException

internal class BurialRepositoryImpl(
    private val httpClient: HttpClient
): BurialRepository {
    override suspend fun getBurials(pageRequest: PageRequest): Page<List<Burial>> {
        return try {
            httpClient.get<DataPage<List<DataBurial>>>(HttpConstants.GET_BURIALS)
                .toDomain {it.map { it.toDomain() }}
        } catch (e: ClientRequestException) {
            throw AppHttpException(404)
        } catch (e: ServerResponseException) {
            throw AppHttpException(500)
        } catch (e: IOException) {
            throw AppNoInternetException()
        }
    }

    override suspend fun getBurialBySlug(slug: String): Burial? {
        return try {
            httpClient.get<DataResponse<DataBurial>>(HttpConstants.GET_BURIAL + slug)
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