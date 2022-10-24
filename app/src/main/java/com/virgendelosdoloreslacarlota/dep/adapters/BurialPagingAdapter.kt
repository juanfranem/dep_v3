package com.virgendelosdoloreslacarlota.dep.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.virgendelosdoloreslacarlota.dep.base.AbstractPagingAdapter
import com.virgendelosdoloreslacarlota.dep.base.BaseViewHolder
import com.virgendelosdoloreslacarlota.dep.base.OnItemClickInterface
import com.virgendelosdoloreslacarlota.dep.databinding.ItemEventBinding
import com.virgendelosdoloreslacarlota.dep.databinding.ItemEventSquareBinding
import com.virgendelosdoloreslacarlota.domain.burial.Burial

class BurialPagingAdapter(
    private val onItemClickInterface: OnItemClickInterface<Burial>
) : AbstractPagingAdapter<Burial, ItemEventSquareBinding, BurialPagingAdapter.BurialViewHolder>(
    onItemClickInterface
) {

    override val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> ItemEventSquareBinding
        get() = ItemEventSquareBinding::inflate

    override fun createViewHolder(view: ItemEventSquareBinding): BurialViewHolder =
        BurialViewHolder(view, onItemClickInterface)

    inner class BurialViewHolder(
        private val binding: ItemEventSquareBinding,
        private val onItemClickInterface: OnItemClickInterface<Burial>
    ) : BaseViewHolder<Burial, ItemEventSquareBinding>(binding) {
        override fun bind(data: Burial) {
            binding.root.setOnClickListener { onItemClickInterface.onClick(data) }
            binding.image.load(data.image)
            binding.title.text = data.deceased.fullName
        }
    }
}