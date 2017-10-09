package com.xiaozi.android.things.phone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
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

    private FirebaseAuth mAuth = null;
    private FirebaseFirestore mDatabase = null;
    private ListenerRegistration mRegistration = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Logger.init(BuildConfig.SHOW_DEV_INFO);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseFirestore.getInstance();

        initView();
        getSensorBMP180Devices();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRegistration.remove();
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
        Logger.i(LOG_TAG, "sendFirebaseMessage");
        Logger.d(LOG_TAG, "sendFirebaseMessage message : " + message);
        Random random = new Random(System.currentTimeMillis());
        FirebaseMessaging fm = FirebaseMessaging.getInstance();
        fm.send(new RemoteMessage.Builder("26939868725@gcm.googleapis.com")
                .setMessageId(String.valueOf(random.nextInt(10000)))
                .addData("message", message)
                .build());
    }

    private void getSensorBMP180Devices() {
        Logger.i(LOG_TAG, "getSensorBMP180Devices");
        mDatabase.collection("devices").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Logger.i(LOG_TAG, "getSensorBMP180Devices onComplete");
                Logger.d(LOG_TAG, "getSensorBMP180Devices onComplete task.isSuccessful : " + task.isSuccessful());
                if (task.isSuccessful()) {
                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
                        Logger.d(LOG_TAG, "getSensorBMP180Devices onComplete documentSnapshot.getId : " + documentSnapshot.getId());
                    }
                }
            }
        });
//        mDatabase.collection("devices").document("b8:27:eb:0d:86:53").collection("sensor_bmp180").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                Logger.i(LOG_TAG, "getSensorBMP180Devices onComplete");
//                Logger.d(LOG_TAG, "getSensorBMP180Devices onComplete task.isSuccessful : " + task.isSuccessful());
//                if (task.isSuccessful()) {
//                    for (DocumentSnapshot documentSnapshot : task.getResult()) {
//                        Logger.d(LOG_TAG, "getSensorBMP180Devices onComplete documentSnapshot.getId : " + documentSnapshot.getId());
//                    }
//                }
//            }
//        });
        mRegistration = mDatabase.collection("devices").document("b8:27:eb:0d:86:53").collection("sensor_bmp180")
                .orderBy("create_time", Query.Direction.DESCENDING)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot snapshots, FirebaseFirestoreException e) {
                        if (e != null) {
                            e.printStackTrace();
                            return;
                        }

                        for (DocumentSnapshot documentSnapshot : snapshots) {
                            Logger.d(LOG_TAG, "getSensorBMP180Devices onComplete documentSnapshot.getId : " + documentSnapshot.getId());
                            Logger.d(LOG_TAG, "getSensorBMP180Devices onComplete documentSnapshot.getData : " + documentSnapshot.getData());
                        }
                    }
                });
    }
}
