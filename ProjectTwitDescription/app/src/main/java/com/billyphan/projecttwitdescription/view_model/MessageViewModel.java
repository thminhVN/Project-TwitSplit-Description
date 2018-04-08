package com.billyphan.projecttwitdescription.view_model;

import android.support.v7.app.AppCompatActivity;
import android.widget.PopupWindow;

import com.billyphan.projecttwitdescription.MainActivity;
import com.billyphan.projecttwitdescription.R;
import com.billyphan.projecttwitdescription.bases.LiveData;
import com.billyphan.projecttwitdescription.bases.ViewModel;
import com.billyphan.projecttwitdescription.callback.ResultCallback;
import com.billyphan.projecttwitdescription.exceptions.TextNullException;
import com.billyphan.projecttwitdescription.exceptions.WordTooLongNoWhiteSpaceException;
import com.billyphan.projecttwitdescription.model.Message;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Billy Phan on 4/6/2018.
 */

public class MessageViewModel extends ViewModel {
    private LiveData<Message> mMessage;
    private LiveData<Boolean> mLoading;
    private LiveData<Boolean> mSuccess;
    private LiveData<String> mValidation;

    public MessageViewModel(AppCompatActivity appCompatActivity) {
        super(appCompatActivity);
        this.mMessage = new LiveData<>();
        this.mLoading = new LiveData<>();
        this.mSuccess = new LiveData<>();
        this.mValidation = new LiveData<>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onStart() {
        super.onStart();
        getLoading().set(false);
        getMessage().set(new Message(getActivity().getResources().getString(R.string.template_message)));
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mLoading.unSubscribe();
        this.mSuccess.unSubscribe();
        this.mValidation.unSubscribe();
        this.mMessage.unSubscribe();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void sendToServer() {
        this.mLoading.set(true);
        getApiService().sendMessage(this.mMessage.get().getParts(), new ResultCallback<Object>() {
            @Override
            public void onSuccess(Object result) {
                mSuccess.set(true);
            }

            @Override
            public void onFailure(Exception ex) {
                mSuccess.set(false);
            }

            @Override
            public void onFinish() {
                mLoading.set(false);
            }
        });
    }

    public LiveData<String> getValidation() {
        return mValidation;
    }

    public LiveData<Message> getMessage() {
        return mMessage;
    }

    public LiveData<Boolean> getLoading() {
        return mLoading;
    }

    public LiveData<Boolean> getSuccess() {
        return mSuccess;
    }

    public void updateMessage(String s, Runnable success) {
        Executors.newFixedThreadPool(1).execute(() -> {
            try {
                getMessage().get().update(s);
                getActivity().runOnUiThread(success);
            } catch (Exception e) {
                getActivity().runOnUiThread(() -> mValidation.set(e.getMessage()));
            }
        });

    }
}
