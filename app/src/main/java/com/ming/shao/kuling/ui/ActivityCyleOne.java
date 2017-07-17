package com.ming.shao.kuling.ui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.ming.shao.kuling.R;

/**
 * Created by smy on 2017/7/13 0013.
 */

public class ActivityCyleOne extends Activity {
    private static final String TAG = "MainActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate::The ActivityCyleOne is being created.");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart::The ActivityCyleOne is about to become visible.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume::The ActivityCyleOne has become visible.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause:: Another ActivityCyleOne is taking focus.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop::The ActivityCyleOne is no longer visible");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy::The ActivityCyleOne is about to be destroyed");
    }
}
