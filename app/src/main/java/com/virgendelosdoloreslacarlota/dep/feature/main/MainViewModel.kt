package com.virgendelosdoloreslacarlota.dep.feature.main

import com.virgendelosdoloreslacarlota.dep.base.BaseViewModel
import com.virgendelosdoloreslacarlota.domain.translations.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    translationRepository: TranslationRepository
) : BaseViewModel<MainInterfaces.Event, MainInterfaces.State, MainInterfaces.Effect>(
    translationRepository
) {

    override fun createInitialState(): MainInterfaces.State {
        return MainInterfaces.State()
    }

    override fun handleEvent(event: MainInterfaces.Event) {

    }
}