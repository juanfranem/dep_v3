package com.virgendelosdoloreslacarlota.dep.base

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T, V : ViewBinding>(
    binding: V,
) : ViewHolder(binding.root) {
    abstract fun bind(data: T)
}