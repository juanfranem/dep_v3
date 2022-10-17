package com.virgendelosdoloreslacarlota.dep.feature.home

import android.net.Uri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.virgendelosdoloreslacarlota.dep.R
import com.virgendelosdoloreslacarlota.dep.adapters.BurialAdapter
import com.virgendelosdoloreslacarlota.dep.adapters.MassAdapter
import com.virgendelosdoloreslacarlota.dep.adapters.NewsAdapter
import com.virgendelosdoloreslacarlota.dep.adapters.SponsorAdapter
import com.virgendelosdoloreslacarlota.dep.base.*
import com.virgendelosdoloreslacarlota.dep.databinding.FragmentHomeBinding
import com.virgendelosdoloreslacarlota.domain.burial.Burial
import com.virgendelosdoloreslacarlota.domain.mass.Mass
import com.virgendelosdoloreslacarlota.domain.news.News
import com.virgendelosdoloreslacarlota.domain.sponsor.Sponsor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<
        HomeInterfaces.State, HomeInterfaces.Effect,
        HomeViewModel, FragmentHomeBinding>(FragmentHomeBinding::inflate) {

    override val viewModel: HomeViewModel by viewModels()

    private val burialAdapter: BurialAdapter by lazy {
        BurialAdapter(object : OnItemClickInterface<Burial> {
            override fun onClick(item: Burial) {
                findNavController().navigate(Uri.parse(item.url))
            }
        })
    }

    private val massAdapter: MassAdapter by lazy {
        MassAdapter(object : OnItemClickInterface<Mass> {
            override fun onClick(item: Mass) {
                findNavController().navigate(Uri.parse(item.url))
            }
        })
    }

    private val newsAdapter: NewsAdapter by lazy {
        NewsAdapter(object : OnItemClickInterface<News> {
            override fun onClick(item: News) {
                findNavController().navigate(Uri.parse(item.url))
            }
        })
    }

    private val sponsorAdapter: SponsorAdapter by lazy {
        SponsorAdapter(object : OnItemClickInterface<Sponsor> {
            override fun onClick(item: Sponsor) {
                item.url?.let { openUrl(it) }
            }
        })
    }

    override fun viewCreated() {
        binding.burialView.setAdapter(burialAdapter)
        binding.massView.setAdapter(massAdapter)
        binding.sponsorItems.adapter = sponsorAdapter
        binding.newsView.setLayoutManager(
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL, false
            )
        )
        binding.newsView.setAdapter(newsAdapter)
        binding.burialView.titleView.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToBurialListFragment()
            )
        }

        binding.massView.titleView.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToMassListFragment()
            )
        }

        binding.newsView.titleView.setOnClickListener {
            findNavController().navigate(
                HomeFragmentDirections.actionHomeFragmentToNewsListFragment()
            )
        }
    }

    override fun handleState(state: HomeInterfaces.State) {
        when (val data = state.currentDataState) {
            HomeInterfaces.LoadingScreenState.Idle -> binding.loading.isVisible = true
            HomeInterfaces.LoadingScreenState.Loading -> binding.loading.isVisible = true
            is HomeInterfaces.LoadingScreenState.Success -> {
                binding.loading.isVisible = false
                binding.screen.isVisible = true
                loadTranslations()
                val home = data.home
                binding.sponsorItems.isVisible = home.sponsors.isNotEmpty()
                binding.burialView.isVisibleEmptyText = home.burial.isEmpty()
                binding.massView.isVisibleEmptyText = home.mass.isEmpty()
                binding.newsView.isVisibleEmptyText = home.news.isEmpty()
                burialAdapter.submitList(home.burial)
                massAdapter.submitList(home.mass)
                newsAdapter.submitList(home.news)
                sponsorAdapter.submitList(home.sponsors)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.setEvent(HomeInterfaces.Event.LoadHome)
        setToolbarTitle(viewModel.getTranslation(getString(R.string.app_name_translate)))
    }

    private fun loadTranslations() {
        binding.burialView.titleView.setButtonText(viewModel.getTranslation(getString(R.string.view_all)))
        binding.massView.titleView.setButtonText(viewModel.getTranslation(getString(R.string.view_all)))
        binding.newsView.titleView.setButtonText(viewModel.getTranslation(getString(R.string.view_all)))

        binding.burialView.titleView.setText(viewModel.getTranslation(getString(R.string.burials_title)))
        binding.massView.titleView.setText(viewModel.getTranslation(getString(R.string.masses_title)))
        binding.newsView.titleView.setText(viewModel.getTranslation(getString(R.string.news_title)))

        binding.burialView.setEmptyText(viewModel.getTranslation(getString(R.string.burial_empty_title)))
        binding.massView.setEmptyText(viewModel.getTranslation(getString(R.string.mass_empty_title)))
        binding.newsView.setEmptyText(viewModel.getTranslation(getString(R.string.news_empty_title)))
    }

    override fun handleEffect(effect: HomeInterfaces.Effect) {

    }

}