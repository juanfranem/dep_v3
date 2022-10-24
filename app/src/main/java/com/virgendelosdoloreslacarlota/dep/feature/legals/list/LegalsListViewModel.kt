package com.virgendelosdoloreslacarlota.dep.feature.legals.list

import androidx.lifecycle.viewModelScope
import com.virgendelosdoloreslacarlota.dep.base.BaseUseCase
import com.virgendelosdoloreslacarlota.dep.base.BaseViewModel
import com.virgendelosdoloreslacarlota.dep.usecase.GetLegalListUseCase
import com.virgendelosdoloreslacarlota.domain.translations.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LegalsListViewModel @Inject constructor(
    private val getLegalListUseCase: GetLegalListUseCase,
    translationRepository: TranslationRepository
) :
    BaseViewModel<LegalsListInterfaces.Event,
            LegalsListInterfaces.State, LegalsListInterfaces.Effect>(
        translationRepository
    ) {
    override fun createInitialState(): LegalsListInterfaces.State {
        return LegalsListInterfaces.State(
            LegalsListInterfaces.LoadingScreenState.Idle
        )
    }

    override fun handleEvent(event: LegalsListInterfaces.Event) {
        when (event) {
            LegalsListInterfaces.Event.LoadData -> {
                getLegalListUseCase(BaseUseCase.None(), viewModelScope) { result ->
                    if (result.isSuccess) {
                        setState {
                            LegalsListInterfaces.State(
                                LegalsListInterfaces.LoadingScreenState.Success(result.getOrThrow())
                            )
                        }
                    } else {
                        setState {
                            LegalsListInterfaces.State(
                                LegalsListInterfaces.LoadingScreenState.Error
                            )
                        }
                    }
                }
            }
        }
    }
}