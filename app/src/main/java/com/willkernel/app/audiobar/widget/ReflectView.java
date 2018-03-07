package com.willkernel.app.audiobar.widget;

/**
 * Created by willkernel on 2018/3/7.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.willkernel.app.audiobar.R;

public class ReflectView extends View {

    private Bitmap mSrcBitmap;
    private Bitmap mRefBitmap;
    private Paint mPaint;
    private PorterDuffXfermode mXferMode;

    public ReflectView(Context context) {
        this(context, null);
    }

    public ReflectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ReflectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mSrcBitmap= BitmapFactory.decodeResource(getResources(), R.mipmap.car3);
        Matrix matrix=new Matrix();
        //实项垂直翻转，(-1,1)实项水平翻转
        matrix.setScale(1,-1);
        mRefBitmap=Bitmap.createBitmap(mSrcBitmap,0,0,
                mSrcBitmap.getWidth(),mSrcBitmap.getHeight(),matrix,true);

        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setShader(new LinearGradient(0,mSrcBitmap.getHeight(),
                0,mSrcBitmap.getHeight()+mSrcBitmap.getHeight()/4,
                0xdd000000,0x33000000, Shader.TileMode.CLAMP));
        mXferMode=new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.BLACK);
        canvas.drawBitmap(mSrcBitmap,0,0,null);
        canvas.drawBitmap(mRefBitmap,0,mSrcBitmap.getHeight(),null);
        mPaint.setXfermode(mXferMode);
        canvas.drawRect(0,mSrcBitmap.getHeight(),mRefBitmap.getWidth(),
                mSrcBitmap.getHeight()*2,mPaint);
        mPaint.setXfermode(null);
    }
}