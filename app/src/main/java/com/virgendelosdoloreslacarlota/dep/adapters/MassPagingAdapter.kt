package com.virgendelosdoloreslacarlota.dep.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.virgendelosdoloreslacarlota.dep.base.AbstractPagingAdapter
import com.virgendelosdoloreslacarlota.dep.base.BaseViewHolder
import com.virgendelosdoloreslacarlota.dep.base.OnItemClickInterface
import com.virgendelosdoloreslacarlota.dep.databinding.ItemEventBinding
import com.virgendelosdoloreslacarlota.domain.mass.Mass

class MassPagingAdapter(
    private val onItemClickInterface: OnItemClickInterface<Mass>
) : AbstractPagingAdapter<Mass, ItemEventBinding, MassPagingAdapter.MassViewHolder>(
    onItemClickInterface
) {

    override val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> ItemEventBinding
        get() = ItemEventBinding::inflate

    override fun createViewHolder(view: ItemEventBinding): MassViewHolder =
        MassViewHolder(view, onItemClickInterface)

    inner class MassViewHolder(
        private val binding: ItemEventBinding,
        private val onItemClickInterface: OnItemClickInterface<Mass>
    ) : BaseViewHolder<Mass, ItemEventBinding>(binding) {
        override fun bind(data: Mass) {
            binding.root.setOnClickListener { onItemClickInterface.onClick(data) }
            binding.image.load(data.image)
            binding.title.text = data.deceased.fullName
        }
    }
}