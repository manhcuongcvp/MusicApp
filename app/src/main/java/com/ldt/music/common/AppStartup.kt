package com.ldt.music.common

import androidx.annotation.WorkerThread
import com.ldt.music.interactors.AppExecutors

object AppStartup {
    @JvmStatic
    fun onAppStartup() {
        AppExecutors.single().execute {
            initDataAsyncOnStartUp()
        }
    }

    @WorkerThread
    private fun initDataAsyncOnStartUp() {
        MediaManager.loadMediaIfNeeded()
    }
}