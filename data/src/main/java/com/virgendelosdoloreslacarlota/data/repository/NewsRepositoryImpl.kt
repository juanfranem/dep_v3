package com.virgendelosdoloreslacarlota.data.repository

import com.virgendelosdoloreslacarlota.data.BuildConfig
import com.virgendelosdoloreslacarlota.data.HttpConstants
import com.virgendelosdoloreslacarlota.data.domain.DataBurial
import com.virgendelosdoloreslacarlota.data.domain.DataNews
import com.virgendelosdoloreslacarlota.data.domain.DataPage
import com.virgendelosdoloreslacarlota.data.domain.DataResponse
import com.virgendelosdoloreslacarlota.domain.Page
import com.virgendelosdoloreslacarlota.domain.PageRequest
import com.virgendelosdoloreslacarlota.domain.exception.AppHttpException
import com.virgendelosdoloreslacarlota.domain.exception.AppNoInternetException
import com.virgendelosdoloreslacarlota.domain.news.News
import com.virgendelosdoloreslacarlota.domain.news.NewsRepository
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import java.io.IOException

internal class NewsRepositoryImpl(private val httpClient: HttpClient): NewsRepository {
    override suspend fun getNewsList(pageRequest: PageRequest): Page<List<News>> {
        return try {
            httpClient.get<DataPage<List<DataNews>>>(HttpConstants.GET_NEWS_LIST)
                .toDomain {it.map { it.toDomain() }}
        } catch (e: ClientRequestException) {
            throw AppHttpException(404)
        } catch (e: ServerResponseException) {
            throw AppHttpException(500)
        } catch (e: IOException) {
            throw AppNoInternetException()
        }
    }

    override suspend fun getNewsBySlug(slug: String): News? {
        return try {
            httpClient.get<DataResponse<DataNews>>(HttpConstants.GET_NEWS + slug)
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