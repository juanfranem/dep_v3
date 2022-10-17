package com.virgendelosdoloreslacarlota.dep.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.virgendelosdoloreslacarlota.dep.base.AbstractAdapter
import com.virgendelosdoloreslacarlota.dep.base.BaseViewHolder
import com.virgendelosdoloreslacarlota.dep.base.OnItemClickInterface
import com.virgendelosdoloreslacarlota.dep.databinding.ItemNewsLayoutBinding
import com.virgendelosdoloreslacarlota.domain.legal.Legal
import com.virgendelosdoloreslacarlota.domain.news.News

class LegalAdapter(
    private val onItemClickInterface: OnItemClickInterface<Legal>
): AbstractAdapter<Legal, ItemNewsLayoutBinding, LegalAdapter.LegalViewHolder>(onItemClickInterface) {

    override val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> ItemNewsLayoutBinding
        get() = ItemNewsLayoutBinding::inflate

    override fun createViewHolder(view: ItemNewsLayoutBinding): LegalViewHolder =
        LegalViewHolder(view, onItemClickInterface)

    inner class LegalViewHolder(
        private val binding: ItemNewsLayoutBinding,
        private val onItemClickInterface: OnItemClickInterface<Legal>
    ): BaseViewHolder<Legal, ItemNewsLayoutBinding>(binding) {
        override fun bind(data: Legal) {
            binding.image.isVisible = false
            binding.title.text = data.title
            binding.root.setOnClickListener { onItemClickInterface.onClick(data) }
        }
    }


}