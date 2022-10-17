package com.virgendelosdoloreslacarlota.domain

data class Page<T>(
    val pagination: Pagination,
    val data: T
)