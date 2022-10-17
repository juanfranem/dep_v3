package com.virgendelosdoloreslacarlota.dep.feature.news.list

import androidx.lifecycle.viewModelScope
import com.virgendelosdoloreslacarlota.dep.base.BaseUseCase
import com.virgendelosdoloreslacarlota.dep.base.BaseViewModel
import com.virgendelosdoloreslacarlota.dep.usecase.GetNewsListUseCase
import com.virgendelosdoloreslacarlota.domain.translations.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsListViewModel @Inject constructor(
    private val getNewsListUseCase: GetNewsListUseCase,
    translationRepository: TranslationRepository
) :
    BaseViewModel<NewsListInterfaces.Event,
            NewsListInterfaces.State, NewsListInterfaces.Effect>(
        translationRepository
    ) {
    override fun createInitialState(): NewsListInterfaces.State {
        return NewsListInterfaces.State(
            NewsListInterfaces.LoadingScreenState.Idle
        )
    }

    override fun handleEvent(event: NewsListInterfaces.Event) {
        when (event) {
            NewsListInterfaces.Event.LoadData -> {
                getNewsListUseCase(BaseUseCase.None(), viewModelScope) { result ->
                    if (result.isSuccess) {
                        setState {
                            NewsListInterfaces.State(
                                NewsListInterfaces.LoadingScreenState.Success(result.getOrThrow())
                            )
                        }
                    }
                }
            }
        }
    }
}