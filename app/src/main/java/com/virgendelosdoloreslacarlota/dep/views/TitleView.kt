package com.virgendelosdoloreslacarlota.dep.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.view.isVisible
import com.virgendelosdoloreslacarlota.dep.databinding.ViewTitleBinding

class TitleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAtr: Int = 0
): LinearLayout(context, attrs, defStyleAtr) {

    private val binding = ViewTitleBinding.inflate(LayoutInflater.from(context), this, true)

    override fun setOnClickListener(l: OnClickListener?) {
        binding.button.setOnClickListener(l)
    }

    fun setButtonText(text: String) {
        binding.button.text = text
    }

    fun setText(text: String) {
        binding.title.text = text
    }

    fun hideButton() {
        binding.button.isVisible = false
    }

    fun showButton() {
        binding.button.isVisible = true
    }

}