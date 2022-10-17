package com.virgendelosdoloreslacarlota.dep.feature.news.list

import androidx.paging.PagingData
import com.virgendelosdoloreslacarlota.dep.base.UiEffect
import com.virgendelosdoloreslacarlota.dep.base.UiEvent
import com.virgendelosdoloreslacarlota.dep.base.UiState
import com.virgendelosdoloreslacarlota.domain.news.News
import kotlinx.coroutines.flow.Flow

class NewsListInterfaces {

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
            val massPagingData: Flow<PagingData<News>>
        ): LoadingScreenState()
    }
}