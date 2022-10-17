package com.virgendelosdoloreslacarlota.data.domain
import kotlinx.serialization.Serializable

@Serializable
internal data class DataTranslation(
    val key: String,
    val translation: String
)