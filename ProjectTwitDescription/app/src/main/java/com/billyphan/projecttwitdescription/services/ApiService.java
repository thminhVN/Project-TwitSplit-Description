package com.billyphan.projecttwitdescription.services;

import android.os.Handler;

import com.billyphan.projecttwitdescription.callback.ResultCallback;

import java.util.List;

/**
 * Created by Billy Phan on 4/6/2018.
 */

public class ApiService {
    private static final long TIME_DELAY = 1000;
    private final Handler mHandler;

    public ApiService() {
        this.mHandler = new Handler();
    }

    public void sendMessage(List<String> parts, ResultCallback<Object> resultCallback) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                resultCallback.onSuccess("");
                resultCallback.onFinish();
            }
        }, TIME_DELAY);
    }
}
