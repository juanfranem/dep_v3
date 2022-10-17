package com.virgendelosdoloreslacarlota.dep.usecase

import com.virgendelosdoloreslacarlota.dep.base.BaseUseCase
import com.virgendelosdoloreslacarlota.domain.exception.AppNotFoundException
import com.virgendelosdoloreslacarlota.domain.legal.Legal
import com.virgendelosdoloreslacarlota.domain.legal.LegalRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GetLegalBySlugUseCase @Inject constructor(
    private val legalRepository: LegalRepository
) : BaseUseCase<Legal, String>() {
    override suspend fun run(params: String, scope: CoroutineScope): Legal {
        return legalRepository.getLegalBySlug(params)
            ?: throw AppNotFoundException()
    }

}