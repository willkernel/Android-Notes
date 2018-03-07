package com.willkernel.app.androidnotes.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import static android.graphics.Canvas.ALL_SAVE_FLAG;


public class Layers extends View {

    private Paint mPaint;
    private int mHeight, mWidth;

    public Layers(Context context) {
        this(context, null);
    }

    public Layers(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Layers(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLUE);
        canvas.drawCircle(mWidth / 2, mHeight / 2, 50, mPaint);

        canvas.saveLayerAlpha(0, 0, mWidth, mHeight, 127, ALL_SAVE_FLAG);
        mPaint.setColor(Color.RED);
        canvas.drawCircle(mWidth / 2 + 50, mHeight / 2 + 50, 50, mPaint);
        canvas.restore();
    }
}
