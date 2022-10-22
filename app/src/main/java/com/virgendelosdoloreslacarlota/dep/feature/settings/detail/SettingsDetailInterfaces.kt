package com.virgendelosdoloreslacarlota.dep.feature.settings.detail

import com.virgendelosdoloreslacarlota.dep.base.UiEffect
import com.virgendelosdoloreslacarlota.dep.base.UiEvent
import com.virgendelosdoloreslacarlota.dep.base.UiState
import com.virgendelosdoloreslacarlota.dep.feature.burial.detail.BurialDetailInterfaces
import com.virgendelosdoloreslacarlota.domain.legal.Legal
import com.virgendelosdoloreslacarlota.domain.news.News

class SettingsDetailInterfaces {

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
            val legal: Legal
        ): LoadingScreenState()
        object Error: LoadingScreenState()
    }

}