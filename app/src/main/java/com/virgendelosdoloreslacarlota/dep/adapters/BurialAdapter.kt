package com.virgendelosdoloreslacarlota.dep.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import coil.load
import com.virgendelosdoloreslacarlota.dep.base.AbstractAdapter
import com.virgendelosdoloreslacarlota.dep.base.BaseViewHolder
import com.virgendelosdoloreslacarlota.dep.base.OnItemClickInterface
import com.virgendelosdoloreslacarlota.dep.databinding.ItemEventBinding
import com.virgendelosdoloreslacarlota.domain.burial.Burial

class BurialAdapter(
    private val onItemClickInterface: OnItemClickInterface<Burial>
): AbstractAdapter<Burial, ItemEventBinding, BurialAdapter.BurialViewHolder>(onItemClickInterface) {

    override val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> ItemEventBinding
        get() = ItemEventBinding::inflate

    override fun createViewHolder(view: ItemEventBinding): BurialViewHolder =
        BurialViewHolder(view, onItemClickInterface)

    inner class BurialViewHolder(
        private val binding: ItemEventBinding,
        private val onItemClickInterface: OnItemClickInterface<Burial>
    ): BaseViewHolder<Burial, ItemEventBinding>(binding) {
        override fun bind(data: Burial) {
            binding.root.setOnClickListener { onItemClickInterface.onClick(data) }
            binding.image.load(data.image)
            binding.title.text = data.deceased.fullName
        }
    }
}