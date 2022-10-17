package com.virgendelosdoloreslacarlota.dep.feature.burial.list

import androidx.lifecycle.viewModelScope
import com.virgendelosdoloreslacarlota.dep.base.BaseUseCase
import com.virgendelosdoloreslacarlota.dep.base.BaseViewModel
import com.virgendelosdoloreslacarlota.dep.usecase.GetBurialListUseCase
import com.virgendelosdoloreslacarlota.domain.translations.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BurialListViewModel @Inject constructor(
    private val getBurialListUseCase: GetBurialListUseCase,
    translationRepository: TranslationRepository
) :
    BaseViewModel<BurialListInterfaces.Event,
            BurialListInterfaces.State, BurialListInterfaces.Effect>(
        translationRepository
    ) {
    override fun createInitialState(): BurialListInterfaces.State {
        return BurialListInterfaces.State(
            BurialListInterfaces.LoadingScreenState.Idle
        )
    }

    override fun handleEvent(event: BurialListInterfaces.Event) {
        when (event) {
            BurialListInterfaces.Event.LoadData -> {
                getBurialListUseCase(BaseUseCase.None(), viewModelScope) { result ->
                    if (result.isSuccess) {
                        setState {
                            BurialListInterfaces.State(
                                BurialListInterfaces.LoadingScreenState.Success(result.getOrThrow())
                            )
                        }
                    }
                }
            }
        }
    }
}