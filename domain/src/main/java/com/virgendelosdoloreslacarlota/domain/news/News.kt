package com.virgendelosdoloreslacarlota.domain.news

import com.virgendelosdoloreslacarlota.domain.ComparableItem

data class News(
    val url: String,
    val title: String,
    val body: String?,
    val image: String?
): ComparableItem {
    override val comparableKey: String
        get() = url
    override val content: String
        get() = title

}