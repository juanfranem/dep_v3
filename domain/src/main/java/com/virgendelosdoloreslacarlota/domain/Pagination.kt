package com.virgendelosdoloreslacarlota.domain

data class Pagination(
    val total: Int,
    val perPage: Int,
    val nextPage: Int? = null,
    val lastPage: Int? = null
)