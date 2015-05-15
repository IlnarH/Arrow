package com.arrowgame.gameprocess.animation;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class Animation {


    List<Double> durations = new ArrayList<Double>();

    double currentTimeInFactors = 0;
    int currentFrame = 0;
    Bitmap currentBitmap = null;

    protected Animation() {
    }

    public Animation(Bitmap bitmap) {
        this.currentBitmap = bitmap;
    }

    public void update(double factor) {
        if (durations.isEmpty() || durations.size() == 1) return;
        currentTimeInFactors += factor;
        if (currentTimeInFactors - durations.get(currentFrame) >= 0) {
            currentTimeInFactors -= durations.get(currentFrame);
            currentFrame = (currentFrame + 1) % durations.size();
        }
    }

    public Bitmap getCurrentBitmap() {
        return currentBitmap;
    }
}