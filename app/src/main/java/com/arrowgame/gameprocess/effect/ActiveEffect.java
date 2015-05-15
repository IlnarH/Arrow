package com.arrowgame.gameprocess.effect;

import com.arrowgame.gameprocess.animation.Animation;
import com.arrowgame.gameprocess.gameobject.GamePoint;
import com.arrowgame.util.BitmapUtils;

public class ActiveEffect extends Effect {

    public double durationInFactors;

    public ActiveEffect(GamePoint position) {
        super(position, new Animation(BitmapUtils.getBitmap("burst")));
        this.durationInFactors = 10;
        initWidthAndHeight();
    }

    @Override
    public boolean update(double factor) {
        if (!super.update(factor) || checkOver(factor)) {
            remove();
            return false;
        }
        return true;
    }

    public boolean checkOver(double factor) {
        durationInFactors -= factor;

        if (durationInFactors > 0) {
            return false;
        }
        return true;
    }
}
