package com.virgendelosdoloreslacarlota.dep.feature.splash

import com.virgendelosdoloreslacarlota.dep.base.UiEffect
import com.virgendelosdoloreslacarlota.dep.base.UiEvent
import com.virgendelosdoloreslacarlota.dep.base.UiState

class SplashInterfaces {

    sealed class Effect: UiEffect {}

    sealed class Event: UiEvent {
        object LoadData: Event()
    }

    sealed class State: UiState {
        object Loading: State()
        object Success: State()
        data class Error(val exception: Exception): State()
    }

}