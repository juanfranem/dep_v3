package com.virgendelosdoloreslacarlota.domain.legal

interface LegalRepository {
    suspend fun getLegalList(): List<Legal>
    suspend fun getLegalBySlug(slug: String): Legal?
}