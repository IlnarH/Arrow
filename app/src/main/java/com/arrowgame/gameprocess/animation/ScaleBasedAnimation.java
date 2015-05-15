package com.arrowgame.gameprocess.animation;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class ScaleBasedAnimation extends Animation {

    List<Double> scales = new ArrayList<Double>();

    double currentScale = 0;

    public ScaleBasedAnimation(Bitmap bitmap) {
        super(bitmap);
    }

    public void addFrame(double scale, double duration) {
        scales.add(scale);
        durations.add(duration);
    }

    public void update(double factor) {
        super.update(factor);
        currentScale = scales.get(currentFrame);
    }

    public void addFrames(double startingScale, double scaleDelta, double duration, int count) {
        for (int i = 0; i < count; i++) {
            addFrame(startingScale + scaleDelta, duration);
            startingScale += scaleDelta;
        }
    }

    public double getCurrentScale() {
        return currentScale;
    }
}
