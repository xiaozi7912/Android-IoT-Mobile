package com.xiaozi.android.things.phone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;
import com.xiaozi.framework.libs.BaseActivity;
import com.xiaozi.framework.libs.activity.PlayerActivity;
import com.xiaozi.framework.libs.utils.Logger;
import com.xiaozi.framework.libs.view.DevInfoView;

import java.util.Random;

public class MainActivity extends BaseActivity {
    private Button mShowPlayerActivityButton = null;
    private DevInfoView mDevInfoView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logger.init(BuildConfig.SHOW_DEV_INFO);
        initView();
//        sendFirebaseMessage(BuildConfig.APPLICATION_ID);
    }

    @Override
    protected void initView() {
        super.initView();
        mShowPlayerActivityButton = findViewById(R.id.main_show_player_activity_button);
        mDevInfoView = findViewById(R.id.main_dev_info_text);

        mDevInfoView.setApplicationId(BuildConfig.APPLICATION_ID);
        mDevInfoView.setVersionName(BuildConfig.VERSION_NAME);
        mDevInfoView.setVersionCode(BuildConfig.VERSION_CODE);
        mDevInfoView.updateView();
        if (!BuildConfig.SHOW_DEV_INFO) mDevInfoView.setVisibility(View.GONE);

        mShowPlayerActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mActivity, PlayerActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sendFirebaseMessage(String message) {
        Log.i(LOG_TAG, "sendFirebaseMessage");
        Log.d(LOG_TAG, "sendFirebaseMessage message : " + message);
        Random random = new Random(System.currentTimeMillis());
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        fm.send(new RemoteMessage.Builder("26939868725@gcm.googleapis.com")
                .setMessageId(String.valueOf(random.nextInt(10000)))
                .addData("message", message)
                .build());
    }
}
