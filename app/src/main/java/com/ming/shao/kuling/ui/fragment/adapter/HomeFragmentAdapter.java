package com.ming.shao.kuling.ui.fragment.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ming.shao.kuling.base.BaseFragment;
import com.ming.shao.kuling.ui.fragment.HomeFragment;
import com.ming.shao.kuling.ui.fragment.MessageFragment;
import com.ming.shao.kuling.ui.fragment.OtherFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smy on 2017/7/19 0019.
 */

public class HomeFragmentAdapter extends FragmentPagerAdapter {
    List<String> titleStr = new ArrayList<>();

    public HomeFragmentAdapter(FragmentManager fm, List<String> titleStr) {
        super(fm);
        this.titleStr = titleStr;
    }

    @Override
    public Fragment getItem(int position) {
        //新建一个Fragment来展示ViewPager item的内容，并传递参数
        HomeFragment homeFragment = HomeFragment.newInstance();
        MessageFragment messageFragment = MessageFragment.newInstance();
        OtherFragment otherFragment = OtherFragment.newInstance();
        List<BaseFragment> list = new ArrayList<>();
        list.add(homeFragment);
        list.add(messageFragment);
        list.add(otherFragment);
        return list.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleStr.get(position % titleStr.size());
    }

    @Override
    public int getCount() {
        return null == titleStr ? 0 : titleStr.size();
    }

}
