package com.virgendelosdoloreslacarlota.dep.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.virgendelosdoloreslacarlota.domain.PageRequest
import com.virgendelosdoloreslacarlota.domain.mass.Mass
import com.virgendelosdoloreslacarlota.domain.mass.MassRepository


class MassPagingSource(
    private val burialRepository: MassRepository
): PagingSource<Int, Mass>() {
    override fun getRefreshKey(state: PagingState<Int, Mass>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Mass> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = burialRepository.getMasses(PageRequest(nextPageNumber))
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