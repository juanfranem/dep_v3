package com.virgendelosdoloreslacarlota.data.domain

import com.virgendelosdoloreslacarlota.domain.sponsor.Sponsor
import kotlinx.serialization.Serializable

@Serializable
internal data class DataSponsor(
    val id: String,
    val image: String?,
    val title: String?,
    val url: String?
) {
    fun toDomain() = Sponsor(
        image, title, url
    )
}