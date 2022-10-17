package com.virgendelosdoloreslacarlota.data.domain

import com.virgendelosdoloreslacarlota.domain.home.Home
import kotlinx.serialization.Serializable

@Serializable
internal data class DataHome(
    val burials: List<DataBurial>,
    val masses: List<DataMass>,
    val sponsors: List<DataSponsor>,
    val news: List<DataNews>
) {
    fun toDomain() = Home(
        sponsors.map { it.toDomain() },
        masses.map { it.toDomain() },
        burials.map { it.toDomain() },
        news.map { it.toDomain() }
    )
}