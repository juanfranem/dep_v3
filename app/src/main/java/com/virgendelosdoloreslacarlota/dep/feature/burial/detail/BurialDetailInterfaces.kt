package com.virgendelosdoloreslacarlota.dep.feature.burial.detail

import com.virgendelosdoloreslacarlota.dep.base.UiEffect
import com.virgendelosdoloreslacarlota.dep.base.UiEvent
import com.virgendelosdoloreslacarlota.dep.base.UiState
import com.virgendelosdoloreslacarlota.domain.burial.Burial

class BurialDetailInterfaces {

    sealed class Effect: UiEffect {

    }

    sealed class Event: UiEvent {
        data class LoadData(val slug: String): Event()
    }

    data class State(
        val currentDataState: LoadingScreenState
    ): UiState

    sealed class LoadingScreenState {
        object Idle: LoadingScreenState()
        object Loading: LoadingScreenState()
        data class Success(
            val burial: Burial
        ): LoadingScreenState()
    }

}