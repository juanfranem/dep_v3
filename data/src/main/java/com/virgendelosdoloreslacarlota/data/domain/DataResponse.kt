package com.virgendelosdoloreslacarlota.data.domain
import kotlinx.serialization.Serializable

@Serializable
data class DataResponse<T>(val data: T)