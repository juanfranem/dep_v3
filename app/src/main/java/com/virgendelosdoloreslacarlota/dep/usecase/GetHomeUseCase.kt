package com.virgendelosdoloreslacarlota.dep.usecase

import com.virgendelosdoloreslacarlota.dep.base.BaseUseCase
import com.virgendelosdoloreslacarlota.domain.home.Home
import com.virgendelosdoloreslacarlota.domain.home.HomeRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class GetHomeUseCase @Inject constructor(
    private val homeRepository: HomeRepository
): BaseUseCase<Home, BaseUseCase.None>() {
    override suspend fun run(params: None, scope: CoroutineScope): Home {
        return homeRepository.getHome()
    }
}