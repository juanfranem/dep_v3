package com.virgendelosdoloreslacarlota.data.domain

import com.virgendelosdoloreslacarlota.domain.Page
import kotlinx.serialization.Serializable

@Serializable
internal data class DataPage<T>(
    val pagination: DataPagination,
    val data: T
) {
    fun <X> toDomain(block: (data: T) -> X): Page<X> = Page(
        pagination = pagination.toDomain(),
        data = block(data)
    )
}