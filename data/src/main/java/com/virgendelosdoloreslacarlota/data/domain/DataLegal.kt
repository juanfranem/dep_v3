package com.virgendelosdoloreslacarlota.data.domain

import com.virgendelosdoloreslacarlota.data.BuildConfig
import com.virgendelosdoloreslacarlota.domain.legal.Legal
import kotlinx.serialization.Serializable

@Serializable
internal data class DataLegal(
    val title: String,
    val slug: String,
    val body: String? = null
) {
    fun toDomain() = Legal(
        title, body, BuildConfig.URL_BASE + slug
    )
}