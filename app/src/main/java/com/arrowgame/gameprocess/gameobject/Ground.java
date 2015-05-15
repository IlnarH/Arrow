package com.arrowgame.gameprocess.gameobject;

import android.util.Log;

import com.arrowgame.gameprocess.animation.Animation;
import com.arrowgame.util.BitmapUtils;
import com.arrowgame.util.ResolutionUtils;

public class Ground extends Decoration {

    public Ground() {
        super(null, new Animation(BitmapUtils.getBitmap("ground")));
        initWidthAndHeight();
        position = new GamePoint(width / 2 - ResolutionUtils.GAME_SCREEN_WIDTH / 2,
                -height / 2);
    }

    @Override
    public boolean update(double factor) {
        return true;
    }
}
