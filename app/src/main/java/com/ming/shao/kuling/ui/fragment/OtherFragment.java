package com.ming.shao.kuling.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.ming.shao.kuling.R;
import com.ming.shao.kuling.base.BaseFragment;

/**
 * Created by smy on 2017/7/19 0019.
 */

public class OtherFragment extends BaseFragment {
    public static OtherFragment newInstance() {
        OtherFragment tabFragment = new OtherFragment();
        return tabFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.other_fragment_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }
}
