package com.arrowgame.gameprocess.effect;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

import com.arrowgame.gameprocess.GameManager;
import com.arrowgame.gameprocess.animation.Animation;
import com.arrowgame.gameprocess.gameobject.GamePoint;
import com.arrowgame.gameprocess.graphics.Camera;
import com.arrowgame.util.ResolutionUtils;
import com.arrowgame.util.Utils;

public abstract class Effect {

    public GamePoint position;

    Animation animation;

    double width;
    double height;

    Matrix matrix = new Matrix();

    public Effect(GamePoint position, Animation animation) {
        this.position = position;
        this.animation = animation;
    }

    public boolean update(double factor) {
        animation.update(factor);
        if (!checkVisible()) {
            remove();
            return false;
        }
        return true;
    }

    public void draw(Canvas canvas) {
        if (!Camera.getInstance().checkOnScreen(position)) return;

        matrix.reset();
        matrix.preTranslate((float) ResolutionUtils.getRelationalX(position.getX() - width / 2),
                (float) ResolutionUtils.getRelationalY(position.getY() - height / 2));
        canvas.drawBitmap(animation.getCurrentBitmap(), matrix, Utils.EFFECT_PAINT);
    }

    protected void initWidthAndHeight() {
        Bitmap bitmap = animation.getCurrentBitmap();
        this.width = bitmap.getWidth() / ResolutionUtils.getRESOLUTION_FACTOR();
        this.height = bitmap.getHeight() / ResolutionUtils.getRESOLUTION_FACTOR();
    }

    public boolean checkVisible() {
        if (position.getY() > Camera.getInstance().getBottomBorder() + 100) {
            return false;
        }
        return true;
    }

    public void add() {
        GameManager.getInstance().effects.add(this);
    }

    public void remove() {
        GameManager.getInstance().effects.remove(this);
    }
}