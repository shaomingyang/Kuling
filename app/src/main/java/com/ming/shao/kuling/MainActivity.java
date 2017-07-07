package com.ming.shao.kuling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ming.shao.kuling.ImagePicker.ImagePicker;
import com.ming.shao.kuling.ImagePicker.callback.CallbackForCamera;
import com.ming.shao.kuling.ImagePicker.callback.CallbackForGallery;
import com.ming.shao.kuling.ImagePicker.callback.CallbackForImagePicker;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button caream;
    Button photo_but;
    Button my_photo_but;
    ImagePicker imagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePicker = new ImagePicker(this);
        setContentView(R.layout.activity_main);
        caream = (Button) findViewById(R.id.caream_but);
        photo_but = (Button) findViewById(R.id.photo_but);
        my_photo_but = (Button) findViewById(R.id.my_photo_but);
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
}
