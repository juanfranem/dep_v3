package com.virgendelosdoloreslacarlota.dep.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.virgendelosdoloreslacarlota.dep.feature.main.MainActivity

abstract class BaseFragment<STATE : UiState, EFFECT : UiEffect, VM : BaseViewModel<
        out UiEvent, STATE, EFFECT>, T : ViewBinding>(
    private val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> T
) :
    Fragment() {

    private var _binding: T? = null
    val binding: T get() = _binding!!

    // Make it open, so it can be overridden in child fragments
    open fun T.initialize() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflateMethod.invoke(inflater, container, false)

        // Calling the extension function
        binding.initialize()

        return binding.root
    }

    fun setToolbarTitle(title: String, subtitle: String? = null) {
        (requireActivity() as MainActivity).setToolbarTitle(title, subtitle)
    }

    fun openUrl(url: String) {
        (requireActivity() as MainActivity).openUrl(url)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.effect.collect {
                handleEffect(it)
            }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                handleState(it)
            }
        }
        viewCreated()
    }

    abstract fun viewCreated()
    abstract fun handleState(state: STATE)
    abstract fun handleEffect(effect: EFFECT)
}