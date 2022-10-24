package com.virgendelosdoloreslacarlota.dep.feature.settings

import com.virgendelosdoloreslacarlota.dep.base.UiEffect
import com.virgendelosdoloreslacarlota.dep.base.UiEvent
import com.virgendelosdoloreslacarlota.dep.base.UiState

class SettingsInterfaces {

    sealed class Effect: UiEffect {

    }

    sealed class Event: UiEvent {
        object LoadData: Event()
    }

    data class State(
        val currentState: LoadingScreenState
    ): UiState

    sealed class LoadingScreenState {
        object Idle: LoadingScreenState()
        object Success: LoadingScreenState()
    }

}