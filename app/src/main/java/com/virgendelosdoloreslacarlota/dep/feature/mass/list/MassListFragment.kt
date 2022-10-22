package com.virgendelosdoloreslacarlota.dep.feature.mass.list

import android.net.Uri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.virgendelosdoloreslacarlota.dep.R
import com.virgendelosdoloreslacarlota.dep.adapters.MassPagingAdapter
import com.virgendelosdoloreslacarlota.dep.base.BaseFragment
import com.virgendelosdoloreslacarlota.dep.base.OnItemClickInterface
import com.virgendelosdoloreslacarlota.dep.databinding.FragmentMassListBinding
import com.virgendelosdoloreslacarlota.dep.helper.showSnackBarErrorMessage
import com.virgendelosdoloreslacarlota.domain.mass.Mass
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MassListFragment : BaseFragment<MassListInterfaces.State,
        MassListInterfaces.Effect, MassListViewModel, FragmentMassListBinding>
    (FragmentMassListBinding::inflate) {

    override val viewModel: MassListViewModel by viewModels()

    private val adapter by lazy {
        MassPagingAdapter(object : OnItemClickInterface<Mass> {
            override fun onClick(item: Mass) {
                findNavController().navigate(Uri.parse(item.url))
            }
        })
    }

    override fun viewCreated() {
        binding.items.adapter = adapter
        viewModel.setEvent(MassListInterfaces.Event.LoadData)
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle(viewModel.getTranslation(getString(R.string.masses_title)),
            viewModel.getTranslation(getString(R.string.mass_description)))
    }

    override fun handleState(state: MassListInterfaces.State) {
        when (state.currentDataState) {
            MassListInterfaces.LoadingScreenState.Idle -> {
                binding.items.isVisible = false
                binding.loading.isVisible = true
            }
            MassListInterfaces.LoadingScreenState.Loading -> {
                binding.items.isVisible = false
                binding.loading.isVisible = true
            }
            is MassListInterfaces.LoadingScreenState.Success -> {
                binding.items.isVisible = true
                binding.loading.isVisible = false
                viewLifecycleOwner.lifecycleScope.launch {
                    state.currentDataState.massPagingData.collectLatest { pagingData ->
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

    override fun handleEffect(effect: MassListInterfaces.Effect) {

    }
}