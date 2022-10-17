package com.virgendelosdoloreslacarlota.dep.feature.burial.detail

import androidx.lifecycle.viewModelScope
import com.virgendelosdoloreslacarlota.dep.base.BaseViewModel
import com.virgendelosdoloreslacarlota.dep.usecase.GetBurialBySlugUseCase
import com.virgendelosdoloreslacarlota.domain.translations.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BurialDetailViewModel @Inject constructor(
    private val getBurialBySlugUseCase: GetBurialBySlugUseCase,
    translationRepository: TranslationRepository) :
    BaseViewModel<BurialDetailInterfaces.Event,
            BurialDetailInterfaces.State, BurialDetailInterfaces.Effect>(
        translationRepository
    ) {
    override fun createInitialState(): BurialDetailInterfaces.State {
        return BurialDetailInterfaces.State(
            BurialDetailInterfaces.LoadingScreenState.Idle
        )
    }

    override fun handleEvent(event: BurialDetailInterfaces.Event) {
        when (event) {
            is BurialDetailInterfaces.Event.LoadData -> {
                getBurialBySlugUseCase(event.slug, viewModelScope) { result ->
                    if (result.isSuccess) {
                        setState {
                            BurialDetailInterfaces.State(
                                BurialDetailInterfaces.LoadingScreenState.Success(result.getOrThrow())
                            )
                        }
                    }
                }
            }
        }
    }
}