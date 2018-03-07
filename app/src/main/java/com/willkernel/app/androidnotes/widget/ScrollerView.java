package com.willkernel.app.androidnotes.widget;


import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by willkernel on 2018/3/2.
 */

public class ScrollerView extends View {

    private Scroller mScroller;
    private int lastX, lastY;
    private int lastRx, lastRy;

    public ScrollerView(Context context) {
        this(context, null);
    }

    public ScrollerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        //判断Scroll是否执行完毕，true 没有执行玩
        if (mScroller.computeScrollOffset()) {
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //只能通过invalidate()—>draw()->computeScroll()间接调用computeScroll()，
            //结束后computeScrollOffset()返回false，中断循环
            postInvalidate();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        int rx = (int) event.getRawX();
        int ry = (int) event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = x;
                lastY = y;
                lastRx = rx;
                lastRy = ry;
                break;
            case MotionEvent.ACTION_MOVE:
                ((View) getParent()).scrollTo(-rx + lastRx, -ry + lastRy);
                break;
            case MotionEvent.ACTION_UP:
                View parent = (View) getParent();
                //模拟滑动，起始坐标和偏移量，滑动偏移为滑动距离的负数，相反方向滑动
                mScroller.startScroll(parent.getScrollX(), parent.getScrollY(),
                        -parent.getScrollX(), -parent.getScrollY());
                //通知重绘
                invalidate();
                break;
        }
        return true;
    }
}