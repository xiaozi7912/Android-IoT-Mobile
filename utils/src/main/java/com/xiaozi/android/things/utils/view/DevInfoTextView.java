package com.xiaozi.android.things.utils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.xiaozi.android.things.utils.BuildConfig;

/**
 * Created by xiaoz on 2017-10-04.
 */

public class DevInfoTextView extends BaseTextView {
    private String mApplicationId = null;
    private String mVersionName = null;
    private int mVersionCode = 0;

    public DevInfoTextView(Context context) {
        super(context);
        initView();
    }

    public DevInfoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    @Override
    protected void initView() {
        super.initView();
        Log.i(LOG_TAG, "initView");
        Log.d(LOG_TAG, "initView BuildConfig.APPLICATION_ID : " + BuildConfig.APPLICATION_ID);
        Log.d(LOG_TAG, "initView BuildConfig.VERSION_NAME : " + BuildConfig.VERSION_NAME);
        Log.d(LOG_TAG, "initView BuildConfig.VERSION_CODE : " + BuildConfig.VERSION_CODE);
        Log.d(LOG_TAG, "initView BuildConfig.SHOW_DEV_INFO : " + BuildConfig.SHOW_DEV_INFO);
        Log.d(LOG_TAG, "initView BuildConfig.DEBUG : " + BuildConfig.DEBUG);
        setVisibility(View.GONE);
        if (BuildConfig.SHOW_DEV_INFO) setVisibility(View.VISIBLE);
    }

    public void setApplicationId(String applicationId) {
        mApplicationId = applicationId;
    }

    public void setVersionName(String versionName) {
        mVersionName = versionName;
    }

    public void setVersionCode(int versionCode) {
        mVersionCode = versionCode;
    }

    public void updateView() {
        Log.i(LOG_TAG, "updateView");
        Log.d(LOG_TAG, "updateView mApplicationId : " + mApplicationId);
        Log.d(LOG_TAG, "updateView mVersionName : " + mVersionName);
        Log.d(LOG_TAG, "updateView mVersionCode : " + mVersionCode);
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("widthPixels : %s", mDisplayMetrics.widthPixels));
        builder.append(" ");
        builder.append(String.format("heightPixels : %s", mDisplayMetrics.heightPixels));
        builder.append(" ");
        builder.append(String.format("densityDpi : %s", mDisplayMetrics.densityDpi));
        builder.append("\n");
        builder.append(String.format("APPLICATION_ID : %s", mApplicationId));
        builder.append("\n");
        builder.append(String.format("VERSION_NAME : %s", mVersionName));
        builder.append("\n");
        builder.append(String.format("VERSION_CODE : %s", mVersionCode));
        setText(builder.toString());
    }
}
