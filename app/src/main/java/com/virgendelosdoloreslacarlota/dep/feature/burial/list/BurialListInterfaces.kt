package com.virgendelosdoloreslacarlota.dep.feature.burial.list

import androidx.paging.PagingData
import com.virgendelosdoloreslacarlota.dep.base.UiEffect
import com.virgendelosdoloreslacarlota.dep.base.UiEvent
import com.virgendelosdoloreslacarlota.dep.base.UiState
import com.virgendelosdoloreslacarlota.domain.burial.Burial
import kotlinx.coroutines.flow.Flow

class BurialListInterfaces {

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
            val burialPagingData: Flow<PagingData<Burial>>
        ): LoadingScreenState()
    }
}