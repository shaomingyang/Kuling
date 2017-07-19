package com.ming.shao.kuling;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ming.shao.kuling.ImagePicker.ImagePicker;
import com.ming.shao.kuling.ImagePicker.callback.CallbackForCamera;
import com.ming.shao.kuling.ImagePicker.callback.CallbackForGallery;
import com.ming.shao.kuling.ImagePicker.callback.CallbackForImagePicker;
import com.ming.shao.kuling.base.BaseActivity;
import com.ming.shao.kuling.ui.fragment.adapter.HomeFragmentAdapter;
import com.ming.shao.kuling.widget.CircleImageView;
import com.ming.shao.kuling.widget.CustomViewPager;
import com.ming.shao.kuling.widget.SlidingTabLayout;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    Button caream;
    Button photo_but;
    Button my_photo_but;
    ImagePicker imagePicker;
    SlidingTabLayout tab_layout;
    CustomViewPager mViewPage;
    HomeFragmentAdapter homeFragmentAdapter;

    CircleImageView circleImageView;
    private List<String> mTabEntitys = Arrays.asList("首页", "消息", "更多");

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {
        imagePicker = new ImagePicker(this);
        setContentView(R.layout.activity_main);
        caream = (Button) findViewById(R.id.caream_but);
        photo_but = (Button) findViewById(R.id.photo_but);
        my_photo_but = (Button) findViewById(R.id.my_photo_but);
        tab_layout = (SlidingTabLayout) findViewById(R.id.tab_layout);

        tab_layout.setTabData(mTabEntitys);
        homeFragmentAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), mTabEntitys);
        mViewPage = (CustomViewPager) findViewById(R.id.content_view);
        mViewPage.setAdapter(homeFragmentAdapter);
        mViewPage.setCurrentItem(0);
        tab_layout.setupWithViewPager(mViewPage);
        tab_layout.setCurrentTab(0);
        circleImageView = (CircleImageView) findViewById(R.id.cycle_head_img);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.approot_bg);
        circleImageView.setImageBitmap(bitmap);
        caream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imagePicker.openCamera(new CallbackForCamera() {
                    @Override
                    public void onError(Exception error) {
                        Toast.makeText(MainActivity.this, error.toString() + "错误", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete(String imagePath) {
                        Toast.makeText(MainActivity.this, imagePath, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onCancel(String imagePath) {
                        Toast.makeText(MainActivity.this, imagePath, Toast.LENGTH_LONG).show();
                    }
                });


//
            }
        });

        photo_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker.openGallery(new CallbackForGallery() {
                    @Override
                    public void onError(Exception error) {
                        Toast.makeText(MainActivity.this, error.toString() + "错误", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete(String imagePath) {
                        Toast.makeText(MainActivity.this, imagePath + "rwerr", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        my_photo_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagePicker.openImagePicker(new CallbackForImagePicker() {
                    @Override
                    public void onError(Exception error) {
                        Toast.makeText(MainActivity.this, error.toString() + "错误", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onComplete(List<String> imagePath) {
                        Toast.makeText(MainActivity.this, imagePath.get(0) + "rwerr", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.imageOnActivityResult(requestCode, resultCode, data);
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart::The activity is about to become visible.");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume::The activity has become visible.");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause:: Another activity is taking focus.");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop::The activity is no longer visible");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy::The activity is about to be destroyed");
    }
}
