package com.willkernel.app.audiobar.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Random;


public class AudioBar extends View {
    private String TAG = "Audio";
    private int mCount = 6;
    private int mWidth;
    private int mRectWidth;
    private int offset=5;
    private Paint mPaint;
    private LinearGradient mLinearGradient;
    private Matrix mMatrix;
    private float mCurrentHeight;
    private float mRectHeight;
    private int mTranslate;

    public AudioBar(Context context) {
        this(context, null);
    }

    public AudioBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        Log.e("Audio", "mWidth=" + mWidth + "  w=" + w + "  h=" + h
                + "  oldw=" + oldw + "  oldh=" + oldh);
        mRectHeight = getHeight();
        mRectWidth = (int) (mWidth * 0.6 / mCount);
        mLinearGradient = new LinearGradient(0, 0, mRectWidth, mRectHeight, Color.YELLOW, Color.RED, Shader.TileMode.CLAMP);

        mMatrix = new Matrix();
        mPaint.setShader(mLinearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTranslate += mRectWidth / 5;
        if (mTranslate > 2 * mRectWidth) {
            mTranslate = -mRectWidth;
        }
        mMatrix.setTranslate(mTranslate, 0);
        mLinearGradient.setLocalMatrix(mMatrix);
        for (int i = 0; i < mCount; i++) {
            mCurrentHeight = (float) (mRectHeight * Math.random());
            canvas.drawRect((float) (mWidth * 0.2 + offset + mRectWidth * i), mCurrentHeight,
                    (float) (mWidth * 0.2 + mRectWidth * (i + 1)), mRectHeight, mPaint);
        }
        postInvalidateDelayed(300);
    }
}
