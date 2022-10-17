package com.virgendelosdoloreslacarlota.dep.feature.news.detail

import androidx.lifecycle.viewModelScope
import com.virgendelosdoloreslacarlota.dep.base.BaseViewModel
import com.virgendelosdoloreslacarlota.dep.usecase.GetNewsBySlugUseCase
import com.virgendelosdoloreslacarlota.domain.translations.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsDetailViewModel @Inject constructor(
    private val getNewsBySlugUseCase: GetNewsBySlugUseCase,
    translationRepository: TranslationRepository) :
    BaseViewModel<NewsDetailInterfaces.Event,
            NewsDetailInterfaces.State, NewsDetailInterfaces.Effect>(
        translationRepository
    ) {
    override fun createInitialState(): NewsDetailInterfaces.State {
        return NewsDetailInterfaces.State(
            NewsDetailInterfaces.LoadingScreenState.Idle
        )
    }

    override fun handleEvent(event: NewsDetailInterfaces.Event) {
        when (event) {
            is NewsDetailInterfaces.Event.LoadData -> {
                getNewsBySlugUseCase(event.slug, viewModelScope) { result ->
                    if (result.isSuccess) {
                        setState {
                            NewsDetailInterfaces.State(
                                NewsDetailInterfaces.LoadingScreenState.Success(result.getOrThrow())
                            )
                        }
                    }
                }
            }
        }
    }
}