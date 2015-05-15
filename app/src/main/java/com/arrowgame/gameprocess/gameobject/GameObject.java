package com.arrowgame.gameprocess.gameobject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.arrowgame.gameprocess.animation.Animation;
import com.arrowgame.gameprocess.effect.ActiveEffect;
import com.arrowgame.gameprocess.effect.Effect;
import com.arrowgame.gameprocess.effect.PassiveEffect;
import com.arrowgame.gameprocess.graphics.Camera;
import com.arrowgame.util.PhysicsUtils;
import com.arrowgame.util.ResolutionUtils;
import com.arrowgame.util.Utils;

public abstract class GameObject {

    protected GamePoint position;
    protected GamePoint velocity = new GamePoint();

    //    bitmap width and height
    protected double width;
    protected double height;
    //    bitmapDelta represents the bitmaps' center (offset from object's actual position)
    protected double bitmapDelta;

    protected double rotation = 90;

    protected Effect passiveEffect;
    protected Effect activeEffect;

    protected Animation animation;

    protected Matrix matrix = new Matrix();

    protected GameObject(GamePoint position, double bitmapDelta, Animation animation,
                         Effect passiveEffect, Effect activeEffect) {
        this.position = position;
        this.bitmapDelta = bitmapDelta;
        this.animation = animation;
        this.passiveEffect = passiveEffect;
        this.activeEffect = activeEffect;
    }

    public boolean update(double factor) {
        updateVelocity(factor);
        updatePosition(factor);
        updateRotation();
        updateAnimation(factor);
        if (!checkVisible()) {
            remove();
            return false;
        }
        return true;
    }

    protected void updateVelocity(double factor) {
        velocity.y += PhysicsUtils.GRAVITY * factor;

        addAirFriction(factor);
    }

    protected abstract void updatePosition(double factor);

    protected void updateRotation() {
        rotation = velocity.computeRotation();
    }

    protected void addAirFriction(double factor) {
        double friction = PhysicsUtils.AIR_FRICTION * factor;

        velocity.x *= (1 - friction);
        velocity.y *= (1 - friction);
    }

    protected void updateAnimation(double factor) {
        animation.update(factor);
    }

    public void draw(Canvas canvas) {
//        if (!Camera.getInstance().checkOnScreen(position)) {
//            return;
//        }
        matrix.reset();
        matrix.preTranslate((float) ResolutionUtils.getRelationalX(position.x - width / 2),
                (float) ResolutionUtils.getRelationalY(position.y - height / 2 + bitmapDelta));
        matrix.preRotate((float) (rotation - 90), (float) ResolutionUtils.getRelationalLength(width / 2),
                (float) ResolutionUtils.getRelationalLength(height / 2 - bitmapDelta));

        canvas.drawBitmap(animation.getCurrentBitmap(), matrix, Utils.FILTERED_PAINT);
    }

    public void setVelocity(GamePoint velocity) {
        this.velocity = velocity;
    }

    public void addVelocity(double x, double y) {
        velocity.x += x;
        velocity.y += y;
    }

    public GamePoint getPosition() {
        return position;
    }

    public void setPosition(GamePoint position) {
        this.position = position;
    }

    protected void initWidthAndHeight() {
        Bitmap bitmap = animation.getCurrentBitmap();
        if (bitmap == null) { return; }
        this.width = bitmap.getWidth() / ResolutionUtils.getRESOLUTION_FACTOR();
        this.height = bitmap.getHeight() / ResolutionUtils.getRESOLUTION_FACTOR();
    }

    public boolean checkVisible() {
        return Camera.getInstance().checkOnScreen(position);
    }

    public abstract void add();

    public abstract void remove();

    public abstract void destroy();
}