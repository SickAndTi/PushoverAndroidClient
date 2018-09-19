package com.example.user.myapp;

import android.app.Application;

import com.example.user.myapp.di.AppModule;

import timber.log.Timber;
import toothpick.Toothpick;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        Toothpick.openScope(Constants.APP_SCOPE).installModules(new AppModule(this));
    }
}
