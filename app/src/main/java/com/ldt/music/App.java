package com.ldt.music;

import android.app.Application;

import com.ldt.music.common.AppStartup;
import com.ldt.music.util.PreferenceUtil;


public class App extends Application {
    private static App mInstance;
    public static synchronized App getInstance() {
        return mInstance;
    }

    public PreferenceUtil getPreferencesUtility() {
        return PreferenceUtil.getInstance(App.this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        AppStartup.onAppStartup();
    }


}