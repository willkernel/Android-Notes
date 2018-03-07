package com.willkernel.app.androidnotes.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by willkernel on 2018/3/2.
 */

public class DragView extends AppCompatTextView {
    private int lastX;
    private int lastY;
    private int lastrX;
    private int lastrY;
    private boolean isScroll;

    public DragView(Context context) {
        this(context, null);
    }

    public DragView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

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
                if (!isScroll) {
                    lastrX = rx;
                    lastrY = ry;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int dx = x - lastX;
                int dy = y - lastY;
                int rdx = rx - lastrX;
                int rdy = ry - lastrY;
                Log.e("drag", "x=" + x + " y=" + y
                        + " lastX=" + lastX + " lastY=" + lastY);
                Log.e("drag", "rx=" + rx + " ry=" + ry
                        + " lastrX=" + lastrX + " lastrY=" + lastrY);
                Log.e("drag", "dx=" + dx + " dy=" + dy
                        + " rdx=" + rdx + " rdy=" + rdy);
//            1.  layout(getLeft() + dx, getTop() + dy,
//                        getRight() + dx, getBottom() + dy);
//                lastrX = x;
//                lastrY = y;

//            2.  offsetLeftAndRight(dx);
//                offsetTopAndBottom(dy);

//                FrameLayout.LayoutParams layoutParams= (FrameLayout.LayoutParams) getLayoutParams();
//                layoutParams.leftMargin=getLeft()+dx;
//                layoutParams.topMargin=getTop()+dy;
//                setLayoutParams(layoutParams);

//                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) getLayoutParams();
//                layoutParams.leftMargin = getLeft() + dx;
//                layoutParams.topMargin = getTop() + dy;
//                setLayoutParams(layoutParams);

                //移动的是屏幕下方画布，会造成内容向相反方向移动
//                scrollBy(dx,dy);
//                ((View) getParent()).scrollBy(-dx, -dy);
                ((View) getParent()).scrollTo(-(int) event.getRawX() + lastrX, -(int) event.getRawY() + lastrY);
                isScroll = true;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
