package com.willkernel.app.audiobar.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.OverScroller;

/**
 * Created by willkernel on 2018/3/1.
 */

public class ScrollStickyView extends ViewGroup {
    private OverScroller mScroller;
    private int mScreenHeight;
    private int mStart;
    private int mEnd;
    private float mLastY;

    public ScrollStickyView(Context context) {
        this(context, null);
    }

    public ScrollStickyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollStickyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mScroller = new OverScroller(getContext());
        mScreenHeight = getResources().getDisplayMetrics().heightPixels;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        //获取整个ViewGroup高度
        MarginLayoutParams layoutParams = (MarginLayoutParams) getLayoutParams();
        layoutParams.height = childCount * mScreenHeight;
        setLayoutParams(layoutParams);
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                child.layout(l, mScreenHeight * i, r, mScreenHeight * (i + 1));
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY =   event.getY();
                //起点
                mStart = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }
                float dy = mLastY - event.getY();
                Log.d("Scroll", "======" );
                Log.d("Scroll", "dy = " + dy);
                Log.d("Scroll", "getScrollY() = " + getScrollY());
                Log.d("Scroll", "getHeight()  = " + getHeight());
                Log.d("Scroll", "mScreenHeight()  = " + mScreenHeight);
                Log.d("Scroll", "getHeight() - mScreenHeight = " + (getHeight() - mScreenHeight));
                Log.d("Scroll", "mLastY = " + mLastY);
                Log.d("Scroll", "mStart = " + mStart);

                if (getScrollY() < 0) {
                    //最顶端，超过0时，不再下拉，要是不设置这个，getScrollY一直是负数
                    dy = 0;
                }
                if (getScrollY() > getHeight() - mScreenHeight) {
                    //滑到最底端时，不再滑动，要是不设置这个，getScrollY一直是大于getHeight() - mScreenHeight的数
                    dy = 0;
                }
                scrollBy(0, (int) dy);
                //不断的设置Y，在滑动的时候子view就会比较顺畅
                mLastY =  event.getY();
                break;
            case MotionEvent.ACTION_UP:
                mEnd = getScrollY();
                int dScrollY = mEnd - mStart;
                //向上，向下滑动，超过1/3屏幕高度，结束滑动时滚动到下个位置，没有超过时复位
                if (dScrollY > 0) {
                    if (dScrollY < mScreenHeight / 3) {
                        mScroller.startScroll(0, mEnd,
                                0, -dScrollY, 200);
                    } else {
                        mScroller.startScroll(0, mEnd, 0,
                                mScreenHeight - dScrollY, 200);
                    }
                } else {
                    if (-dScrollY < mScreenHeight / 3) {
                        mScroller.startScroll(0, mEnd,
                                0, -dScrollY, 200);
                    } else {
                        mScroller.startScroll(0, mEnd,
                                0, -(mScreenHeight + dScrollY), 200);
                    }
                }
                break;
        }
        // 重绘执行computeScroll()
        postInvalidate();
        //需要返回true否则down后无法执行move和up操作
        return true;
    }

    /**
     * Scroller只是个计算器，提供插值计算，让滚动过程具有动画属性，
     * 但它并不是UI，也不是滑动辅助UI运动，反而是单纯地为滑动提供计算
     * 需要invalidate()之后才会调用,这个方法在onDraw()中调用
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }
}
