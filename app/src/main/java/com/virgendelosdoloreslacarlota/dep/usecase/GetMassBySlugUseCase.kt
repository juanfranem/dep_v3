package com.virgendelosdoloreslacarlota.dep.usecase

import com.virgendelosdoloreslacarlota.dep.base.BaseUseCase
import com.virgendelosdoloreslacarlota.domain.burial.Burial
import com.virgendelosdoloreslacarlota.domain.burial.BurialRepository
import com.virgendelosdoloreslacarlota.domain.exception.AppNotFoundException
import com.virgendelosdoloreslacarlota.domain.mass.Mass
import com.virgendelosdoloreslacarlota.domain.mass.MassRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GetMassBySlugUseCase @Inject constructor(
    private val massRepository: MassRepository
): BaseUseCase<Mass, String>() {
    override suspend fun run(params: String, scope: CoroutineScope): Mass {
        return massRepository.getMassBySlug(params)
            ?: throw AppNotFoundException()
    }
}