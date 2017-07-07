package com.ming.shao.kuling.ImagePicker.callback;

/**
 * Created by smy on 2017/7/5 0005.
 */

public interface CallbackForCamera {
    void onError(Exception error);

    void onComplete(String imagePath);

    void onCancel(String imagePath);
}
