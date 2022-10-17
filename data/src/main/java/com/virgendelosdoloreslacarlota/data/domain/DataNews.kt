package com.virgendelosdoloreslacarlota.data.domain

import com.virgendelosdoloreslacarlota.data.BuildConfig
import com.virgendelosdoloreslacarlota.domain.news.News
import kotlinx.serialization.Serializable

@Serializable
internal data class DataNews(
    val id: String,
    val slug: String,
    val title: String,
    val body: String? = null,
    val url: String? = null,
    val image: String?,
    val createdAt: String,
    val updatedAt: String?
) {
    fun toDomain() = News(
        BuildConfig.URL_BASE + slug, title, body, image
    )
}