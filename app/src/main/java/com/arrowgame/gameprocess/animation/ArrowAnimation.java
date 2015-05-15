package com.arrowgame.gameprocess.animation;

import android.graphics.Bitmap;

import com.arrowgame.shop.skins.SkinType;
import com.arrowgame.shop.shopItems.Skin;
import com.arrowgame.util.ResolutionUtils;

public abstract class ArrowAnimation extends BitmapBasedAnimation implements Skin {

    protected double xOffset;
    protected double yOffset;
    protected boolean right = true;

    protected SkinType type;

    protected ArrowAnimation(double xOffset, double yOffset, SkinType type) {
        this.xOffset = ResolutionUtils.getScaledBitmapSize(xOffset);
        this.yOffset = ResolutionUtils.getScaledBitmapSize(yOffset);
        this.type = type;
    }

    public void addBothFrames(Bitmap rightBitmap, Bitmap leftBitmap, double duration) {
        bitmaps.add(leftBitmap);
        bitmaps.add(rightBitmap);
        durations.add(duration);
        currentBitmap = rightBitmap;
    }

    public void update(double factor, double rotation) {
        super.update(factor);
        if (rotation > 90 && rotation < 270) {
            currentBitmap = bitmaps.get(currentFrame * 2);
            right = false;
        } else {
            currentBitmap = bitmaps.get(currentFrame * 2 + 1);
            right = true;
        }
    }

    public double getXOffset() {
        if (right) {
            return xOffset;
        } else {
            return -xOffset;
        }
    }

    public double getYOffset() {
        return yOffset;
    }

    @Override
    public SkinType getType() {
        return type;
    }
}
