package com.arrowgame.util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.arrowgame.R;

import java.util.Random;

public class Utils {

    public static Resources RESOURCES;
    public static LayoutInflater inflater;

    public final static double FORCE = 0.05f;

    public static Typeface FONT;

    public final static Random random = new Random();

    public static Paint FILTERED_PAINT;
    public static Paint EFFECT_PAINT;


    static {
        FILTERED_PAINT = new Paint();
        FILTERED_PAINT.setAntiAlias(true);
        FILTERED_PAINT.setFilterBitmap(true);
        FILTERED_PAINT.setAlpha(210);

        EFFECT_PAINT = new Paint();
        EFFECT_PAINT.setAntiAlias(true);
        EFFECT_PAINT.setFilterBitmap(true);
        EFFECT_PAINT.setAlpha(220);
    }

    public static double getFrameRateFactor(long millis) {
        return  millis / 15;
    }

    public static Bitmap makeScaledBitmap(int bitmapId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        Bitmap bitmap = BitmapFactory.decodeResource(RESOURCES, bitmapId, options);
        bitmap.setHasAlpha(true);

        bitmap = Bitmap.createScaledBitmap(bitmap, (int) ResolutionUtils.getScaledBitmapSize(bitmap.getWidth()),
                (int) ResolutionUtils.getScaledBitmapSize(bitmap.getHeight()), false);

        return bitmap;
    }

    public static Bitmap makeScaledBitmap(int bitmapId, double scale) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        Bitmap bitmap = BitmapFactory.decodeResource(RESOURCES, bitmapId, options);
        bitmap.setHasAlpha(true);

        bitmap = Bitmap.createScaledBitmap(bitmap, (int) (ResolutionUtils.getScaledBitmapSize(bitmap.getWidth()) * scale),
                (int) (ResolutionUtils.getScaledBitmapSize(bitmap.getHeight()) * scale), false);

        return bitmap;
    }

    public static Bitmap getScaledBitmap(Bitmap bitmap, double scale) {
        return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * scale),
                (int) (bitmap.getHeight() * scale), false);
    }

    public static Bitmap getFlippedBitmap(Bitmap bitmap) {
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, false);
    }
}