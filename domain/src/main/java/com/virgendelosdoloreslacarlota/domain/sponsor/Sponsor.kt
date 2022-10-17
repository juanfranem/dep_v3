package com.virgendelosdoloreslacarlota.domain.sponsor

import com.virgendelosdoloreslacarlota.domain.ComparableItem

data class Sponsor(
    val image: String?,
    val title: String?,
    val url: String?
): ComparableItem {
    override val comparableKey: String
        get() = image ?: title ?: url ?: ""
    override val content: String
        get() = image ?: title ?: url ?: ""

}