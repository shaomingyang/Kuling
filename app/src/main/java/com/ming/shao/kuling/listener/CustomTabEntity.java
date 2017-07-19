package com.ming.shao.kuling.listener;

import android.support.annotation.DrawableRes;

/**
 * Created by smy on 2017/7/18 0018.
 */

public interface CustomTabEntity {
    String getTabTitle();

    @DrawableRes
    int getTabSelectedIcon();

    @DrawableRes
    int getTabUnselectedIcon();
}
