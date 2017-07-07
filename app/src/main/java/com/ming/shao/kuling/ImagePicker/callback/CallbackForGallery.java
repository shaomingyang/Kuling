package com.ming.shao.kuling.ImagePicker.callback;

/**
 * Created by smy on 2017/7/6 0006.
 */

public interface CallbackForGallery {
    void onError(Exception error);

    void onComplete(String imagePath);
}
