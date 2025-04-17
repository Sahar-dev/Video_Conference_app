package com.android.avempace;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;

public class App  extends Application {
    private static App sApplication;

    public static App getApplication() {
        return sApplication;
    }

    public static Context getContext() {
        return getApplication().getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }
}