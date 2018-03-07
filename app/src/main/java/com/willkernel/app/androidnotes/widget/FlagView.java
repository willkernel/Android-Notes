package com.willkernel.app.androidnotes.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.willkernel.app.androidnotes.R;

/**
 * Created by willkernel on 2018/3/6.
 */

public class FlagView extends View {

    private float[] orig, verts;
    int HEIGHT = 100, WIDTH = 100;
    //振幅
    private float A = 20;
    private Bitmap bitmap;
    private float k;

    public FlagView(Context context) {
        this(context, null);
    }

    public FlagView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.car3);
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        int index = 0;

        orig = new float[bitmapHeight * bitmapWidth];
        verts = new float[bitmapHeight * bitmapWidth];
        for (int y = 0; y <= HEIGHT; y++) {
            float fy = bitmapHeight * y / HEIGHT;
            for (int x = 0; x <= WIDTH; x++) {
                float fx = bitmapWidth * x / WIDTH;
                orig[index * 2 + 0] = verts[index * 2 + 0] = fx;
                //+100 避免被遮挡
                orig[index * 2 + 1] = verts[index * 2 + 1] = fy + 100;
                index += 1;
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        flagWave();
        k += 0.1f;
        canvas.drawBitmapMesh(bitmap, WIDTH, HEIGHT, verts,
                0, null, 0, null);
        invalidate();
    }

    private void flagWave() {
        for (int j = 0; j <= HEIGHT; j++) {
            for (int i = 0; i <= WIDTH; i++) {
                verts[(j * (WIDTH + 1) + i) * 2 + 0] += 0;
                //图像动起来，纵坐标周期性变化
                float offsetY = (float) Math.sin((float) i / WIDTH * 2 * Math.PI + Math.PI * k);
                verts[(j * (WIDTH + 1) + i) * 2 + 1] = orig[(j * WIDTH + i) * 2 + 1] + offsetY * A;
            }
        }
    }
}