package com.arrowgame.gameprocess.gameobject.balloons;

import android.graphics.Canvas;

import com.arrowgame.gameprocess.GameManager;
import com.arrowgame.gameprocess.animation.Animation;
import com.arrowgame.gameprocess.collider.CircleCollider;
import com.arrowgame.gameprocess.collider.Collider;
import com.arrowgame.gameprocess.effect.ActiveEffect;
import com.arrowgame.gameprocess.effect.Effect;
import com.arrowgame.gameprocess.effect.PassiveEffect;
import com.arrowgame.gameprocess.gameobject.Arrow;
import com.arrowgame.gameprocess.gameobject.CollidingObject;
import com.arrowgame.gameprocess.gameobject.GamePoint;
import com.arrowgame.util.BitmapUtils;
import com.arrowgame.util.ResolutionUtils;
import com.arrowgame.util.Utils;

import java.util.List;

public abstract class Balloon extends CollidingObject {

    public static double lift = 0.047f;

    public static double rotationDelta = 3;
    private double additionalRotation = Utils.random.nextInt(10) - 5;
    private boolean additionalRotationDirection = true;

    public Balloon(GamePoint position, double bitmapDelta, Animation animation, Effect passiveEffect,
                   Effect activeEffect, Collider collider, double bonus) {
        super(position, bitmapDelta, animation, passiveEffect, activeEffect, collider, bonus);
        initWidthAndHeight();
    }



    @Override
    public boolean update(double factor) {
        if (additionalRotationDirection) {
            additionalRotation += factor * 0.1;
            if (additionalRotation > 10) {
                additionalRotationDirection = false;
            }
        } else {
            additionalRotation -= factor * 0.1;
            if (additionalRotation < -10) {
                additionalRotationDirection = true;
            }
        }
        if (!super.update(factor)) {
            return false;
        }
        if (!checkAlive()) {
            destroy();
            return false;
        }
        return true;
    }

    @Override
    public void add() {
        GameManager.getInstance().collidingObjects.add(this);
        if (passiveEffect != null) {
            passiveEffect.add();
        }
    }

    @Override
    protected void updateVelocity(double factor) {
        velocity.setY(velocity.getY() - lift * factor);
        super.updateVelocity(factor);
    }

    @Override
    public boolean checkAlive() {
        List<Arrow> arrows = GameManager.getInstance().arrows;
        for (Arrow arrow : arrows) {
            if (checkCollision(arrow.pikePosition)) {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void updateRotation() {
        double newRotation = 90;
        double dx = velocity.getX();

        if (dx > 0) {
            dx = Math.min(dx * 100, 60);
        } else {
            dx = Math.max(dx * 100, -60);
        }
        newRotation += dx;
        double delta = newRotation - rotation;
        if (Math.abs(delta) > rotationDelta) {
            rotation += delta > 0 ? rotationDelta : -rotationDelta;
        } else {
            rotation = newRotation;
        }
    }

    @Override
    public void draw(Canvas canvas) {
        matrix.reset();
        matrix.preTranslate((float) ResolutionUtils.getRelationalX(position.getX() - width / 2),
                (float) ResolutionUtils.getRelationalY(position.getY() - height / 2 + bitmapDelta));
        matrix.preRotate((float) (rotation + additionalRotation - 90), (float) ResolutionUtils.getRelationalLength(width / 2),
                (float) ResolutionUtils.getRelationalLength(height / 2 - bitmapDelta));

        canvas.drawBitmap(animation.getCurrentBitmap(), matrix, Utils.FILTERED_PAINT);
    }
}