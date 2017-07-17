package com.ming.shao.kuling.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by smy on 2017/7/12 0012.
 */

public abstract class BaseFragment extends Fragment {
    protected BaseActivity mActivity;

    //获取fragment布局文件ID
    protected abstract int getLayoutId();

    //封装抽象方法用于在fragment中初始化控件，增加代码的条理性
    protected abstract void initView(View view, Bundle savedInstanceState);

    //获取宿主Activity,不在使用getActivity()获取Activity对象
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (BaseActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);

        initView(view, savedInstanceState);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    /**
     * 显示吐司
     */
    public void showToast(String message) {
        ProjectApplication.showToast(message);
    }
}

