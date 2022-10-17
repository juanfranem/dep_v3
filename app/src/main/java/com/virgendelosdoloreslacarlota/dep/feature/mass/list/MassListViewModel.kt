package com.virgendelosdoloreslacarlota.dep.feature.mass.list

import androidx.lifecycle.viewModelScope
import com.virgendelosdoloreslacarlota.dep.base.BaseUseCase
import com.virgendelosdoloreslacarlota.dep.base.BaseViewModel
import com.virgendelosdoloreslacarlota.dep.usecase.GetMassListUseCase
import com.virgendelosdoloreslacarlota.domain.translations.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MassListViewModel @Inject constructor(
    private val getMassListUseCase: GetMassListUseCase,
    translationRepository: TranslationRepository
) :
    BaseViewModel<MassListInterfaces.Event,
            MassListInterfaces.State, MassListInterfaces.Effect>(
        translationRepository
    ) {
    override fun createInitialState(): MassListInterfaces.State {
        return MassListInterfaces.State(
            MassListInterfaces.LoadingScreenState.Idle
        )
    }

    override fun handleEvent(event: MassListInterfaces.Event) {
        when (event) {
            MassListInterfaces.Event.LoadData -> {
                getMassListUseCase(BaseUseCase.None(), viewModelScope) { result ->
                    if (result.isSuccess) {
                        setState {
                            MassListInterfaces.State(
                                MassListInterfaces.LoadingScreenState.Success(result.getOrThrow())
                            )
                        }
                    }
                }
            }
        }
    }
}