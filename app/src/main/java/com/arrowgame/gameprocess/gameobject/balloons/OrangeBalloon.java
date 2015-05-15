package com.arrowgame.gameprocess.gameobject.balloons;

import com.arrowgame.gameprocess.animation.Animation;
import com.arrowgame.gameprocess.collider.CircleCollider;
import com.arrowgame.gameprocess.effect.ActiveEffect;
import com.arrowgame.gameprocess.effect.PassiveEffect;
import com.arrowgame.gameprocess.gameobject.GamePoint;
import com.arrowgame.util.BitmapUtils;
import com.arrowgame.util.ResolutionUtils;

public class OrangeBalloon extends Balloon {

    public OrangeBalloon(GamePoint position) {
        super(position, ResolutionUtils.getScaledSize(31.5), new Animation(BitmapUtils.getBitmap("OrangeBalloon")),
                null, new ActiveEffect(position), new CircleCollider(position, 9), 1);
    }
}
