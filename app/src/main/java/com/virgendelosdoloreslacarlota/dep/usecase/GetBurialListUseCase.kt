package com.virgendelosdoloreslacarlota.dep.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.virgendelosdoloreslacarlota.dep.base.BaseUseCase
import com.virgendelosdoloreslacarlota.dep.source.BurialPagingSource
import com.virgendelosdoloreslacarlota.domain.burial.Burial
import com.virgendelosdoloreslacarlota.domain.burial.BurialRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBurialListUseCase @Inject constructor(
    private val burialRepository: BurialRepository
): BaseUseCase<Flow<PagingData<Burial>>, BaseUseCase.None>() {
    override suspend fun run(params: None, scope: CoroutineScope): Flow<PagingData<Burial>> {
        return Pager(
            PagingConfig(pageSize = 15)
        ) {
            BurialPagingSource(burialRepository)
        }.flow.cachedIn(scope)
    }
}