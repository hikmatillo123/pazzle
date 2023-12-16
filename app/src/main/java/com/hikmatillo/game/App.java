package com.hikmatillo.game;

import android.app.Application;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppCashe.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
