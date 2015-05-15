package com.arrowgame.shop.skins;

import com.arrowgame.gameprocess.animation.ArrowAnimation;
import com.arrowgame.util.BitmapUtils;

public class LanternEyes extends ArrowAnimation {

    public final static String ITEM_ID = "LANTERN_EYES";

    public LanternEyes() {
        super(-200, -5, SkinType.EYES);
        addBothFrames(BitmapUtils.getBitmap("ARROW_LANTERN_EYES_OPEN_RIGHT"),
                BitmapUtils.getBitmap("ARROW_LANTERN_EYES_OPEN_LEFT"), 90);
        addBothFrames(BitmapUtils.getBitmap("ARROW_LANTERN_EYES_CLOSE_RIGHT"),
                BitmapUtils.getBitmap("ARROW_LANTERN_EYES_CLOSE_LEFT"), 15);
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
