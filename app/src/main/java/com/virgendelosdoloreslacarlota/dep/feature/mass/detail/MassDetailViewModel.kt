package com.virgendelosdoloreslacarlota.dep.feature.mass.detail

import androidx.lifecycle.viewModelScope
import com.virgendelosdoloreslacarlota.dep.base.BaseViewModel
import com.virgendelosdoloreslacarlota.dep.feature.home.HomeInterfaces
import com.virgendelosdoloreslacarlota.dep.usecase.GetMassBySlugUseCase
import com.virgendelosdoloreslacarlota.domain.translations.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MassDetailViewModel @Inject constructor(
    private val getMassBySlugUseCase: GetMassBySlugUseCase,
    translationRepository: TranslationRepository) :
    BaseViewModel<MassDetailInterfaces.Event,
            MassDetailInterfaces.State, MassDetailInterfaces.Effect>(
        translationRepository
    ) {
    override fun createInitialState(): MassDetailInterfaces.State {
        return MassDetailInterfaces.State(
            MassDetailInterfaces.LoadingScreenState.Idle
        )
    }

    override fun handleEvent(event: MassDetailInterfaces.Event) {
        when (event) {
            is MassDetailInterfaces.Event.LoadData -> {
                getMassBySlugUseCase(event.slug, viewModelScope) { result ->
                    if (result.isSuccess) {
                        setState {
                            MassDetailInterfaces.State(
                                MassDetailInterfaces.LoadingScreenState.Success(result.getOrThrow())
                            )
                        }
                    } else {
                        setState {
                            MassDetailInterfaces.State(
                                MassDetailInterfaces.LoadingScreenState.Error
                            )
                        }
                    }
                }
            }
        }
    }
}