package com.virgendelosdoloreslacarlota.dep.usecase

import com.virgendelosdoloreslacarlota.dep.base.BaseUseCase
import com.virgendelosdoloreslacarlota.domain.legal.Legal
import com.virgendelosdoloreslacarlota.domain.legal.LegalRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GetLegalListUseCase @Inject constructor(
    private val legalRepository: LegalRepository
) : BaseUseCase<List<Legal>, BaseUseCase.None>() {
    override suspend fun run(params: None, scope: CoroutineScope): List<Legal> {
        return legalRepository.getLegalList()
    }
}