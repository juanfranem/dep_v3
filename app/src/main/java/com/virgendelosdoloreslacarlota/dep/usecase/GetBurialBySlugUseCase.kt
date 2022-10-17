package com.virgendelosdoloreslacarlota.dep.usecase

import com.virgendelosdoloreslacarlota.dep.base.BaseUseCase
import com.virgendelosdoloreslacarlota.domain.burial.Burial
import com.virgendelosdoloreslacarlota.domain.burial.BurialRepository
import com.virgendelosdoloreslacarlota.domain.exception.AppNotFoundException
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GetBurialBySlugUseCase @Inject constructor(
    private val burialRepository: BurialRepository
): BaseUseCase<Burial, String>() {
    override suspend fun run(params: String, scope: CoroutineScope): Burial {
        return burialRepository.getBurialBySlug(params)
            ?: throw AppNotFoundException()
    }
}