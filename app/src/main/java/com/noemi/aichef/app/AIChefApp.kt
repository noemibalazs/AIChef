package com.noemi.aichef.app

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import com.noemi.aichef.observer.AppObserver
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class AIChefApp : Application() {

    @Inject
    lateinit var appObserver: AppObserver

    override fun onCreate() {
        super.onCreate()

        ProcessLifecycleOwner.get().lifecycle.addObserver(appObserver)
    }

    override fun onTerminate() {
        ProcessLifecycleOwner.get().lifecycle.removeObserver(appObserver)
        super.onTerminate()
    }
}