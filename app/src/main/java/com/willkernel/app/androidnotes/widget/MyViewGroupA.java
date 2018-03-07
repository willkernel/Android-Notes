package com.willkernel.app.androidnotes.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by willkernel on 2018/3/1.
 */

public class MyViewGroupA extends ViewGroup {
    public MyViewGroupA(Context context) {
        super(context);
    }

    public MyViewGroupA(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroupA(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("A","dispatchTouchEvent "+ev.getAction());
        Log.e("A"," super.dispatchTouchEvent(ev) "+ super.dispatchTouchEvent(ev));
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("A","onInterceptTouchEvent "+ev.getAction());
        Log.e("A","super.onInterceptTouchEvent(ev) "+super.onInterceptTouchEvent(ev));
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        Log.e("A","onTouchEvent "+ev.getAction());
        return super.onTouchEvent(ev);
    }
}
