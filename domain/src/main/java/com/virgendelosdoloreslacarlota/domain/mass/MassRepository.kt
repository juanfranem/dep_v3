package com.virgendelosdoloreslacarlota.domain.mass

import com.virgendelosdoloreslacarlota.domain.Page
import com.virgendelosdoloreslacarlota.domain.PageRequest

interface MassRepository {
    suspend fun getMasses(pageRequest: PageRequest = PageRequest()): Page<List<Mass>>
    suspend fun getMassBySlug(slug: String): Mass?
}