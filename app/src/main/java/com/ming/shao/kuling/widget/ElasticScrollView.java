package com.ming.shao.kuling.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by smy on 2017/7/17 0017.
 * 弹性的ScrollView
 */

public class ElasticScrollView extends ScrollView {
    ViewGroup inner;
    //保存正常时候的位置
    private Rect normal = new Rect();
    private int mScaledTouchSlop;


    public ElasticScrollView(Context context) {
        super(context);
        init(context);
    }

    public ElasticScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ElasticScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        //创建辅助对象 包含了方法和标准的常量用来设置UI的超时、大小和距离
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        mScaledTouchSlop = viewConfiguration.getScaledTouchSlop();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            inner = (ViewGroup) getChildAt(0);
        }
    }

    float movY;
    float lastY;

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = 0;
                break;
            case MotionEvent.ACTION_MOVE:
                final float preY = lastY == 0 ? ev.getY() : lastY;
                movY = ev.getY();
                int distans = (int) (preY - movY);

                if (normal.isEmpty()) {
                    normal.set(inner.getLeft(), inner.getTop(), inner.getRight(), inner.getBottom());
                }
                inner.layout(inner.getLeft(), inner.getTop() - distans / 4, inner.getRight(), inner.getBottom() - distans / 4);

                lastY = movY;

                break;
            case MotionEvent.ACTION_UP:
                lastY = 0;
                //获取正常的位置 设置原来的位置
                inner.layout(normal.left, normal.top, normal.right, normal.bottom);
                break;
        }

        return super.onTouchEvent(ev);


    }


}
