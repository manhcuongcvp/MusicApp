package com.ldt.music.utils

import android.app.Application
import com.ldt.music.App
import com.ldt.music.R
import com.ldt.music.interactors.runOnUiThread
import es.dmoral.toasty.Toasty

object Utils {
    @JvmStatic
    fun getApp(): Application {
        return App.getInstance()
    }

    fun hasFlags(flags: Int, flagsNeedToCheck: Int): Boolean {
        return flags and flagsNeedToCheck != 0
    }

    fun showGeneralErrorToast() {
        runOnUiThread {
            Toasty.normal(getApp(), R.string.str_error_general).show()
        }
    }
}