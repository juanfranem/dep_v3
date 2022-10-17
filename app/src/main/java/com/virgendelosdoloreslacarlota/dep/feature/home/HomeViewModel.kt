package com.virgendelosdoloreslacarlota.dep.feature.home

import androidx.lifecycle.viewModelScope
import com.virgendelosdoloreslacarlota.dep.base.BaseUseCase
import com.virgendelosdoloreslacarlota.dep.base.BaseViewModel
import com.virgendelosdoloreslacarlota.dep.usecase.GetHomeUseCase
import com.virgendelosdoloreslacarlota.domain.translations.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getHomeUseCase: GetHomeUseCase,
    translationRepository: TranslationRepository
) : BaseViewModel<HomeInterfaces.Event, HomeInterfaces.State, HomeInterfaces.Effect>(
    translationRepository
) {
    override fun createInitialState(): HomeInterfaces.State {
        return HomeInterfaces.State(
            HomeInterfaces.LoadingScreenState.Idle
        )
    }

    override fun handleEvent(event: HomeInterfaces.Event) {
        when (event) {
            HomeInterfaces.Event.LoadHome -> getHomeUseCase(
                BaseUseCase.None(), viewModelScope
            ) { result ->
                if (result.isSuccess) {
                    setState {
                        HomeInterfaces.State(
                            HomeInterfaces.LoadingScreenState.Success(result.getOrThrow())
                        )
                    }
                }
            }
            is HomeInterfaces.Event.NavigateToDeepLink -> {}
            HomeInterfaces.Event.NavigateToSettings -> {}
        }
    }
}