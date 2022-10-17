package com.virgendelosdoloreslacarlota.dep.feature.main

import androidx.lifecycle.viewModelScope
import com.virgendelosdoloreslacarlota.dep.base.BaseUseCase
import com.virgendelosdoloreslacarlota.dep.base.BaseViewModel
import com.virgendelosdoloreslacarlota.dep.usecase.UpdateTranslationsUseCase
import com.virgendelosdoloreslacarlota.dep.usecase.UpdateTranslationsUseCase_Factory
import com.virgendelosdoloreslacarlota.domain.translations.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val updateTranslationsUseCase: UpdateTranslationsUseCase,
    translationRepository: TranslationRepository
) : BaseViewModel<MainInterfaces.Event, MainInterfaces.State, MainInterfaces.Effect>(
    translationRepository
) {

    override fun createInitialState(): MainInterfaces.State {
        return MainInterfaces.State()
    }

    override fun handleEvent(event: MainInterfaces.Event) {
        when (event) {
            MainInterfaces.Event.LoadTranslations -> {
                updateTranslationsUseCase(BaseUseCase.None(), viewModelScope)
            }
        }
    }
}