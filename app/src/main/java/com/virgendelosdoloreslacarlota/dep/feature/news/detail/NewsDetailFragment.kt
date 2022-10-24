package com.virgendelosdoloreslacarlota.dep.feature.news.detail

import android.graphics.Color
import android.os.Handler
import android.os.Looper
import android.view.ViewTreeObserver.OnScrollChangedListener
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import coil.load
import com.virgendelosdoloreslacarlota.dep.R
import com.virgendelosdoloreslacarlota.dep.Tracker
import com.virgendelosdoloreslacarlota.dep.analytics.ScreenEvent
import com.virgendelosdoloreslacarlota.dep.base.BaseFragment
import com.virgendelosdoloreslacarlota.dep.databinding.FragmentNewsDetailBinding
import com.virgendelosdoloreslacarlota.dep.helper.showSnackBarErrorMessage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NewsDetailFragment : BaseFragment<NewsDetailInterfaces.State,
        NewsDetailInterfaces.Effect, NewsDetailViewModel, FragmentNewsDetailBinding>
    (FragmentNewsDetailBinding::inflate) {
    override val viewModel: NewsDetailViewModel by viewModels()
    private val args: NewsDetailFragmentArgs by navArgs()

    @Inject
    lateinit var tracker: Tracker

    override fun viewCreated() {
        viewModel.setEvent(NewsDetailInterfaces.Event.LoadData(args.slug))
    }


    override fun handleState(state: NewsDetailInterfaces.State) {
        when (state.currentDataState) {
            NewsDetailInterfaces.LoadingScreenState.Idle -> {
                binding.image.isVisible = false
                binding.shareView.isVisible = false
                binding.loading.isVisible = true
            }
            NewsDetailInterfaces.LoadingScreenState.Loading -> {
                binding.image.isVisible = false
                binding.shareView.isVisible = false
                binding.loading.isVisible = true
            }
            is NewsDetailInterfaces.LoadingScreenState.Success -> {
                binding.image.isVisible = true
                binding.shareView.isVisible = true
                binding.loading.isVisible = false
                binding.image.load(state.currentDataState.news.image)
                setToolbarTitle(state.currentDataState.news.title)
                binding.shareView.setOnSaveClickListener(requireActivity(),
                    state.currentDataState.news.image)
                binding.title.text = state.currentDataState.news.title
                binding.content.setBackgroundColor(Color.TRANSPARENT)
                binding.content.isScrollContainer = false
                binding.content.loadData(
                    state.currentDataState.news.body.orEmpty(),
                    "text/html", "utf-8"
                )
                binding.shareView.setOnShareClickListener(
                    state.currentDataState.news.url,
                    ""
                )
            }
            else -> {
                binding.loading.isVisible = false
                binding.root.showSnackBarErrorMessage(getString(R.string.error_message))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        tracker.setScreen(ScreenEvent.NewsDetail(args.slug))
    }

    override fun handleEffect(effect: NewsDetailInterfaces.Effect) {

    }
}