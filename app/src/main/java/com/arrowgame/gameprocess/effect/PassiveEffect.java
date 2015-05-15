package com.arrowgame.gameprocess.effect;

import android.graphics.Canvas;

import com.arrowgame.gameprocess.GameManager;
import com.arrowgame.gameprocess.animation.ScaleBasedAnimation;
import com.arrowgame.gameprocess.collider.CircleCollider;
import com.arrowgame.gameprocess.gameobject.Arrow;
import com.arrowgame.gameprocess.gameobject.CollidingObject;
import com.arrowgame.gameprocess.gameobject.GamePoint;
import com.arrowgame.gameprocess.graphics.Camera;
import com.arrowgame.util.BitmapUtils;
import com.arrowgame.util.ResolutionUtils;
import com.arrowgame.util.Utils;

import java.util.List;

public class PassiveEffect extends Effect {

    double actionRadius = 40;
    double force = 0.15;

    public PassiveEffect(GamePoint position) {
        super(position, new ScaleBasedAnimation(BitmapUtils.getBitmap("aura")));

        ((ScaleBasedAnimation) animation).addFrames(1, 0.05, 4, 7);
        ((ScaleBasedAnimation) animation).addFrames(1.35, -0.05, 4, 7);

        initWidthAndHeight();
    }

    public boolean update(double factor) {
        List<CollidingObject> objects = GameManager.getInstance().collidingObjects;

        for (CollidingObject object : objects) {

            GamePoint p1 = position;
            GamePoint p2 = object.getPosition();

            if (Math.abs(p2.getX() - p1.getX()) > actionRadius) continue;
            if (Math.abs(p2.getY() - p1.getY()) > actionRadius) continue;

            double distance = GamePoint.getLength(p1, p2);
            if (distance == 0) continue;
            if (distance > actionRadius) continue;

            object.addVelocity((p2.getX() - p1.getX()) / distance * force * factor, (p2.getY() - p1.getY()) / distance * force * factor);
        }

        for (Arrow arrow : GameManager.getInstance().arrows) {
            GamePoint p1 = position;
            GamePoint p2 = arrow.getPosition();

            double distance = GamePoint.getLength(p1, p2);
            if (distance == 0) continue;
            if (distance > actionRadius) continue;

            arrow.addVelocity((p2.getX() - p1.getX()) / distance * force * factor, (p2.getY() - p1.getY()) / distance * force * factor);
        }

        return super.update(factor);
    }

    @Override
    public void draw(Canvas canvas) {
        if (!Camera.getInstance().checkOnScreen(position)) return;

        matrix.reset();
        ScaleBasedAnimation scaleBasedAnimation = (ScaleBasedAnimation) animation;
        matrix.preTranslate((float) ResolutionUtils.getRelationalX(position.getX() - width / 2 * scaleBasedAnimation.getCurrentScale()),
                (float) ResolutionUtils.getRelationalY(position.getY() - height / 2 * scaleBasedAnimation.getCurrentScale()));
        matrix.preScale((float) scaleBasedAnimation.getCurrentScale(), (float) scaleBasedAnimation.getCurrentScale());
        canvas.drawBitmap(animation.getCurrentBitmap(), matrix, Utils.EFFECT_PAINT);
    }
}
