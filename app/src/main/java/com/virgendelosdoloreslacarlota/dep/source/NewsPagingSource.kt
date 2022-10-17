package com.virgendelosdoloreslacarlota.dep.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.virgendelosdoloreslacarlota.domain.PageRequest
import com.virgendelosdoloreslacarlota.domain.news.News
import com.virgendelosdoloreslacarlota.domain.news.NewsRepository

class NewsPagingSource(
    private val newsRepository: NewsRepository
): PagingSource<Int, News>() {
    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = newsRepository.getNewsList(PageRequest(nextPageNumber))
            LoadResult.Page(
                data = response.data,
                prevKey = null,
                nextKey = response.pagination.nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}