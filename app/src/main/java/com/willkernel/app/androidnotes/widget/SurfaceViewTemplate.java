package com.willkernel.app.androidnotes.widget;

/**
 * Created by willkernel on 2018/3/7.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class SurfaceViewTemplate extends SurfaceView implements SurfaceHolder.Callback, Runnable {

    private SurfaceHolder mSurfaceHolder;
    //绘图Canvas
    private Canvas mCanvas;
    //子线程标志位
    private boolean mIsDrawing;
    private Paint mPaint;
    private Paint mDrawPaint;
    private Path mDrawPath;
    private Path mPath;
    private int x, y;

    public SurfaceViewTemplate(Context context) {
        this(context, null);
    }

    public SurfaceViewTemplate(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SurfaceViewTemplate(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setKeepScreenOn(true);
//        mSurfaceHolder.setFormat(PixelFormat.OPAQUE);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPath = new Path();

        mDrawPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mDrawPaint.setColor(Color.RED);
        mDrawPaint.setStyle(Paint.Style.STROKE);
        mDrawPaint.setStrokeCap(Paint.Cap.ROUND);
        mDrawPaint.setStrokeWidth(5);
        mDrawPaint.setStrokeJoin(Paint.Join.ROUND);
        mDrawPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPath.moveTo(0, getHeight() / 2);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mIsDrawing = true;
        Log.e("surfaceCreated", Thread.currentThread().getName());
        new Thread(this).start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mIsDrawing = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDrawPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                mDrawPath.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

    @Override
    public void run() {
        while (mIsDrawing) {
            long start = System.currentTimeMillis();
            draw();
            long end = System.currentTimeMillis();
            //100是经验值，取值范围50-100ms
            if (end - start < 100) {
                try {
                    Thread.sleep(100 - (end - start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            x += 1;
            y = (int) (100 * Math.sin(x * 2 * Math.PI / 180) + 100);
            mPath.lineTo(x, y);
        }
    }

    private void draw() {
        try {
            //获得canvas图像
            mCanvas = mSurfaceHolder.lockCanvas();
            //surfaceview 背景
            mCanvas.drawColor(Color.WHITE);
            //绘制路径
            mCanvas.drawPath(mPath, mPaint);
            mCanvas.drawPath(mDrawPath, mDrawPaint);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //保证每次内容的提交
            if (mCanvas != null) mSurfaceHolder.unlockCanvasAndPost(mCanvas);
        }
    }
}