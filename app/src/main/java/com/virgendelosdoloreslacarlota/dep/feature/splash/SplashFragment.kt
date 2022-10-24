package com.virgendelosdoloreslacarlota.dep.feature.splash

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.virgendelosdoloreslacarlota.dep.R
import com.virgendelosdoloreslacarlota.dep.base.BaseFragment
import com.virgendelosdoloreslacarlota.dep.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<
        SplashInterfaces.State, SplashInterfaces.Effect, SplashViewModel, FragmentSplashBinding
        >(FragmentSplashBinding::inflate) {
    override val viewModel: SplashViewModel by viewModels()

    override fun viewCreated() {
        viewModel.handleEvent(SplashInterfaces.Event.LoadData)
    }

    override fun handleState(state: SplashInterfaces.State) {
        when (state) {
            is SplashInterfaces.State.Error -> {
                binding.errorMessage.text = getString(R.string.error_message)
                binding.loader.isVisible = false
                binding.errorMessage.isVisible = true
                binding.tryButton.isVisible = true
                binding.tryButton.setOnClickListener {
                    viewModel.handleEvent(SplashInterfaces.Event.LoadData)
                }
            }
            SplashInterfaces.State.Loading -> {
                binding.loader.isVisible = true
                binding.errorMessage.isVisible = false
                binding.tryButton.isVisible = false
            }
            SplashInterfaces.State.Success -> {
                binding.loader.isVisible = false
                binding.errorMessage.isVisible = false
                binding.tryButton.isVisible = false
                findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToMainGraph()
                )
            }
        }
    }

    override fun handleEffect(effect: SplashInterfaces.Effect) {

    }
}