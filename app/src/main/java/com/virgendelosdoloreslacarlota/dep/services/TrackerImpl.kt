package com.virgendelosdoloreslacarlota.dep.services

import android.content.Context
import androidx.core.os.bundleOf
import com.google.firebase.analytics.FirebaseAnalytics
import com.virgendelosdoloreslacarlota.dep.Tracker
import com.virgendelosdoloreslacarlota.dep.analytics.ScreenEvent
import com.virgendelosdoloreslacarlota.dep.analytics.UserEvents

class TrackerImpl(context: Context): Tracker {

    private val firebaseAnalytics = FirebaseAnalytics.getInstance(context)

    override fun setEvent(event: UserEvents) {
        val param = bundleOf()
        event.params.forEach {
            param.putString(it.key, it.value)
        }
        firebaseAnalytics.logEvent(event.eventName, param)
    }

    override fun setScreen(screen: ScreenEvent) {
        val param = bundleOf()
        param.putString(FirebaseAnalytics.Param.SCREEN_NAME, screen.screenName)
        param.putString(FirebaseAnalytics.Param.SCREEN_CLASS, screen.screenName)
        screen.params.forEach {
            param.putString(it.key, it.value)
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, param)
    }
}