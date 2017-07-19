package com.ming.shao.kuling.ui.fragment;

import android.os.Bundle;
import android.view.View;

import com.ming.shao.kuling.R;
import com.ming.shao.kuling.base.BaseFragment;

/**
 * Created by smy on 2017/7/19 0019.
 */

public class MessageFragment extends BaseFragment{
    public static MessageFragment newInstance() {
        MessageFragment tabFragment = new MessageFragment();
        return tabFragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.message_fragment_layout;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

    }
}
