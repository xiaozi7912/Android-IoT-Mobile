package com.xiaozi.android.things.utils;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;

/**
 * Created by xiaoz on 2017-10-04.
 */

public class BaseActivity extends Activity {
    protected final String LOG_TAG = getClass().getSimpleName();
    protected Activity mActivity = this;
    protected Handler mHandler = new Handler();
    protected DisplayMetrics mDisplayMetrics = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDisplayMetrics = getResources().getDisplayMetrics();
    }

    protected void initView() {

    }
}
