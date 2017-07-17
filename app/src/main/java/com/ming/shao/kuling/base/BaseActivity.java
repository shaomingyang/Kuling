package com.ming.shao.kuling.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ming.shao.kuling.util.AppManager;

/**
 * Created by smy on 2017/7/12 0012.
 */

public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 设置布局文件
     *
     * @return
     */
    public abstract int getContentView();

    /**
     * 获取控件
     */
    protected abstract void initViews();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        initViews();
        AppManager.getAppManager().addActivity(this);

    }

    /**
     * 显示吐司
     */
    public void showToast(String message) {
        ProjectApplication.showToast(message);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }
}
