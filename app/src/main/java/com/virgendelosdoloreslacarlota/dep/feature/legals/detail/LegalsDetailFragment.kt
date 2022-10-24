package com.virgendelosdoloreslacarlota.dep.feature.legals.detail

import android.graphics.Color
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.virgendelosdoloreslacarlota.dep.R
import com.virgendelosdoloreslacarlota.dep.Tracker
import com.virgendelosdoloreslacarlota.dep.analytics.ScreenEvent
import com.virgendelosdoloreslacarlota.dep.base.BaseFragment
import com.virgendelosdoloreslacarlota.dep.databinding.FragmentSettingsDetailBinding
import com.virgendelosdoloreslacarlota.dep.helper.showSnackBarErrorMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LegalsDetailFragment : BaseFragment<LegalsDetailInterfaces.State,
        LegalsDetailInterfaces.Effect, LegalsDetailViewModel, FragmentSettingsDetailBinding>
    (FragmentSettingsDetailBinding::inflate) {
    override val viewModel: LegalsDetailViewModel by viewModels()
    private val args: LegalsDetailFragmentArgs by navArgs()

    @Inject
    lateinit var tracker: Tracker

    override fun viewCreated() {
        viewModel.setEvent(LegalsDetailInterfaces.Event.LoadData(args.slug))

    }

    override fun handleState(state: LegalsDetailInterfaces.State) {
        when (state.currentDataState) {
            LegalsDetailInterfaces.LoadingScreenState.Idle -> {
                binding.screen.isVisible = false
                binding.loading.isVisible = true
            }
            LegalsDetailInterfaces.LoadingScreenState.Loading -> {
                binding.screen.isVisible = false
                binding.loading.isVisible = true
            }
            is LegalsDetailInterfaces.LoadingScreenState.Success -> {
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
            else -> {
                binding.loading.isVisible = false
                binding.root.showSnackBarErrorMessage(getString(R.string.error_message))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        tracker.setScreen(ScreenEvent.LegalDetail(args.slug))
    }

    override fun handleEffect(effect: LegalsDetailInterfaces.Effect) {

    }
}