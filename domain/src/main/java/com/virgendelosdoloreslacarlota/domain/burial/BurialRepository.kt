package com.virgendelosdoloreslacarlota.domain.burial

import com.virgendelosdoloreslacarlota.domain.Page
import com.virgendelosdoloreslacarlota.domain.PageRequest

interface BurialRepository {
    suspend fun getBurials(pageRequest: PageRequest = PageRequest()): Page<List<Burial>>
    suspend fun getBurialBySlug(slug: String): Burial?
}