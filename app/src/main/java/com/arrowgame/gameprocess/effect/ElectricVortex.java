package com.arrowgame.gameprocess.effect;

import android.graphics.Canvas;
import android.util.Log;

import com.arrowgame.gameprocess.GameManager;
import com.arrowgame.gameprocess.animation.Animation;
import com.arrowgame.gameprocess.animation.BitmapBasedAnimation;
import com.arrowgame.gameprocess.gameobject.Arrow;
import com.arrowgame.gameprocess.gameobject.CollidingObject;
import com.arrowgame.gameprocess.gameobject.GamePoint;
import com.arrowgame.gameprocess.graphics.Camera;
import com.arrowgame.util.BitmapUtils;
import com.arrowgame.util.ResolutionUtils;
import com.arrowgame.util.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ильнар on 01.02.2015.
 */
public class ElectricVortex extends Effect {

    private double duration = 100;
    private static double actionRadius = 108;
    private static double force = -4;

    private BitmapBasedAnimation staticAnimation = new BitmapBasedAnimation();

    private List<GamePoint> targets = new ArrayList<GamePoint>();

    public ElectricVortex(GamePoint position) {
        super(position, new BitmapBasedAnimation());
        ((BitmapBasedAnimation) animation).addFrame(BitmapUtils.getBitmap("electric_whip_1"), 2);
        ((BitmapBasedAnimation) animation).addFrame(BitmapUtils.getBitmap("electric_whip_2"), 2);
        staticAnimation.addFrame(BitmapUtils.getBitmap("electric_field_1"), 2);
        staticAnimation.addFrame(BitmapUtils.getBitmap("electric_field_2"), 2);
        initWidthAndHeight();
    }

    @Override
    public boolean update(double factor) {

        duration -= factor;
        if (duration < 0) {
            remove();
            return false;
        }
        action(factor);
        staticAnimation.update(factor);

        return super.update(factor);
    }

    private void addTarget(GamePoint point) {
        targets.add(point);
    }

    private void action(double factor) {
        List<CollidingObject> objects = GameManager.getInstance().collidingObjects;

        for (CollidingObject object : objects) {

            GamePoint p1 = position;
            GamePoint p2 = object.getPosition();

            if (Math.abs(p2.getX() - p1.getX()) > actionRadius) continue;
            if (Math.abs(p2.getY() - p1.getY()) > actionRadius) continue;

            double distance = GamePoint.getLength(p1, p2);
            if (distance == 0) continue;
            if (distance > actionRadius) continue;

            addTarget(p2);
            object.addVelocity((p2.getX() - p1.getX()) / Math.pow(distance, 1.5) * force * factor,
                    (p2.getY() - p1.getY()) / Math.pow(distance, 1.5) * force * factor);
        }

        for (Arrow arrow : GameManager.getInstance().arrows) {
            GamePoint p1 = position;
            GamePoint p2 = arrow.getPosition();

            double distance = GamePoint.getLength(p1, p2);
            if (distance == 0) continue;
            if (distance > actionRadius) continue;

            addTarget(p2);
            arrow.addVelocity((p2.getX() - p1.getX()) / Math.pow(distance, 1.5) * force * factor,
                    (p2.getY() - p1.getY()) / Math.pow(distance, 1.5) * force * factor);
        }
    }

    @Override
    public void draw(Canvas canvas) {

        matrix.reset();
        matrix.preTranslate((float) ResolutionUtils.getRelationalX(position.getX() - staticAnimation.getCurrentBitmap().getWidth() / 2 / ResolutionUtils.getRESOLUTION_FACTOR()),
                (float) ResolutionUtils.getRelationalY(position.getY() - staticAnimation.getCurrentBitmap().getHeight() / 2 / ResolutionUtils.getRESOLUTION_FACTOR()));
        canvas.drawBitmap(staticAnimation.getCurrentBitmap(), matrix, Utils.EFFECT_PAINT);

        for (GamePoint currentTarget : targets) {

            if (!Camera.getInstance().checkOnScreen(position) || !Camera.getInstance().checkOnScreen(currentTarget)) continue;

            GamePoint vector = new GamePoint(currentTarget.getX() - position.getX(), currentTarget.getY() - position.getY());

            matrix.reset();
            matrix.preTranslate((float) ResolutionUtils.getRelationalX(position.getX() - width / 2),
                    (float) ResolutionUtils.getRelationalY(position.getY()));
            matrix.preRotate((float) vector.computeRotation() - 90,
                    (float) ResolutionUtils.getRelationalLength(width / 2), 0);
            matrix.preScale(1, (float) (vector.getVectorLength() / actionRadius) * 2);
            canvas.drawBitmap(animation.getCurrentBitmap(), matrix, Utils.EFFECT_PAINT);
        }
        targets.clear();
    }
}
