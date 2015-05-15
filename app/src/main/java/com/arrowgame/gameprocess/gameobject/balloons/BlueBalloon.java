package com.arrowgame.gameprocess.gameobject.balloons;

import com.arrowgame.gameprocess.animation.Animation;
import com.arrowgame.gameprocess.animation.BitmapBasedAnimation;
import com.arrowgame.gameprocess.collider.CircleCollider;
import com.arrowgame.gameprocess.effect.ElectricField;
import com.arrowgame.gameprocess.effect.ElectricVortex;
import com.arrowgame.gameprocess.gameobject.GamePoint;
import com.arrowgame.util.BitmapUtils;
import com.arrowgame.util.ResolutionUtils;

/**
 * Created by Ильнар on 31.01.2015.
 */
public class BlueBalloon extends Balloon {

    private Animation inactiveAnimation;
    private BitmapBasedAnimation activeAnimation;

    public BlueBalloon(GamePoint position) {
        super(position, ResolutionUtils.getScaledSize(45), new Animation(BitmapUtils.getBitmap("BlueBalloon_1")),
                new ElectricField(position), new ElectricVortex(position), new CircleCollider(position, 13), 1);
        inactiveAnimation = animation;
        activeAnimation = new BitmapBasedAnimation();
        activeAnimation.addFrame(BitmapUtils.getBitmap("BlueBalloon_2"), 2);
        activeAnimation.addFrame(BitmapUtils.getBitmap("BlueBalloon_1"), 4);
        activeAnimation.addFrame(BitmapUtils.getBitmap("BlueBalloon_3"), 2);
        activeAnimation.addFrame(BitmapUtils.getBitmap("BlueBalloon_1"), 4);
    }

    @Override
    public boolean update(double factor) {
        if (((ElectricField) passiveEffect).isActive()) {
            animation = activeAnimation;
        } else {
            animation = inactiveAnimation;
        }
        return super.update(factor);
    }
}
