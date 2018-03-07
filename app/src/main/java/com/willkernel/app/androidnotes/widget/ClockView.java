package com.willkernel.app.androidnotes.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by willkernel on 2018/3/6.
 */

public class ClockView extends View {

    private Paint mPaint;
    private int mWidth, mHeight;

    public ClockView(Context context) {
        this(context, null);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(2);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mWidth / 2, mHeight / 2,
                mWidth / 2, mPaint);

        for (int i = 0; i < 24; i++) {
            if (i == 0 || i == 6 || i == 12 | i == 18) {
                mPaint.setStrokeWidth(5);
                mPaint.setTextSize(30);
                canvas.drawLine(mWidth / 2, mHeight / 2 - mWidth / 2,
                        mWidth / 2, mHeight / 2 - mWidth / 2 + 60, mPaint);
                canvas.drawText(String.valueOf(i), mWidth / 2, mHeight / 2 - mWidth / 2 + 90, mPaint);
            } else {
                mPaint.setStrokeWidth(3);
                mPaint.setTextSize(16);
                canvas.drawLine(mWidth / 2, mHeight / 2 - mWidth / 2,
                        mWidth / 2, mHeight / 2 - mWidth / 2 + 30, mPaint);
                canvas.drawText(String.valueOf(i), mWidth / 2, mHeight / 2 - mWidth / 2 + 60, mPaint);
            }
            //旋转画布简化角度,坐标运算
            canvas.rotate(15, mWidth / 2, mHeight / 2);
        }

        canvas.save();

        mPaint.setColor(Color.CYAN);
        mPaint.setStrokeWidth(10);
        canvas.translate(mWidth / 2, mHeight / 2);
        canvas.drawLine(0, 0, 100, 100, mPaint);
        canvas.drawLine(0, 0, 100, 200, mPaint);

        canvas.restore();
    }
}