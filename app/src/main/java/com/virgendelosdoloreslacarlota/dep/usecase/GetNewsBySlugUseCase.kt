package com.virgendelosdoloreslacarlota.dep.usecase

import com.virgendelosdoloreslacarlota.dep.base.BaseUseCase
import com.virgendelosdoloreslacarlota.domain.exception.AppNotFoundException
import com.virgendelosdoloreslacarlota.domain.mass.Mass
import com.virgendelosdoloreslacarlota.domain.mass.MassRepository
import com.virgendelosdoloreslacarlota.domain.news.News
import com.virgendelosdoloreslacarlota.domain.news.NewsRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GetNewsBySlugUseCase  @Inject constructor(
    private val newsRepository: NewsRepository
): BaseUseCase<News, String>() {
    override suspend fun run(params: String, scope: CoroutineScope): News {
        return newsRepository.getNewsBySlug(params)
            ?: throw AppNotFoundException()
    }
}