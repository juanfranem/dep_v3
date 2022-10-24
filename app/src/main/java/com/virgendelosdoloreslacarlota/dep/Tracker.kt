package com.virgendelosdoloreslacarlota.dep

import com.virgendelosdoloreslacarlota.dep.analytics.ScreenEvent
import com.virgendelosdoloreslacarlota.dep.analytics.UserEvents

interface Tracker {
    fun setEvent(event: UserEvents)
    fun setScreen(screen: ScreenEvent)
}