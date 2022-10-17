package com.virgendelosdoloreslacarlota.dep.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import coil.load
import com.virgendelosdoloreslacarlota.dep.base.AbstractPagingAdapter
import com.virgendelosdoloreslacarlota.dep.base.BaseViewHolder
import com.virgendelosdoloreslacarlota.dep.base.OnItemClickInterface
import com.virgendelosdoloreslacarlota.dep.databinding.ItemEventBinding
import com.virgendelosdoloreslacarlota.dep.databinding.ItemNewsLayoutBinding
import com.virgendelosdoloreslacarlota.domain.burial.Burial
import com.virgendelosdoloreslacarlota.domain.news.News

class NewsPagingAdapter(
    private val onItemClickInterface: OnItemClickInterface<News>
) : AbstractPagingAdapter<News, ItemNewsLayoutBinding, NewsPagingAdapter.NewsViewHolder>(
    onItemClickInterface
) {

    override val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> ItemNewsLayoutBinding
        get() = ItemNewsLayoutBinding::inflate

    override fun createViewHolder(view: ItemNewsLayoutBinding): NewsViewHolder =
        NewsViewHolder(view, onItemClickInterface)

    inner class NewsViewHolder(
        private val binding: ItemNewsLayoutBinding,
        private val onItemClickInterface: OnItemClickInterface<News>
    ) : BaseViewHolder<News, ItemNewsLayoutBinding>(binding) {
        override fun bind(data: News) {
            binding.image.isVisible = !data.image.isNullOrBlank()
            binding.image.load(data.image)
            binding.title.text = data.title
            binding.root.setOnClickListener { onItemClickInterface.onClick(data) }
        }
    }
}