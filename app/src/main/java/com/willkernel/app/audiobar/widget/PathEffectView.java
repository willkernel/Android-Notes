package com.willkernel.app.audiobar.widget;

/**
 * Created by willkernel on 2018/3/7.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.SumPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;


public class PathEffectView extends View {

    private Path mPath;
    private PathEffect[] mEffects;
    private Paint mPaint;

    public PathEffectView(Context context) {
        this(context, null);
    }

    public PathEffectView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PathEffectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mEffects=new PathEffect[7];
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPath=new Path();
        mPath.moveTo(0,0);
        for (int i = 0; i <=30; i++) {
            mPath.lineTo(i*35, (float) (Math.random()*100));
        }
        mEffects[0]=null;
        mEffects[1]=new CornerPathEffect(30);
        mEffects[2]=new DiscretePathEffect(5,3);
        mEffects[3]=new DashPathEffect(new float[]{10,20,30,50},0);
        Path path=new Path();
        path.addRect(0,0,10,10, Path.Direction.CCW);
        mEffects[4]=new PathDashPathEffect(path,12,0, PathDashPathEffect.Style.ROTATE);
        mEffects[5]=new ComposePathEffect(mEffects[3],mEffects[1]);
        mEffects[6]=new SumPathEffect(mEffects[3],mEffects[1]);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (PathEffect mEffect : mEffects) {
            mPaint.setPathEffect(mEffect);
            canvas.drawPath(mPath,mPaint);
            canvas.translate(0,200);
        }
    }
}