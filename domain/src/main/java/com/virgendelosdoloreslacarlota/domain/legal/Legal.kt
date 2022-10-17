package com.virgendelosdoloreslacarlota.domain.legal

import com.virgendelosdoloreslacarlota.domain.ComparableItem

data class Legal(
    val title: String,
    val body: String?,
    val url: String
): ComparableItem {
    override val comparableKey: String
        get() = url
    override val content: String
        get() = title

}