package com.ming.shao.kuling.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.ming.shao.kuling.base.ProjectApplication;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by smy on 2017/7/13 0013.
 * 侧滑菜单显示
 */

public class SideslipMenuLayout extends HorizontalScrollView {
    /**
     * 屏幕宽度
     */
    private int mScreenWidth;
    /**
     * 屏幕高度
     */
    private int mScreenHeight;
    /**
     * dp设置菜单距离左边的距离
     */
    private int mMenuRightPadding = 200;
    /**
     * 菜单的宽度
     */
    private int mMenuWidth;
    //菜单宽度的一半
    private int mHalfMenuWidth;
    //设置布局是否改变
    private boolean change = false;
    //是否打开菜单
    private boolean isOpen = false;
    //菜单
    ViewGroup menu;
    ViewGroup content;

    public SideslipMenuLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public SideslipMenuLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public SideslipMenuLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * 初始化数据
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        mScreenWidth = ProjectApplication.screenWidth;
        mScreenHeight = ProjectApplication.screenHeight;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        //测量一次
        if (!change) {
            LinearLayout wrapper = (LinearLayout) getChildAt(0);
            menu = (ViewGroup) wrapper.getChildAt(0);//菜单
            content = (ViewGroup) wrapper.getChildAt(1);//内容
            mMenuWidth = mScreenWidth - mMenuRightPadding;
            mHalfMenuWidth = mMenuWidth / 2;
            menu.getLayoutParams().width = mMenuWidth;//设置菜单的宽度
            content.getLayoutParams().width = mScreenWidth;//设置内容宽度

        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            // 将菜单隐藏
            this.scrollTo(mMenuWidth, 0);
            change = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX > mHalfMenuWidth) {
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                } else {
                    this.smoothScrollTo(0, 0);
                    isOpen = true;
                }

                return true;

        }
        return super.onTouchEvent(ev);
    }

    /**
     * 打开菜单
     */
    private void openMenu() {
        if (isOpen) {
            return;
        }
        this.smoothScrollTo(0, 0);
        isOpen = true;
    }

    /**
     * 关闭菜单
     */
    public void closeMenu() {
        if (isOpen) {
            this.smoothScrollTo(mMenuWidth, 0);
            isOpen = false;
        }
    }

    /**
     * 切换菜单状态
     */
    public void toggle() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        //这个是滑动过程中变化的值
        float scale = l * 1.0f / mMenuWidth;
        //（scale 从1到0 ），是不是哦了~
        float rightScale = 0.8f + scale * 0.2f;
        float leftScale = 1 - 0.3f * scale;
//        ViewHelper.setScaleX(menu, leftScale);
//        ViewHelper.setScaleY(menu, leftScale);
//        ViewHelper.setScaleX(content, rightScale);
//        ViewHelper.setScaleY(content, rightScale);
        ViewHelper.setAlpha(content, 0.6f + 0.4f * (scale));
        ViewHelper.setPivotX(content, 0);
        ViewHelper.setPivotY(content, content.getHeight() / 2);
    }
}
