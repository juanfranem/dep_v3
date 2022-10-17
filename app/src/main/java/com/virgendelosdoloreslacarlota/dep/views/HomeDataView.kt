package com.virgendelosdoloreslacarlota.dep.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.virgendelosdoloreslacarlota.dep.base.AbstractAdapter
import com.virgendelosdoloreslacarlota.dep.databinding.ViewHomeDataBinding

class HomeDataView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAtr: Int = 0
): LinearLayout(context, attrs, defStyleAtr) {

    private val binding = ViewHomeDataBinding.inflate(LayoutInflater.from(context),
        this,
        true)

    fun setEmptyText(text: String) {
        binding.emptyText.text = text
    }

    fun setAdapter(adapter: AbstractAdapter<*, *, *>) {
        binding.items.adapter = adapter
    }

    fun setLayoutManager(layoutManager: LayoutManager) {
        binding.items.layoutManager = layoutManager
    }

    var isVisibleEmptyText: Boolean
        get() = binding.emptyText.isVisible
        set(value) {
            if (value) {
                binding.title.hideButton()
            } else {
                binding.title.showButton()
            }
            binding.items.isVisible = !value
            binding.emptyText.isVisible = value
        }

    val titleView: TitleView = binding.title

}