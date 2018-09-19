package com.example.user.myapp.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.example.user.myapp.api.ApiClient;
import com.example.user.myapp.db.MessageDao;
import com.example.user.myapp.db.MyDatabase;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;
import toothpick.config.Module;

public class AppModule extends Module {

    public AppModule(Context context) {
        MyDatabase dataBase;
        dataBase = Room.databaseBuilder(context, MyDatabase.class, "database")
                .fallbackToDestructiveMigration()
                .build();
        bind(MyDatabase.class).toInstance(dataBase);
        bind(MessageDao.class).toInstance(dataBase.messageDao());

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLoggingInterceptor(log -> Timber.d(log)).setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();

        bind(Retrofit.class).toInstance(new Retrofit.Builder()
                .baseUrl("https://api.pushover.net/1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build());
        bind(ApiClient.class).singletonInScope();
    }
}
