package com.arrowgame.gameprocess.gameobject;

import com.arrowgame.gameprocess.GameManager;
import com.arrowgame.gameprocess.animation.Animation;
import com.arrowgame.gameprocess.collider.Collider;
import com.arrowgame.gameprocess.effect.ActiveEffect;
import com.arrowgame.gameprocess.effect.Effect;
import com.arrowgame.gameprocess.effect.PassiveEffect;
import com.arrowgame.util.ResolutionUtils;

public abstract class CollidingObject extends GameObject {

    public Collider collider;

    public double bonus;

    public CollidingObject(GamePoint position, double bitmapDelta, Animation animation,
                           Effect passiveEffect, Effect activeEffect, Collider collider, double bonus) {
        super(position, bitmapDelta, animation, passiveEffect, activeEffect);
        this.collider = collider;
        this.bonus = bonus;
    }

    @Override
    protected void updatePosition(double factor) {
        double px = position.x;
        double py = position.y;

        position.x += velocity.x * factor;
        position.y += velocity.y * factor;

        if (position.x < collider.getWidth() / 2) {
            position.x = collider.getWidth() / 2;
            velocity.x = - velocity.x;
        } else if (position.x > ResolutionUtils.GAME_SCREEN_WIDTH - collider.getWidth() / 2) {
            position.x = ResolutionUtils.GAME_SCREEN_WIDTH - collider.getWidth() / 2;
            velocity.x = -velocity.x;
        }

//        collision for objects with passive effects
        /*if (passiveEffect != null) {
            if (!(position.x == px && position.y == py)) {
                List<CollidingObject> objects = GameManager.getInstance().collidingObjects;
                for (CollidingObject object : objects) {
                    if (checkCollision(object)) {
                        position.x = px;
                        position.y = py;
                        break;
                    }
                }
            }
        }*/

        if (px == position.x) {
            velocity.x = 0;
        }
        if (py == position.y) {
            velocity.y = 0;
        }
    }

    public boolean checkCollision(GamePoint gamePoint) {
        return collider.checkCollision(gamePoint);
    }

    public boolean checkCollision(CollidingObject object) {
        if (this == object) {
            return false;
        }
        return this.collider.checkCollision(object.collider);
    }

    public abstract boolean checkAlive();

    @Override
    public void add() {
        GameManager.getInstance().collidingObjects.add(this);
    }
    @Override
    public void remove() {
        GameManager.getInstance().collidingObjects.remove(this);
        if (passiveEffect != null) {
            passiveEffect.remove();
        }
    }
    @Override
    public void destroy() {
        remove();
        if (activeEffect != null) {
            activeEffect.add();
        }
        GameManager.getInstance().addBonusTime(bonus);
    }
}
