package com.virgendelosdoloreslacarlota.dep.feature.home

import com.virgendelosdoloreslacarlota.dep.base.UiEffect
import com.virgendelosdoloreslacarlota.dep.base.UiEvent
import com.virgendelosdoloreslacarlota.dep.base.UiState
import com.virgendelosdoloreslacarlota.domain.burial.Burial
import com.virgendelosdoloreslacarlota.domain.home.Home
import com.virgendelosdoloreslacarlota.domain.mass.Mass
import com.virgendelosdoloreslacarlota.domain.news.News
import com.virgendelosdoloreslacarlota.domain.sponsor.Sponsor

class HomeInterfaces {

    sealed class Event: UiEvent {
        object LoadHome: Event()
        object NavigateToSettings: Event()
        data class NavigateToDeepLink(val url: String): Event()
    }

    data class State(
        val currentDataState: LoadingScreenState
    ): UiState

    sealed class LoadingScreenState {
        object Idle: LoadingScreenState()
        object Loading: LoadingScreenState()
        data class Success(
            val home: Home
        ): LoadingScreenState()
    }

    sealed class Effect: UiEffect {

    }

}