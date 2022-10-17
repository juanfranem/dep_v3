package com.virgendelosdoloreslacarlota.data.domain

import kotlinx.serialization.Serializable

@Serializable
internal data class DataChurch(
    val name: String,
    val address: String?,
    val latitude: Double?,
    val longitude: Double?
)