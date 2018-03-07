package com.willkernel.app.audiobar;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.willkernel.app.audiobar.widget.ColorMatrixUtil;
import com.willkernel.app.audiobar.widget.OverScrollListView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {


    private float mHue = 120, mSaturation = 1, mLum = 1;
    private ImageView filterImage;
    private Bitmap bm;
    private GridLayout gridLayout;
    private EditText[] mEts = new EditText[20];
    private float[] mColorMatrix = new float[20];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main15);
//        setListAdapter();
//        adjustColorFilter();
//        setColorMatrix();
//        getPixels();
//        drawRoundRect();
    }


    private void setListAdapter() {
        String[] contents = {"A", "B", "C", "D", "A", "B", "C", "D", "A", "B", "C", "D", "A", "B", "C", "D"};
        OverScrollListView listView = findViewById(R.id.listView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, contents);
        listView.setAdapter(arrayAdapter);
    }

    private void adjustColorFilter() {
        filterImage = findViewById(R.id.filterImage);
        bm = BitmapFactory.decodeResource(getResources(), R.mipmap.car2);
        SeekBar hueSeekBar = findViewById(R.id.hueSeekBar);
        SeekBar saturationSeekBar = findViewById(R.id.saturationSeekBar);
        SeekBar lumSeekBar = findViewById(R.id.lumSeekBar);

        hueSeekBar.setOnSeekBarChangeListener(this);
        saturationSeekBar.setOnSeekBarChangeListener(this);
        lumSeekBar.setOnSeekBarChangeListener(this);
    }

    private void setColorMatrix() {
        filterImage = findViewById(R.id.filterImage);
        bm = BitmapFactory.decodeResource(getResources(), R.mipmap.car2);
        gridLayout = findViewById(R.id.matrixGroup);
        addEts();
    }

    private void addEts() {
        gridLayout.post(new Runnable() {
            int mEtHeight;
            int mEtWidth;

            @Override
            public void run() {
                mEtHeight = gridLayout.getWidth() / 4;
                mEtWidth = gridLayout.getWidth() / 5;
                for (int i = 0; i < 20; i++) {
                    EditText editText = new EditText(getBaseContext());
                    gridLayout.addView(editText, mEtWidth, mEtHeight);
                    mEts[i] = editText;
                }
                initMatrix();
            }
        });
    }

    private void initMatrix() {
        for (int i = 0; i < mEts.length; i++) {
            if (i % 6 == 0) {
                mEts[i].setText("1");
            } else {
                mEts[i].setText("0");
            }
        }
    }

    private void getMatrix() {
        for (int i = 0; i < mEts.length; i++) {
            mColorMatrix[i] = Float.valueOf(mEts[i].getText().toString());
        }
    }

    private void setImageMatrix() {
        Bitmap bmp = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();
        ColorMatrix colorMatrix = new ColorMatrix(mColorMatrix);
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bm, 0, 0, paint);
        filterImage.setImageBitmap(bmp);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.hueSeekBar:
                mHue = (progress - 50) * 1.0f / 50 * 180;
                break;
            case R.id.saturationSeekBar:
                mSaturation = progress * 1.0f / 50;
                break;
            case R.id.lumSeekBar:
                mLum = progress * 1.0f / 50;
                break;
        }
        filterImage.setImageBitmap(ColorMatrixUtil.handleImageEffect(bm,
                mHue, mSaturation, mLum));
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    public void setMatrix(View view) {
        getMatrix();
        setImageMatrix();
    }

    public void resetMatrix(View view) {
        initMatrix();
        getMatrix();
        setImageMatrix();
    }

    private void getPixels() {
        ImageView pixelImage1 = findViewById(R.id.pixelImage1);
        ImageView pixelImage2 = findViewById(R.id.pixelImage2);
        ImageView pixelImage3 = findViewById(R.id.pixelImage3);

        bm = BitmapFactory.decodeResource(getResources(), R.mipmap.car3);
        int[] pixels = new int[bm.getWidth() * bm.getHeight()];
        int[] newPixels1 = new int[bm.getWidth() * bm.getHeight()];
        int[] newPixels2 = new int[bm.getWidth() * bm.getHeight()];
        int[] newPixels3 = new int[bm.getWidth() * bm.getHeight()];

        bm.getPixels(pixels, 0, bm.getWidth(), 0, 0, bm.getWidth(), bm.getHeight());
        Bitmap bmp1 = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap bmp2 = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap bmp3 = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), Bitmap.Config.ARGB_8888);
        for (int i = 0; i < pixels.length; i++) {
            int color = pixels[i];
            int r = Color.red(color);
            int g = Color.green(color);
            int b = Color.blue(color);
            int a = Color.alpha(color);

            //老照片
            int r1 = (int) (0.393 * r + 0.769 * g + 0.189 * b);
            int g1 = (int) (0.349 * r + 0.686 * g + 0.168 * b);
            int b1 = (int) (0.272 * r + 0.534 * g + 0.131 * b);
            newPixels1[i] = Color.rgb(r1, g1, b1);

            //底片处理
            int r2 = 255 - r;
            int g2 = 255 - g;
            int b2 = 255 - b;
            if (r > 255) {
                r = 255;
            } else if (r < 0) {
                r = 0;
            }
            if (g > 255) {
                g = 255;
            } else if (g < 0) {
                g = 0;
            }
            if (b > 255) {
                b = 255;
            } else if (b < 0) {
                b = 0;
            }
            newPixels2[i] = Color.argb(a, r2, g2, b2);

            //浮雕
            int rB = 0;
            int gB = 0;
            int bB = 0;
            if (i + 1 < pixels.length) {
                int colorB = pixels[i + 1];
                rB = Color.red(colorB);
                gB = Color.green(colorB);
                bB = Color.blue(colorB);

                rB = r - rB + 127;
                gB = g - gB + 127;
                bB = b - bB + 127;
                if (rB > 255) {
                    rB = 255;
                }
                if (gB > 255) {
                    gB = 255;
                }
                if (bB > 255) {
                    bB = 255;
                }
            }
            newPixels3[i] = Color.rgb(rB, gB, bB);
        }
        bmp1.setPixels(newPixels1, 0, bm.getWidth(),
                0, 0, bm.getWidth(), bm.getHeight());
        bmp2.setPixels(newPixels2, 0, bm.getWidth(),
                0, 0, bm.getWidth(), bm.getHeight());
        bmp3.setPixels(newPixels3, 0, bm.getWidth(),
                0, 0, bm.getWidth(), bm.getHeight());

        pixelImage1.setImageBitmap(bmp1);
        pixelImage2.setImageBitmap(bmp2);
        pixelImage3.setImageBitmap(bmp3);
    }

    private void drawRoundRect() {
        ImageView roundRectIv = findViewById(R.id.roundRectIv);
        Bitmap mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.car3);
        Bitmap mOut = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        PorterDuffXfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
        Canvas canvas = new Canvas(mOut);
        canvas.drawRoundRect(0, 0, mBitmap.getWidth(), mBitmap.getHeight(), 100, 100, mPaint);
        mPaint.setXfermode(mXfermode);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        roundRectIv.setImageBitmap(mOut);
    }
}