package com.billyphan.projecttwitdescription.bases;

import android.support.v7.app.AppCompatActivity;

import com.billyphan.projecttwitdescription.App;
import com.billyphan.projecttwitdescription.services.ApiService;

/**
 * Created by Billy Phan on 4/6/2018.
 */

public class ViewModel {
    private final AppCompatActivity mActivity;
    private final ApiService mApiService;

    public ViewModel(AppCompatActivity appCompatActivity) {
        this.mActivity = appCompatActivity;
        this.mApiService = App.getInstance().getApiService();
    }

    public ApiService getApiService() {
        return mApiService;
    }

    public AppCompatActivity getActivity() {
        return mActivity;
    }

    public void onCreate() {
    }

    public void onReset() {
    }

    public void onStart() {
    }

    public void onResume() {
    }

    public void onPause() {
    }

    public void onStop() {
    }

    public void onDestroy() {
    }

    public void onResult() {
    }

}
