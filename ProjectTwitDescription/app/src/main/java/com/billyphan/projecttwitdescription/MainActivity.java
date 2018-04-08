package com.billyphan.projecttwitdescription;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.billyphan.projecttwitdescription.bases.BaseActivity;
import com.billyphan.projecttwitdescription.bases.adapter.CollapseList;
import com.billyphan.projecttwitdescription.view_model.MessageViewModel;
import com.billyphan.projecttwitdescription.views.MessageAdapter;

import java.util.List;

public class MainActivity extends BaseActivity {

    private MessageViewModel mViewModel;
    private MessageAdapter mTrunkMessage;
    private EditText mEdtMessage;
    private View mBtnSend;

    private void mapViews() {
        this.mTrunkMessage = new MessageAdapter(findViewById(R.id.recv_message));
        this.mEdtMessage = findViewById(R.id.edt_message);
        this.mBtnSend = findViewById(R.id.btn_send);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.mViewModel = new MessageViewModel(this);
        this.mapViews();
        this.setupViews();
        this.mViewModel.onCreate();
    }

    private void setupViews() {
        this.mTrunkMessage.setItems(new CollapseList());
        this.mBtnSend.setOnClickListener(view -> {
            // if loading then do not update text
            if (mViewModel.getLoading().get()) return;
            mViewModel.updateMessage(this.mEdtMessage.getText().toString(),
                    () -> mViewModel.sendToServer());
        });
    }

    private void setupEvents() {
        mViewModel.getMessage().subscribe(message -> this.updateEditMessage());
        mViewModel.getLoading().subscribe(loading -> {
            if (loading)
                this.showLoading();
            else
                this.hideLoading();
        });
        mViewModel.getSuccess().subscribe(success -> {
            if (success) {
                this.notifySuccess();
                this.updateTextMessage();
            } else
                this.notifyError("Error loading");
        });
        mViewModel.getValidation().subscribe(valid -> {
            if (valid != null)
                this.notifyError(valid);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupEvents();
        this.mViewModel.onStart();
    }


    @Override
    protected void onStop() {
        super.onStop();
        this.mViewModel.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.mViewModel.onDestroy();
    }

    private void updateTextMessage() {
        List<String> parts = this.mViewModel
                .getMessage()
                .get().getParts();
        this.mTrunkMessage.addGroup(new CollapseList.Header(parts));
        this.mEdtMessage.setText("");
    }

    private void updateEditMessage() {
        String text = this.mViewModel
                .getMessage()
                .get().toString();
        this.mEdtMessage.setText(text);
    }

    private void notifyError(String valid) {
        Toast.makeText(this, valid, Toast.LENGTH_SHORT).show();
    }

    private void notifySuccess() {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }

    private void hideLoading() {
        // TODO: 4/6/2018 Hide Loading
    }

    private void showLoading() {
        // TODO: 4/6/2018 show loading
        Toast.makeText(this, "Send to server", Toast.LENGTH_SHORT).show();
    }

}
