package com.virgendelosdoloreslacarlota.dep.feature.settings.detail

import android.graphics.Color
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.virgendelosdoloreslacarlota.dep.base.BaseFragment
import com.virgendelosdoloreslacarlota.dep.databinding.FragmentSettingsDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsDetailFragment : BaseFragment<SettingsDetailInterfaces.State,
        SettingsDetailInterfaces.Effect, SettingsDetailViewModel, FragmentSettingsDetailBinding>
    (FragmentSettingsDetailBinding::inflate) {
    override val viewModel: SettingsDetailViewModel by viewModels()
    private val args: SettingsDetailFragmentArgs by navArgs()

    override fun viewCreated() {
        viewModel.setEvent(SettingsDetailInterfaces.Event.LoadData(args.slug))

    }

    override fun handleState(state: SettingsDetailInterfaces.State) {
        when (state.currentDataState) {
            SettingsDetailInterfaces.LoadingScreenState.Idle -> {
                binding.screen.isVisible = false
                binding.loading.isVisible = true
            }
            SettingsDetailInterfaces.LoadingScreenState.Loading -> {
                binding.screen.isVisible = false
                binding.loading.isVisible = true
            }
            is SettingsDetailInterfaces.LoadingScreenState.Success -> {
                binding.screen.isVisible = true
                binding.loading.isVisible = false
                binding.title.text = state.currentDataState.legal.title
                binding.body.isScrollContainer = true
                binding.body.loadData(
                    state.currentDataState.legal.body.orEmpty(),
                    "text/html", "utf-8"
                )
                binding.body.setBackgroundColor(Color.TRANSPARENT)
                setToolbarTitle(state.currentDataState.legal.title)
            }
        }
    }

    override fun handleEffect(effect: SettingsDetailInterfaces.Effect) {

    }
}