package com.virgendelosdoloreslacarlota.dep.usecase

import com.virgendelosdoloreslacarlota.dep.base.BaseUseCase
import com.virgendelosdoloreslacarlota.domain.token.TokenRepository
import com.virgendelosdoloreslacarlota.domain.token.TokenRequest
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class SaveTokenUseCase  @Inject constructor(
    private val tokenRepository: TokenRepository
): BaseUseCase<BaseUseCase.None, TokenRequest>() {
    override suspend fun run(params: TokenRequest, scope: CoroutineScope): None {
        tokenRepository.saveToken(params)
        return None()
    }
}