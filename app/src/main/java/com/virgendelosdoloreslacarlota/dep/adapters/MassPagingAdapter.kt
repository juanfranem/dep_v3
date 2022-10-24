package com.virgendelosdoloreslacarlota.dep.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.virgendelosdoloreslacarlota.dep.base.AbstractPagingAdapter
import com.virgendelosdoloreslacarlota.dep.base.BaseViewHolder
import com.virgendelosdoloreslacarlota.dep.base.OnItemClickInterface
import com.virgendelosdoloreslacarlota.dep.databinding.ItemEventSquareBinding
import com.virgendelosdoloreslacarlota.domain.mass.Mass

class MassPagingAdapter(
    private val onItemClickInterface: OnItemClickInterface<Mass>
) : AbstractPagingAdapter<Mass, ItemEventSquareBinding, MassPagingAdapter.MassViewHolder>(
    onItemClickInterface
) {

    override val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> ItemEventSquareBinding
        get() = ItemEventSquareBinding::inflate

    override fun createViewHolder(view: ItemEventSquareBinding): MassViewHolder =
        MassViewHolder(view, onItemClickInterface)

    inner class MassViewHolder(
        private val binding: ItemEventSquareBinding,
        private val onItemClickInterface: OnItemClickInterface<Mass>
    ) : BaseViewHolder<Mass, ItemEventSquareBinding>(binding) {
        override fun bind(data: Mass) {
            binding.root.setOnClickListener { onItemClickInterface.onClick(data) }
            binding.image.load(data.image)
            binding.title.text = data.deceased.fullName
        }
    }
}