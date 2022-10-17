package com.virgendelosdoloreslacarlota.domain.sponsor

interface SponsorRepository {
    suspend fun getSponsors(): List<Sponsor>
}