package com.virgendelosdoloreslacarlota.dep.feature.legals.detail

import androidx.lifecycle.viewModelScope
import com.virgendelosdoloreslacarlota.dep.base.BaseViewModel
import com.virgendelosdoloreslacarlota.dep.usecase.GetLegalBySlugUseCase
import com.virgendelosdoloreslacarlota.domain.translations.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LegalsDetailViewModel @Inject constructor(
    private val getLegalBySlugUseCase: GetLegalBySlugUseCase,
    translationRepository: TranslationRepository) :
    BaseViewModel<LegalsDetailInterfaces.Event,
            LegalsDetailInterfaces.State, LegalsDetailInterfaces.Effect>(
        translationRepository
    ) {
    override fun createInitialState(): LegalsDetailInterfaces.State {
        return LegalsDetailInterfaces.State(
            LegalsDetailInterfaces.LoadingScreenState.Idle
        )
    }

    override fun handleEvent(event: LegalsDetailInterfaces.Event) {
        when (event) {
            is LegalsDetailInterfaces.Event.LoadData -> {
                getLegalBySlugUseCase(event.slug, viewModelScope) { result ->
                    if (result.isSuccess) {
                        setState {
                            LegalsDetailInterfaces.State(
                                LegalsDetailInterfaces.LoadingScreenState.Success(result.getOrThrow())
                            )
                        }
                    } else {
                        setState {
                            LegalsDetailInterfaces.State(
                                LegalsDetailInterfaces.LoadingScreenState.Error
                            )
                        }
                    }
                }
            }
        }
    }
}