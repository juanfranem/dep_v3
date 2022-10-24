package com.virgendelosdoloreslacarlota.dep.feature.burial.list

import android.net.Uri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.virgendelosdoloreslacarlota.dep.R
import com.virgendelosdoloreslacarlota.dep.Tracker
import com.virgendelosdoloreslacarlota.dep.adapters.BurialPagingAdapter
import com.virgendelosdoloreslacarlota.dep.analytics.ScreenEvent
import com.virgendelosdoloreslacarlota.dep.analytics.UserEvents
import com.virgendelosdoloreslacarlota.dep.base.BaseFragment
import com.virgendelosdoloreslacarlota.dep.base.OnItemClickInterface
import com.virgendelosdoloreslacarlota.dep.databinding.FragmentBurialListBinding
import com.virgendelosdoloreslacarlota.dep.helper.showSnackBarErrorMessage
import com.virgendelosdoloreslacarlota.domain.burial.Burial
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BurialListFragment : BaseFragment<BurialListInterfaces.State,
        BurialListInterfaces.Effect, BurialListViewModel, FragmentBurialListBinding>
    (FragmentBurialListBinding::inflate) {

    @Inject
    lateinit var tracker: Tracker

    override val viewModel: BurialListViewModel by viewModels()

    private val adapter by lazy {
        BurialPagingAdapter(object : OnItemClickInterface<Burial> {
            override fun onClick(item: Burial) {
                tracker.setEvent(UserEvents.ItemTap(item.url))
                findNavController().navigate(Uri.parse(item.url))
            }
        })
    }

    override fun viewCreated() {
        binding.items.adapter = adapter
        viewModel.setEvent(BurialListInterfaces.Event.LoadData)
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle(viewModel.getTranslation(getString(R.string.burials_title)),
            viewModel.getTranslation(getString(R.string.burial_description)))
        tracker.setScreen(ScreenEvent.BurialList)
    }

    override fun handleState(state: BurialListInterfaces.State) {
        when (state.currentDataState) {
            BurialListInterfaces.LoadingScreenState.Idle -> {
                binding.items.isVisible = false
                binding.loading.isVisible = true
            }
            BurialListInterfaces.LoadingScreenState.Loading -> {
                binding.items.isVisible = false
                binding.loading.isVisible = true
            }
            is BurialListInterfaces.LoadingScreenState.Success -> {
                binding.items.isVisible = true
                binding.loading.isVisible = false
                viewLifecycleOwner.lifecycleScope.launch {
                    state.currentDataState.burialPagingData.collectLatest { pagingData ->
                        adapter.submitData(pagingData)
                    }
                }
            }
            else -> {
                binding.loading.isVisible = false
                binding.root.showSnackBarErrorMessage(getString(R.string.error_message))
            }
        }
    }

    override fun handleEffect(effect: BurialListInterfaces.Effect) {

    }
}