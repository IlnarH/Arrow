package com.arrowgame.gameprocess.gameobject.balloons;

import com.arrowgame.gameprocess.animation.Animation;
import com.arrowgame.gameprocess.collider.CircleCollider;
import com.arrowgame.gameprocess.collider.Collider;
import com.arrowgame.gameprocess.effect.ActiveEffect;
import com.arrowgame.gameprocess.effect.Explosion;
import com.arrowgame.gameprocess.effect.PassiveEffect;
import com.arrowgame.gameprocess.gameobject.GamePoint;
import com.arrowgame.util.BitmapUtils;
import com.arrowgame.util.ResolutionUtils;

public class RedBalloon extends Balloon {

    public RedBalloon(GamePoint position) {
        super(position, ResolutionUtils.getScaledSize(31.5), new Animation(BitmapUtils.getBitmap("RedBalloon")),
                new PassiveEffect(position), new Explosion(position), new CircleCollider(position, 9), 1);
    }
}
