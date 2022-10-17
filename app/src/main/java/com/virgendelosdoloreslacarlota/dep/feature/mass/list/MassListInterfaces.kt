package com.virgendelosdoloreslacarlota.dep.feature.mass.list

import androidx.paging.PagingData
import com.virgendelosdoloreslacarlota.dep.base.UiEffect
import com.virgendelosdoloreslacarlota.dep.base.UiEvent
import com.virgendelosdoloreslacarlota.dep.base.UiState
import com.virgendelosdoloreslacarlota.domain.mass.Mass
import kotlinx.coroutines.flow.Flow

class MassListInterfaces {

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
            val massPagingData: Flow<PagingData<Mass>>
        ): LoadingScreenState()
    }
}