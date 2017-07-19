package com.ming.shao.kuling.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ming.shao.kuling.R;
import com.ming.shao.kuling.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smy on 2017/7/18 0018.
 */

public class SlidingTabLayout extends FrameLayout {
    private Context mContext;
    private LinearLayout mTabsContainer;
    private ArrayList<String> mTabEntitys = new ArrayList<>();
    //是否设置权重为1
    private boolean mTabSpaceEqual = true;

    private int mCurrentTab;
    private int mLastTab;
    private int mTabCount;
    //设置viewPager
    private ViewPager mViewPager;
    private PagerAdapter mPagerAdapter;
    OnTabSelectListener listener;

    public void setOnTabSelectListener(OnTabSelectListener listener) {
        this.listener = listener;
    }

    public SlidingTabLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {

        this.mContext = context;
        mTabsContainer = new LinearLayout(context);
        addView(mTabsContainer);
    }

    public void setTabData(List<String> tabEntitys) {
        if (tabEntitys == null || tabEntitys.size() == 0) {
            throw new IllegalStateException("TabEntitys can not be NULL or EMPTY !");
        }

        this.mTabEntitys.clear();
        this.mTabEntitys.addAll(tabEntitys);
        notifyDataSetChanged();
    }

    /**
     * 刷新view
     */
    private void notifyDataSetChanged() {
        if (mTabsContainer != null) {
            mTabsContainer.removeAllViews();
        }
        this.mTabCount = mTabEntitys.size();

        for (int i = 0; i < mTabCount; i++) {
            View tabView = LayoutInflater.from(mContext).inflate(R.layout.tab_view_layout, null);
            tabView.setTag(i);
            addTab(i, tabView);
        }
    }

    /**
     * 添加tab
     *
     * @param postion
     * @param view
     */
    private void addTab(int postion, View view) {
        //tab里面文字还有图片初始化赋值
        TextView tab_text = (TextView) view.findViewById(R.id.tab_title);
        tab_text.setText(mTabEntitys.get(postion));
        ImageView iv_tab_icon = (ImageView) view.findViewById(R.id.tab_image);
        iv_tab_icon.setImageResource(R.drawable.skin_tab_icon_contact_normal);
        //view的点击事件
        view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = (Integer) v.getTag();
                if (mCurrentTab != position) {//点击的是否是当前的tab 是
                    setCurrentTab(position);

                    if (null != listener) {
                        listener.onTabSelect(position);
                    }
                } else {//否
                    if (null != listener) {
                        listener.onTabReselect(position);
                    }
                }
            }
        });
        //判断view的父类是否为空 不为空删除所有的视图
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeAllViews();
        }

        /** 每一个Tab的布局参数 */
        LinearLayout.LayoutParams lp_tab = mTabSpaceEqual ?
                new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f) :
                new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);

        mTabsContainer.addView(view, postion, lp_tab);
    }

    /**
     * 设置当前的选中
     *
     * @param currentTab
     */
    public void setCurrentTab(int currentTab) {
        mLastTab = this.mCurrentTab;
        this.mCurrentTab = currentTab;
        updateViewBackgroud(currentTab);
    }

    /**
     * 改变view背景
     *
     * @param currentTab
     */
    private void updateViewBackgroud(int currentTab) {
        for (int i = 0; i < mTabCount; i++) {
            View tabView = mTabsContainer.getChildAt(i);
            final boolean isSelect = i == currentTab;
            TextView tabTextview = (TextView) tabView.findViewById(R.id.tab_title);
            tabTextview.setTextColor(isSelect ? mContext.getResources().getColor(R.color.yo_blue) : mContext.getResources().getColor(R.color.yo_bar_title_color));
            tabTextview.setText(mTabEntitys.get(i));

            ImageView iv_tab_icon = (ImageView) tabView.findViewById(R.id.tab_image);
            iv_tab_icon.setImageResource(isSelect ? R.drawable.skin_tab_icon_contact_selected : R.drawable.skin_tab_icon_contact_normal);
        }

    }

    /**
     * 设置viewPager
     */
    public void setupWithViewPager(@Nullable final ViewPager viewPager) {
        if (mViewPager != null && mPageChangeListener != null) {
            // If we've already been setup with a ViewPager, remove us from it
            mViewPager.removeOnPageChangeListener(mPageChangeListener);
        }

        if (viewPager != null) {
            final PagerAdapter adapter = viewPager.getAdapter();
            if (adapter == null) {
                throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
            }

            mViewPager = viewPager;
            // Add our custom OnPageChangeListener to the ViewPager
            if (mPageChangeListener == null) {
                mPageChangeListener = new TabLayoutOnPageChangeListener();
            }
            viewPager.addOnPageChangeListener(mPageChangeListener);
            // Now we'll add a tab selected listener to set ViewPager's current item
            setOnTabSelectListener(new ViewPagerOnTabSelectedListener(mViewPager));

            // Now we'll populate ourselves from the pager adapter
            mPagerAdapter = adapter;
        } else {
            // We've been given a null ViewPager so we need to clear out the internal state,
            // listeners and observers
            mViewPager = null;
            setOnTabSelectListener(null);
            mPagerAdapter = null;
        }
    }

    TabLayoutOnPageChangeListener mPageChangeListener;

    public class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Log.e("onPageScrolled", "===============:   " + position);
            setCurrentTab(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    }

    public static class ViewPagerOnTabSelectedListener implements OnTabSelectListener {
        private final ViewPager mViewPager;

        public ViewPagerOnTabSelectedListener(ViewPager viewPager) {
            mViewPager = viewPager;
        }

        @Override
        public void onTabReselect(int position) {

        }

        @Override
        public void onTabSelect(int position) {
            mViewPager.setCurrentItem(position);
        }
    }


}
