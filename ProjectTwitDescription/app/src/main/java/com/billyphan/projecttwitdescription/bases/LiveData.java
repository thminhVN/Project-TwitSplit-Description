package com.billyphan.projecttwitdescription.bases;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Billy Phan on 4/6/2018.
 */

public class LiveData<T> {

    private T data;
    private List<ObserveListener<T>> listeners = new ArrayList<>();

    public T get() {
        return data;
    }

    public void set(T data) {
        if (this.data != data) {
            this.data = data;
            notifyChange();
        }
    }

    public void subscribe(@NonNull ObserveListener<T> listener) {
        if (listeners.contains(listener)) return;
        listeners.add(listener);
    }

    public void unSubscribe(){
        listeners.clear();
    }

    private void notifyChange() {
        if (listeners.isEmpty()) return;
        for (ObserveListener<T> listener : listeners) {
            listener.onChanged(data);
        }
    }

    public interface ObserveListener<T> {
        void onChanged(T data);
    }
}
