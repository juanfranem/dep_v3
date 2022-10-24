package com.virgendelosdoloreslacarlota.dep.di

import android.content.Context
import com.virgendelosdoloreslacarlota.dep.Tracker
import com.virgendelosdoloreslacarlota.dep.services.TrackerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Provides
    @Singleton
    fun providesAppTracker(
        @ApplicationContext context: Context
    ): Tracker = TrackerImpl(context)

}