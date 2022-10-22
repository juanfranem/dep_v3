package com.virgendelosdoloreslacarlota.dep.feature.settings.list

import androidx.lifecycle.viewModelScope
import com.virgendelosdoloreslacarlota.dep.base.BaseUseCase
import com.virgendelosdoloreslacarlota.dep.base.BaseViewModel
import com.virgendelosdoloreslacarlota.dep.feature.settings.detail.SettingsDetailInterfaces
import com.virgendelosdoloreslacarlota.dep.usecase.GetLegalListUseCase
import com.virgendelosdoloreslacarlota.domain.translations.TranslationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsListViewModel @Inject constructor(
    private val getLegalListUseCase: GetLegalListUseCase,
    translationRepository: TranslationRepository
) :
    BaseViewModel<SettingsListInterfaces.Event,
            SettingsListInterfaces.State, SettingsListInterfaces.Effect>(
        translationRepository
    ) {
    override fun createInitialState(): SettingsListInterfaces.State {
        return SettingsListInterfaces.State(
            SettingsListInterfaces.LoadingScreenState.Idle
        )
    }

    override fun handleEvent(event: SettingsListInterfaces.Event) {
        when (event) {
            SettingsListInterfaces.Event.LoadData -> {
                getLegalListUseCase(BaseUseCase.None(), viewModelScope) { result ->
                    if (result.isSuccess) {
                        setState {
                            SettingsListInterfaces.State(
                                SettingsListInterfaces.LoadingScreenState.Success(result.getOrThrow())
                            )
                        }
                    } else {
                        setState {
                            SettingsListInterfaces.State(
                                SettingsListInterfaces.LoadingScreenState.Error
                            )
                        }
                    }
                }
            }
        }
    }
}