package com.virgendelosdoloreslacarlota.domain.home

interface HomeRepository {
    suspend fun getHome(): Home
}