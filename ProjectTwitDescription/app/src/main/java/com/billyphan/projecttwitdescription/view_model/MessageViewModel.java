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

    public void updateMessage(String s) {
        try {
            getMessage().get().update(s);
        } catch (WordTooLongNoWhiteSpaceException e) {
            this.mValidation.set(e.getMessage());
        } catch (TextNullException e) {
            this.mValidation.set(e.getMessage());
        }
    }
}
