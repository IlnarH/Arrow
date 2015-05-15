package com.arrowgame.gameprocess.animation;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class BitmapBasedAnimation extends Animation {

    List<Bitmap> bitmaps = new ArrayList<Bitmap>();

    public void addFrame(Bitmap bitmap, double duration) {
        bitmaps.add(bitmap);
        durations.add(duration);
        currentBitmap = bitmap;
    }

    public void update(double factor) {
        super.update(factor);
        currentBitmap = bitmaps.get(currentFrame);
    }
}
