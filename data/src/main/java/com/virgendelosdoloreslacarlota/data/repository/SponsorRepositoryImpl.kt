package com.virgendelosdoloreslacarlota.data.repository

import com.virgendelosdoloreslacarlota.data.HttpConstants
import com.virgendelosdoloreslacarlota.data.domain.DataResponse
import com.virgendelosdoloreslacarlota.data.domain.DataSponsor
import com.virgendelosdoloreslacarlota.domain.exception.AppHttpException
import com.virgendelosdoloreslacarlota.domain.exception.AppNoInternetException
import com.virgendelosdoloreslacarlota.domain.sponsor.Sponsor
import com.virgendelosdoloreslacarlota.domain.sponsor.SponsorRepository
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import java.io.IOException

internal class SponsorRepositoryImpl(private val httpClient: HttpClient): SponsorRepository {
    override suspend fun getSponsors(): List<Sponsor> {
        return try {
            httpClient.get<DataResponse<List<DataSponsor>>>(HttpConstants.GET_SPONSORS)
                .data.map { it.toDomain() }
        } catch (e: ClientRequestException) {
            throw AppHttpException(404)
        } catch (e: ServerResponseException) {
            throw AppHttpException(500)
        } catch (e: IOException) {
            throw AppNoInternetException()
        }
    }
}