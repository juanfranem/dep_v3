package com.virgendelosdoloreslacarlota.dep.feature.settings.detail

import androidx.lifecycle.viewModelScope
import com.virgendelosdoloreslacarlota.dep.base.BaseViewModel
import com.virgendelosdoloreslacarlota.dep.usecase.GetLegalBySlugUseCase
import com.virgendelosdoloreslacarlota.domain.translations.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsDetailViewModel @Inject constructor(
    private val getLegalBySlugUseCase: GetLegalBySlugUseCase,
    translationRepository: TranslationRepository) :
    BaseViewModel<SettingsDetailInterfaces.Event,
            SettingsDetailInterfaces.State, SettingsDetailInterfaces.Effect>(
        translationRepository
    ) {
    override fun createInitialState(): SettingsDetailInterfaces.State {
        return SettingsDetailInterfaces.State(
            SettingsDetailInterfaces.LoadingScreenState.Idle
        )
    }

    override fun handleEvent(event: SettingsDetailInterfaces.Event) {
        when (event) {
            is SettingsDetailInterfaces.Event.LoadData -> {
                getLegalBySlugUseCase(event.slug, viewModelScope) { result ->
                    if (result.isSuccess) {
                        setState {
                            SettingsDetailInterfaces.State(
                                SettingsDetailInterfaces.LoadingScreenState.Success(result.getOrThrow())
                            )
                        }
                    }
                }
            }
        }
    }
}