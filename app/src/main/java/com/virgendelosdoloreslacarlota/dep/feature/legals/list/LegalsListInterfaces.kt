package com.virgendelosdoloreslacarlota.dep.feature.legals.list

import com.virgendelosdoloreslacarlota.dep.base.UiEffect
import com.virgendelosdoloreslacarlota.dep.base.UiEvent
import com.virgendelosdoloreslacarlota.dep.base.UiState
import com.virgendelosdoloreslacarlota.domain.legal.Legal

class LegalsListInterfaces {

    sealed class Effect: UiEffect {

    }

    sealed class Event: UiEvent {
        object LoadData: Event()
    }

    data class State(
        val currentDataState: LoadingScreenState
    ): UiState

    sealed class LoadingScreenState {
        object Idle: LoadingScreenState()
        object Loading: LoadingScreenState()
        data class Success(
            val legalList: List<Legal>
        ): LoadingScreenState()
        object Error: LoadingScreenState()
    }
}