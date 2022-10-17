package com.virgendelosdoloreslacarlota.data.domain

import com.virgendelosdoloreslacarlota.data.BuildConfig
import com.virgendelosdoloreslacarlota.domain.deceased.Deceased
import com.virgendelosdoloreslacarlota.domain.mass.Mass
import kotlinx.serialization.Serializable

@Serializable
internal data class DataMass(
    val event_date: String,
    val church: DataChurch,
    val name: String,
    val last_name: String,
    val sub_name: String?,
    val birth_date: String?,
    val death_date: String,
    val from_site: String?,
    val death_site: String?,
    val image: String?,
    val slug: String,
    val description: String?
) {
    fun toDomain() = Mass(
        Deceased(name, last_name, sub_name, birth_date, death_date, from_site, description),
        church.name,
        event_date,
        image,
        BuildConfig.URL_BASE + slug
    )
}