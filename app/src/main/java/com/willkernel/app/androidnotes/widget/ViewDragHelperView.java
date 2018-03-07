package com.willkernel.app.androidnotes.widget;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by willkernel on 2018/3/2.
 */

public class ViewDragHelperView extends FrameLayout {

    private ViewDragHelper mViewDragHelper;
    private View mMenuView, mMainView;
    private int mWidth, mHeight;

    private ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        /**重写tryCaptureView,何时开始检测触摸事件，
         * 当前触摸的是MainView时开始检测*/
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mMainView == child || mMenuView == child;
        }

        /**水平垂直方向上的滑动,默认返回0，不滑动*/
        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
//            return super.clampViewPositionHorizontal(child, left, dx);
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
//            return super.clampViewPositionVertical(child, top, dy);
            return top;
        }

//        @Override
//        public int getViewHorizontalDragRange(View child) {
//            return 1;
//        }

        /**拖动结束后调用，自动滑动打开或关闭菜单*/
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (mMainView.getTop() > mHeight / 2) {
                mViewDragHelper.smoothSlideViewTo(mMainView, 0, (int) (mHeight * 1.2f));
            } else {
                if (mMainView.getLeft() < mWidth / 2) {
                    //关闭菜单
                    mViewDragHelper.smoothSlideViewTo(mMainView, 0, 0);
                } else {
                    mViewDragHelper.smoothSlideViewTo(mMainView, (int) (mWidth * 1.2f), 0);
                }
            }
            ViewCompat.postInvalidateOnAnimation(ViewDragHelperView.this);
        }

        /**更改scale进行缩放*/
        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
        }

        /**状态改变*/
        @Override
        public void onViewDragStateChanged(int state) {
            super.onViewDragStateChanged(state);
        }

        /**触摸后回掉*/
        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
            if (capturedChild == mMenuView) {//当触摸的view是menu
                mViewDragHelper.captureChildView(mMainView, activePointerId);//交给main处理
            }
        }

//        @Override
//        public int getViewVerticalDragRange(View child) {
//            return getMeasuredHeight()-child.getMeasuredHeight();
//        }
    };

    public ViewDragHelperView(Context context) {
        this(context, null);
    }

    public ViewDragHelperView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ViewDragHelperView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = mMenuView.getMeasuredWidth();
        mHeight = mMenuView.getMeasuredHeight();
    }

    private void init() {
        mViewDragHelper = ViewDragHelper.create(this, callback);
//        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_RIGHT);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mMenuView = getChildAt(0);
        mMainView = getChildAt(1);
    }

    /**
     * 触摸事件传递给ViewDragHelper,必须写
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }

    /**
     * 重写事件拦截方法，把事件传递给ViewDragHelper
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        final int action = MotionEventCompat.getActionMasked(ev);
//        if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
//            mViewDragHelper.cancel();
//            return false;
//        }
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    /**
     * 平滑移动
     */
    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public void openMenu() {
        mViewDragHelper.smoothSlideViewTo(mMainView, mWidth, 0);
        ViewCompat.postInvalidateOnAnimation(ViewDragHelperView.this);
    }

    public void closeMenu() {
        mViewDragHelper.smoothSlideViewTo(mMainView, 0, 0);
        ViewCompat.postInvalidateOnAnimation(ViewDragHelperView.this);
    }
}