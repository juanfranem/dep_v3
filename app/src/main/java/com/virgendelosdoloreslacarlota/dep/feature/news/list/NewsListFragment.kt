package com.virgendelosdoloreslacarlota.dep.feature.news.list

import android.net.Uri
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.virgendelosdoloreslacarlota.dep.R
import com.virgendelosdoloreslacarlota.dep.Tracker
import com.virgendelosdoloreslacarlota.dep.adapters.NewsPagingAdapter
import com.virgendelosdoloreslacarlota.dep.analytics.ScreenEvent
import com.virgendelosdoloreslacarlota.dep.analytics.UserEvents
import com.virgendelosdoloreslacarlota.dep.base.BaseFragment
import com.virgendelosdoloreslacarlota.dep.base.OnItemClickInterface
import com.virgendelosdoloreslacarlota.dep.databinding.FragmentNewsListBinding
import com.virgendelosdoloreslacarlota.dep.helper.showSnackBarErrorMessage
import com.virgendelosdoloreslacarlota.domain.news.News
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NewsListFragment : BaseFragment<NewsListInterfaces.State,
        NewsListInterfaces.Effect, NewsListViewModel, FragmentNewsListBinding>
    (FragmentNewsListBinding::inflate) {

    override val viewModel: NewsListViewModel by viewModels()

    @Inject
    lateinit var tracker: Tracker


    private val adapter by lazy {
        NewsPagingAdapter(object : OnItemClickInterface<News> {
            override fun onClick(item: News) {
                tracker.setEvent(UserEvents.ItemTap(item.url))
                findNavController().navigate(Uri.parse(item.url))
            }
        })
    }

    override fun viewCreated() {
        binding.items.adapter = adapter
        viewModel.setEvent(NewsListInterfaces.Event.LoadData)
    }

    override fun onResume() {
        super.onResume()
        setToolbarTitle(viewModel.getTranslation(getString(R.string.news_title)),
            viewModel.getTranslation(getString(R.string.news_description)))
        tracker.setScreen(ScreenEvent.NewsList)
    }

    override fun handleState(state: NewsListInterfaces.State) {
        when (state.currentDataState) {
            NewsListInterfaces.LoadingScreenState.Idle -> {
                binding.items.isVisible = false
                binding.loading.isVisible = true
            }
            NewsListInterfaces.LoadingScreenState.Loading -> {
                binding.items.isVisible = false
                binding.loading.isVisible = true
            }
            is NewsListInterfaces.LoadingScreenState.Success -> {
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

    override fun handleEffect(effect: NewsListInterfaces.Effect) {

    }
}