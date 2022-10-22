package com.virgendelosdoloreslacarlota.dep.feature.settings.list

import android.net.Uri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.virgendelosdoloreslacarlota.dep.R
import com.virgendelosdoloreslacarlota.dep.adapters.LegalAdapter
import com.virgendelosdoloreslacarlota.dep.base.BaseFragment
import com.virgendelosdoloreslacarlota.dep.base.OnItemClickInterface
import com.virgendelosdoloreslacarlota.dep.databinding.FragmentSettingsListBinding
import com.virgendelosdoloreslacarlota.dep.helper.showSnackBarErrorMessage
import com.virgendelosdoloreslacarlota.domain.legal.Legal
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsListFragment : BaseFragment<SettingsListInterfaces.State,
        SettingsListInterfaces.Effect, SettingsListViewModel, FragmentSettingsListBinding>
    (FragmentSettingsListBinding::inflate) {

    override val viewModel: SettingsListViewModel by viewModels()

    private val adapter by lazy {
        LegalAdapter(object : OnItemClickInterface<Legal> {
            override fun onClick(item: Legal) {
                findNavController().navigate(Uri.parse(item.url))
            }
        })
    }

    override fun viewCreated() {
        binding.items.adapter = adapter
        viewModel.setEvent(SettingsListInterfaces.Event.LoadData)
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle(viewModel.getTranslation(getString(R.string.legals_title)))
    }

    override fun handleState(state: SettingsListInterfaces.State) {
        when (state.currentDataState) {
            SettingsListInterfaces.LoadingScreenState.Idle -> {
                binding.items.isVisible = false
                binding.loading.isVisible = true
            }
            SettingsListInterfaces.LoadingScreenState.Loading -> {
                binding.items.isVisible = false
                binding.loading.isVisible = true
            }
            is SettingsListInterfaces.LoadingScreenState.Success -> {
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

    override fun handleEffect(effect: SettingsListInterfaces.Effect) {

    }
}