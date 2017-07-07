package com.ming.shao.kuling.constant;

import android.os.Environment;

/**
 * Created by smy on 2017/7/5 0005.
 */

public class Config {
    public static final String fileDirectory = "Kuling";
    public static final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + fileDirectory;
}
