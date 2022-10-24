package com.virgendelosdoloreslacarlota.dep.feature.legals.list

import android.net.Uri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.virgendelosdoloreslacarlota.dep.R
import com.virgendelosdoloreslacarlota.dep.Tracker
import com.virgendelosdoloreslacarlota.dep.adapters.LegalAdapter
import com.virgendelosdoloreslacarlota.dep.analytics.ScreenEvent
import com.virgendelosdoloreslacarlota.dep.analytics.UserEvents
import com.virgendelosdoloreslacarlota.dep.base.BaseFragment
import com.virgendelosdoloreslacarlota.dep.base.OnItemClickInterface
import com.virgendelosdoloreslacarlota.dep.databinding.FragmentSettingsListBinding
import com.virgendelosdoloreslacarlota.dep.helper.showSnackBarErrorMessage
import com.virgendelosdoloreslacarlota.domain.legal.Legal
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LegalsListFragment : BaseFragment<LegalsListInterfaces.State,
        LegalsListInterfaces.Effect, LegalsListViewModel, FragmentSettingsListBinding>
    (FragmentSettingsListBinding::inflate) {

    @Inject
    lateinit var tracker: Tracker


    override val viewModel: LegalsListViewModel by viewModels()

    private val adapter by lazy {
        LegalAdapter(object : OnItemClickInterface<Legal> {
            override fun onClick(item: Legal) {
                tracker.setEvent(UserEvents.ItemTap(item.url))
                findNavController().navigate(Uri.parse(item.url), NavOptions.Builder()
                    .setEnterAnim(R.anim.slide_in)
                    .setExitAnim(R.anim.slide_out)
                    .setPopEnterAnim(R.anim.slide_in)
                    .setPopExitAnim(R.anim.slide_out)
                    .build()
                )
            }
        })
    }

    override fun viewCreated() {
        binding.items.adapter = adapter
        viewModel.setEvent(LegalsListInterfaces.Event.LoadData)
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle(viewModel.getTranslation(getString(R.string.legals_title)))
        tracker.setScreen(ScreenEvent.LegalList)
    }

    override fun handleState(state: LegalsListInterfaces.State) {
        when (state.currentDataState) {
            LegalsListInterfaces.LoadingScreenState.Idle -> {
                binding.items.isVisible = false
                binding.loading.isVisible = true
            }
            LegalsListInterfaces.LoadingScreenState.Loading -> {
                binding.items.isVisible = false
                binding.loading.isVisible = true
            }
            is LegalsListInterfaces.LoadingScreenState.Success -> {
                binding.items.isVisible = true
                binding.loading.isVisible = false
                adapter.submitList(state.currentDataState.legalList)
            }
            else -> {
                binding.loading.isVisible = false
                binding.root.showSnackBarErrorMessage(getString(R.string.error_message))
            }
        }
    }

    override fun handleEffect(effect: LegalsListInterfaces.Effect) {

    }
}