package com.virgendelosdoloreslacarlota.domain.news

import com.virgendelosdoloreslacarlota.domain.Page
import com.virgendelosdoloreslacarlota.domain.PageRequest

interface NewsRepository {
    suspend fun getNewsList(pageRequest: PageRequest = PageRequest()): Page<List<News>>
    suspend fun getNewsBySlug(slug: String): News?
}