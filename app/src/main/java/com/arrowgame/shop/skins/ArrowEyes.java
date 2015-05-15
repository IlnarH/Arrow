package com.arrowgame.shop.skins;

import com.arrowgame.gameprocess.animation.ArrowAnimation;
import com.arrowgame.util.BitmapUtils;

public class ArrowEyes extends ArrowAnimation {

    public final static String ITEM_ID = "DEFAULT_EYES";

    public ArrowEyes() {
        super(14, -5, SkinType.EYES);
        addBothFrames(BitmapUtils.getBitmap("ARROW_EYES_OPEN_RIGHT"), BitmapUtils.getBitmap("ARROW_EYES_OPEN_LEFT"), 45);
        addBothFrames(BitmapUtils.getBitmap("ARROW_EYES_CLOSE_RIGHT"), BitmapUtils.getBitmap("ARROW_EYES_CLOSE_LEFT"), 15);
    }

    @Override
    public double getXOffset() {
        if (right) {
            return xOffset;
        } else {
            return -xOffset - getCurrentBitmap().getWidth();
        }
    }
}
