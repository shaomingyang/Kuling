package com.ming.shao.kuling.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.ming.shao.kuling.R;
import com.ming.shao.kuling.base.BaseFragment;

/**
 * Created by smy on 2017/7/19 0019.
 */

public class HomeFragment extends BaseFragment {
    public static HomeFragment newInstance() {
        HomeFragment tabFragment = new HomeFragment();
        return tabFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_fragment_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }
}
