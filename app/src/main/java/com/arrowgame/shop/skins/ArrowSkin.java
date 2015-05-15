package com.arrowgame.shop.skins;

import com.arrowgame.gameprocess.animation.ArrowAnimation;
import com.arrowgame.util.BitmapUtils;

public class ArrowSkin extends ArrowAnimation {

    public final static String ITEM_ID = "DEFAULT_ARROW";

    public ArrowSkin() {
        super(-12.5, -88, SkinType.BODY);
        addBothFrames(BitmapUtils.getBitmap("ARROW_SKIN"), BitmapUtils.getBitmap("ARROW_SKIN"), 0);
    }

    @Override
    public double getXOffset() {
        return xOffset;
    }
}
