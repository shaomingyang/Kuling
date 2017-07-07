package com.ming.shao.kuling.ImagePicker;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.ming.shao.kuling.ImagePicker.callback.CallbackForCamera;
import com.ming.shao.kuling.ImagePicker.callback.CallbackForGallery;
import com.ming.shao.kuling.ImagePicker.callback.CallbackForImagePicker;
import com.ming.shao.kuling.constant.Config;
import com.ming.shao.kuling.util.PictureUtil;
import com.ming.shao.kuling.util.UriUtil;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by smy on 2017/7/5 0005.
 */

public class ImagePicker {
    public static final int REQ_PICKER = 1000;
    public static final int REQ_CAMERA = 1001;
    public static final int REQ_GALLERY = 1002;
    //拍照文件的路径
    private static final String SPF_KEY_PATH = "camera_path";


    Activity currActivity;
    //拍照回调
    CallbackForCamera callbackForCamera;
    //自定义相册回调
    CallbackForImagePicker forImagePicker;
    private CallbackForGallery mCallbackForGallery;
    private boolean isMultiplePick = true;
    //接受当前activity
    public ImagePicker(Activity currActivity) {
        this.currActivity = currActivity;
    }

    /**
     * 自定义相册选择图片
     */
    public void openImagePicker(CallbackForImagePicker forImagePicker) {
        this.forImagePicker = forImagePicker;
        currActivity.startActivityForResult(ImagePickActivity.getCallingIntent(currActivity, isMultiplePick), REQ_PICKER);


    }
    public void openGallery(CallbackForGallery callback) {
        this.mCallbackForGallery = callback;

        Intent intentPic = new Intent(Intent.ACTION_PICK);
        intentPic.setType("image/*");
        currActivity.startActivityForResult(intentPic, REQ_GALLERY);
    }

    //打开相机  回调
    public void openCamera(CallbackForCamera callbackForCamera) {
        this.callbackForCamera = callbackForCamera;
        //获取照片路径
        String tempCameraPath = buildCameraPath();
        //先将图片路径保存起来
        saveCameraFilePath(tempCameraPath);
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(tempCameraPath);
        Uri imageUri = Uri.fromFile(file);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(currActivity, "com.ming.shao.kuling.base.fileprovider", file);//通过FileProvider创建一个content类型的Uri
        }
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        intentCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        currActivity.startActivityForResult(intentCamera, REQ_CAMERA);//调用activity startActivityForResult
    }

    /**
     * 拍照完成返回的 处理
     */
    public void imageOnActivityResult(int requestCode, int resultCode, Intent data) {
        try {


            if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    case REQ_CAMERA:
                        String filePath = getCameraFilePath();
                        if (!TextUtils.isEmpty(filePath)) {
                            PictureUtil.galleryAddPic(currActivity, filePath);
                            if (null != callbackForCamera) {
                                callbackForCamera.onComplete(filePath);
                            }
                        }
                        break;
                    case REQ_GALLERY:
                        if (mCallbackForGallery != null) {
                            mCallbackForGallery.onComplete(UriUtil.getPath2PickImage(currActivity, data));
                        }
                        break;
                }

            } else if (resultCode == Activity.RESULT_CANCELED) {
                if (null != callbackForCamera) {
                    callbackForCamera.onCancel("取消拍照");
                }
            }
        } catch (Exception e) {
            if (callbackForCamera != null) {
                callbackForCamera.onError(e);
            }
            if (mCallbackForGallery != null) {
                mCallbackForGallery.onError(e);
            }
            if (forImagePicker != null) {
                forImagePicker.onError(e);
            }

        }
    }


    /**
     * 生成拍照文件的路径
     */
    private String buildCameraPath() {
        String fileName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg";
        File folderFile = new File(Config.filePath);
        if (!folderFile.exists()) {
            folderFile.mkdir();
        }
        return Config.filePath + "/" + fileName;

    }

    /**
     * 将文件的path 异步保存到本地
     */
    private void saveCameraFilePath(String path) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(currActivity).edit();
        editor.putString(SPF_KEY_PATH, path).apply();
    }

    /**
     * 获取拍照文件的path
     */
    private String getCameraFilePath() {
        SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(currActivity);
        return spf.getString(SPF_KEY_PATH, "");
    }
}
