package com.virgendelosdoloreslacarlota.data.domain

import com.virgendelosdoloreslacarlota.domain.Pagination
import kotlinx.serialization.Serializable

@Serializable
internal data class DataPagination(
    val total: Int,
    val count: Int,
    val per_page: Int,
    val current_page: Int,
    val total_pages: Int?,
    val next: Int?
) {
    fun toDomain(): Pagination = Pagination(
        total = total,
        perPage = per_page,
        nextPage = next,
        lastPage = total_pages
    )
}