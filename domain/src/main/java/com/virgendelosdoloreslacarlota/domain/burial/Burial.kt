package com.virgendelosdoloreslacarlota.domain.burial

import com.virgendelosdoloreslacarlota.domain.ComparableItem
import com.virgendelosdoloreslacarlota.domain.deceased.Deceased

data class Burial(
    val deceased: Deceased,
    val church: String,
    val date: String,
    val image: String?,
    val url: String
): ComparableItem {
    override val comparableKey: String
        get() = url + url
    override val content: String
        get() = deceased.name + deceased.lastName

}