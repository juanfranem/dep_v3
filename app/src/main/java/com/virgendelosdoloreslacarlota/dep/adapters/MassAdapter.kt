package com.virgendelosdoloreslacarlota.dep.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.virgendelosdoloreslacarlota.dep.base.AbstractAdapter
import com.virgendelosdoloreslacarlota.dep.base.BaseViewHolder
import com.virgendelosdoloreslacarlota.dep.base.OnItemClickInterface
import com.virgendelosdoloreslacarlota.dep.databinding.ItemEventBinding
import com.virgendelosdoloreslacarlota.domain.mass.Mass

class MassAdapter(
    private val onItemClickInterface: OnItemClickInterface<Mass>
): AbstractAdapter<Mass, ItemEventBinding, MassAdapter.MassViewHolder>(onItemClickInterface) {

    override val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> ItemEventBinding
        get() = ItemEventBinding::inflate

    override fun createViewHolder(view: ItemEventBinding): MassViewHolder =
        MassViewHolder(view, onItemClickInterface)

    inner class MassViewHolder(
        private val binding: ItemEventBinding,
        private val onItemClickInterface: OnItemClickInterface<Mass>
    ): BaseViewHolder<Mass, ItemEventBinding>(binding) {
        override fun bind(data: Mass) {
            binding.root.setOnClickListener { onItemClickInterface.onClick(data) }
            binding.image.load(data.image)
            binding.title.text = data.deceased.fullName
        }
    }


}