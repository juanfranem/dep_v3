package com.virgendelosdoloreslacarlota.dep.feature.settings

import com.virgendelosdoloreslacarlota.dep.base.BaseViewModel
import com.virgendelosdoloreslacarlota.domain.translations.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    translationRepository: TranslationRepository
) : BaseViewModel<SettingsInterfaces.Event, SettingsInterfaces.State, SettingsInterfaces.Effect>
    (translationRepository) {
    override fun createInitialState(): SettingsInterfaces.State {
        return SettingsInterfaces.State(
            SettingsInterfaces.LoadingScreenState.Idle
        )
    }

    override fun handleEvent(event: SettingsInterfaces.Event) {
        when (event) {
            SettingsInterfaces.Event.LoadData -> {
                setState {
                    SettingsInterfaces.State(
                        SettingsInterfaces.LoadingScreenState.Success
                    )
                }
            }
        }
    }
}