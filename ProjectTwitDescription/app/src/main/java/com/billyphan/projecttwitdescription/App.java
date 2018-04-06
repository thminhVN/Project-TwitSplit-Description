package com.billyphan.projecttwitdescription;

import android.app.Application;

import com.billyphan.projecttwitdescription.services.ApiService;

/**
 * Created by Billy Phan on 4/6/2018.
 */

public class App extends Application {
    private static App sApp;
    private ApiService mApiService;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mApiService = new ApiService();
        sApp = this;
    }

    public static App getInstance() {
        return sApp;
    }

    public ApiService getApiService() {
        return mApiService;
    }
}
