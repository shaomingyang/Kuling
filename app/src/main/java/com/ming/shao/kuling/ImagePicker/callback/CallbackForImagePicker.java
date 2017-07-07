package com.ming.shao.kuling.ImagePicker.callback;

import java.util.List;

/**
 * Created by smy on 2017/7/5 0005.
 */

public interface CallbackForImagePicker {
    void onError(Exception error);

    void onComplete(List<String> imagePath);
}
