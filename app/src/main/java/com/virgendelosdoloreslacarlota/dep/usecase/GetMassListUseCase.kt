package com.virgendelosdoloreslacarlota.dep.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.virgendelosdoloreslacarlota.dep.base.BaseUseCase
import com.virgendelosdoloreslacarlota.dep.source.BurialPagingSource
import com.virgendelosdoloreslacarlota.dep.source.MassPagingSource
import com.virgendelosdoloreslacarlota.domain.burial.Burial
import com.virgendelosdoloreslacarlota.domain.burial.BurialRepository
import com.virgendelosdoloreslacarlota.domain.mass.Mass
import com.virgendelosdoloreslacarlota.domain.mass.MassRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMassListUseCase @Inject constructor(
    private val massRepository: MassRepository
): BaseUseCase<Flow<PagingData<Mass>>, BaseUseCase.None>() {
    override suspend fun run(params: None, scope: CoroutineScope): Flow<PagingData<Mass>> {
        return Pager(
            PagingConfig(pageSize = 15)
        ) {
            MassPagingSource(massRepository)
        }.flow.cachedIn(scope)
    }
}