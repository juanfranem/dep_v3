package com.virgendelosdoloreslacarlota.dep.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import coil.load
import com.virgendelosdoloreslacarlota.dep.base.AbstractAdapter
import com.virgendelosdoloreslacarlota.dep.base.BaseViewHolder
import com.virgendelosdoloreslacarlota.dep.base.OnItemClickInterface
import com.virgendelosdoloreslacarlota.dep.databinding.ItemSponsorBinding
import com.virgendelosdoloreslacarlota.domain.sponsor.Sponsor

class SponsorAdapter(
    private val onItemClickInterface: OnItemClickInterface<Sponsor>
): AbstractAdapter<Sponsor, ItemSponsorBinding, SponsorAdapter.SponsorViewHolder>(onItemClickInterface) {

    override val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> ItemSponsorBinding
        get() = ItemSponsorBinding::inflate

    override fun createViewHolder(view: ItemSponsorBinding): SponsorViewHolder =
        SponsorViewHolder(view, onItemClickInterface)

    inner class SponsorViewHolder(
        private val binding: ItemSponsorBinding,
        private val onItemClickInterface: OnItemClickInterface<Sponsor>
    ): BaseViewHolder<Sponsor, ItemSponsorBinding>(binding) {
        override fun bind(data: Sponsor) {
            binding.root.isEnabled = !data.url.isNullOrBlank()
            binding.root.setOnClickListener { onItemClickInterface.onClick(data) }
            binding.image.load(data.image)
            binding.title.isVisible = !data.title.isNullOrBlank()
            binding.title.text = data.title
        }
    }


}