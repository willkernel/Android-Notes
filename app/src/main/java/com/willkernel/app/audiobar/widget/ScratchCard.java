package com.willkernel.app.audiobar.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.willkernel.app.audiobar.R;

/**
 * Created by willkernel on 2018/3/7.
 */

public class ScratchCard extends View {

    private Path mPath;
    private Canvas mCanvas;
    private Paint mPaint;
    private Bitmap bgBmp, fgBmp;

    public ScratchCard(Context context) {
        this(context, null);
    }

    public ScratchCard(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScratchCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bgBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.car3);
        fgBmp = Bitmap.createBitmap(bgBmp.getWidth(), bgBmp.getHeight(), Bitmap.Config.ARGB_8888);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //画路径时走透明通道，形成刮刮卡效果
        mPaint.setAlpha(0);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(50);
        mCanvas = new Canvas(fgBmp);
        mCanvas.drawColor(Color.GRAY);
        mPath = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.reset();
                mPath.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                break;
        }
        mCanvas.drawPath(mPath, mPaint);
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bgBmp, 0, 0, null);
        canvas.drawBitmap(fgBmp, 0, 0, null);
    }
}