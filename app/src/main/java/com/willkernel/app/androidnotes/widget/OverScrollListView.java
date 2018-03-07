package com.willkernel.app.androidnotes.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ListView;

/**
 * Created by willkernel on 2018/3/2.
 */

public class OverScrollListView extends ListView {
    private int mMaxOverDistance=60;

    public OverScrollListView(Context context) {
        this(context,null);
    }

    public OverScrollListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public OverScrollListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mMaxOverDistance= (int) (getResources().getDisplayMetrics().density*mMaxOverDistance);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        Log.e("overscroll","by "+mMaxOverDistance);
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, mMaxOverDistance, isTouchEvent);
    }
}
