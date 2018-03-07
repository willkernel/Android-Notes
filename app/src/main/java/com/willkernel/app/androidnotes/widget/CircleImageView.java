package com.willkernel.app.androidnotes.widget;

/**
 * Created by willkernel on 2018/3/7.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.willkernel.app.androidnotes.R;

public class CircleImageView extends AppCompatImageView {

    private Bitmap bm;
    private BitmapShader bitmapShader;
    private int mHeight, mWidth;
    private Paint mPaint;
    private LinearGradient mLinearGradient;

    public CircleImageView(Context context) {
        this(context, null);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        bm = BitmapFactory.decodeResource(getResources(), R.mipmap.car3);
        //图像填充功能
        bitmapShader = new BitmapShader(bm, Shader.TileMode.CLAMP, Shader.TileMode.REPEAT);
        mPaint = new Paint();
        mPaint.setShader(bitmapShader);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mLinearGradient=new LinearGradient(100,300,300,500, Color.RED,Color.YELLOW, Shader.TileMode.CLAMP);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(200, 200, 100, mPaint);
        mPaint.setShader(mLinearGradient);
        canvas.drawRect(100,300,300,500,mPaint);
    }
}
