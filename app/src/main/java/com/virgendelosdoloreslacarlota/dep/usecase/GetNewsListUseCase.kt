package com.virgendelosdoloreslacarlota.dep.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.virgendelosdoloreslacarlota.dep.base.BaseUseCase
import com.virgendelosdoloreslacarlota.dep.source.NewsPagingSource
import com.virgendelosdoloreslacarlota.domain.news.News
import com.virgendelosdoloreslacarlota.domain.news.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsListUseCase @Inject constructor(
    private val newsRepository: NewsRepository
): BaseUseCase<Flow<PagingData<News>>, BaseUseCase.None>() {
    override suspend fun run(params: None, scope: CoroutineScope): Flow<PagingData<News>> {
        return Pager(
            PagingConfig(pageSize = 15)
        ) {
            NewsPagingSource(newsRepository)
        }.flow.cachedIn(scope)
    }
}