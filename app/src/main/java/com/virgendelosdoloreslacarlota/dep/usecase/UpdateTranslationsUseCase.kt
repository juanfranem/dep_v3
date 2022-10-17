package com.virgendelosdoloreslacarlota.dep.usecase

import com.virgendelosdoloreslacarlota.dep.base.BaseUseCase
import com.virgendelosdoloreslacarlota.domain.translations.TranslationRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class UpdateTranslationsUseCase @Inject constructor(
    private val translationRepository: TranslationRepository
): BaseUseCase<BaseUseCase.None, BaseUseCase.None>() {
    override suspend fun run(params: None, scope: CoroutineScope): None {
        translationRepository.getTranslations()
        return None()
    }
}