package com.virgendelosdoloreslacarlota.dep.feature.splash

import androidx.lifecycle.viewModelScope
import com.virgendelosdoloreslacarlota.dep.base.BaseUseCase
import com.virgendelosdoloreslacarlota.dep.base.BaseViewModel
import com.virgendelosdoloreslacarlota.dep.usecase.UpdateTranslationsUseCase
import com.virgendelosdoloreslacarlota.domain.translations.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val translationRepository: TranslationRepository
) : BaseViewModel<
        SplashInterfaces.Event, SplashInterfaces.State, SplashInterfaces.Effect
        >(translationRepository) {
    override fun createInitialState(): SplashInterfaces.State = SplashInterfaces.State.Loading

    override fun handleEvent(event: SplashInterfaces.Event) {
        when (event) {
            SplashInterfaces.Event.LoadData -> {
                setState { SplashInterfaces.State.Loading }
                viewModelScope.launch {
                    try {
                        translationRepository.getTranslations()
                        setState { SplashInterfaces.State.Success }
                    } catch (e: Exception) {
                        setState { SplashInterfaces.State.Error(e) }
                    }
                }
            }
        }
    }
}