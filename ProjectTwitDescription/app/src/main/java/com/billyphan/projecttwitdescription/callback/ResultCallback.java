package com.billyphan.projecttwitdescription.callback;

/**
 * Created by Billy Phan on 4/6/2018.
 */

public interface ResultCallback<T> {
    default void onSuccess(T result) {
        onFinish();
    }

    default void onFailure(Exception ex) {
        onFinish();
    }

    default void onFinish() {
    }
}
