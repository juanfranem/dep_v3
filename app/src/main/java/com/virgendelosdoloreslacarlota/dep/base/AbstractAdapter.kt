package com.virgendelosdoloreslacarlota.dep.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.viewbinding.ViewBinding
import com.virgendelosdoloreslacarlota.domain.ComparableItem

abstract class AbstractAdapter<T: ComparableItem, V : ViewBinding, VH: BaseViewHolder<T, V>>(
    private val onItemClickInterface: OnItemClickInterface<T>
): ListAdapter<T, VH>(diffUtil<T>()) {

    companion object {
        private fun <T: ComparableItem> diffUtil() = object: DiffUtil.ItemCallback<T>() {
            override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                return newItem.comparableKey == oldItem.comparableKey
            }

            override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                return newItem.content == oldItem.content
            }

        }
    }


    open fun onBindedViewHolder(holder: BaseViewHolder<T, V>, data: T) {

    }

    abstract val inflateMethod: (LayoutInflater, ViewGroup?, Boolean) -> V
    abstract fun createViewHolder(view: V): VH
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = inflateMethod.invoke(LayoutInflater.from(parent.context), parent, false)
        return createViewHolder(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
        onBindedViewHolder(holder, getItem(position))
    }

}