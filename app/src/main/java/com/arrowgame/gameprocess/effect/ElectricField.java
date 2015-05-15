package com.arrowgame.gameprocess.effect;


import android.graphics.Canvas;

import com.arrowgame.gameprocess.GameManager;
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
 * Created by Ильнар on 31.01.2015.
 */
public class ElectricField extends Effect {

    private List<GamePoint> targets = new ArrayList<GamePoint>();

    private static double maxActionRadius = 54;
    private static double minActionRadius = 9;
    private static double force = -0.15;

    private static double actionDuration = 40;
    private static double inactionDuration = 40;

    private double currentTime = 0;
    private boolean active = false;


    public ElectricField(GamePoint position) {
        super(position, new BitmapBasedAnimation());
        ((BitmapBasedAnimation) animation).addFrame(BitmapUtils.getBitmap("electric_whip_1"), 2);
        ((BitmapBasedAnimation) animation).addFrame(BitmapUtils.getBitmap("electric_whip_2"), 2);

        initWidthAndHeight();
    }

    public void addTarget(GamePoint point) {
        targets.add(point);
    }

    @Override
    public boolean update(double factor) {

        currentTime += factor;
        if (active && currentTime > actionDuration) {
            active = false;
            currentTime = 0;
        } else if (!active && currentTime > inactionDuration) {
            active = true;
            currentTime = 0;
        }
        if (active) { action(factor); }

        return super.update(factor);
    }

    public void action(double factor) {
        List<CollidingObject> objects = GameManager.getInstance().collidingObjects;

        for (CollidingObject object : objects) {

            GamePoint p1 = position;
            GamePoint p2 = object.getPosition();

            if (Math.abs(p2.getX() - p1.getX()) > maxActionRadius) continue;
            if (Math.abs(p2.getY() - p1.getY()) > maxActionRadius) continue;

            double distance = GamePoint.getLength(p1, p2);
            if (distance == 0) continue;
            if (distance > maxActionRadius) continue;
            if (distance < minActionRadius) continue;

            addTarget(p2);
            object.addVelocity((p2.getX() - p1.getX()) / distance * force * factor, (p2.getY() - p1.getY()) / distance * force * factor);
        }

        for (Arrow arrow : GameManager.getInstance().arrows) {
            GamePoint p1 = position;
            GamePoint p2 = arrow.getPosition();

            double distance = GamePoint.getLength(p1, p2);
            if (distance == 0) continue;
            if (distance > maxActionRadius) continue;
            if (distance < minActionRadius) continue;

            addTarget(p2);
            arrow.addVelocity((p2.getX() - p1.getX()) / distance * force * factor, (p2.getY() - p1.getY()) / distance * force * factor);
        }
    }

    @Override
    public void draw(Canvas canvas) {

        for (GamePoint currentTarget : targets) {

            if (!Camera.getInstance().checkOnScreen(position) || !Camera.getInstance().checkOnScreen(currentTarget)) continue;

            GamePoint vector = new GamePoint(currentTarget.getX() - position.getX(), currentTarget.getY() - position.getY());

            matrix.reset();
            matrix.preTranslate((float) ResolutionUtils.getRelationalX(position.getX() - width / 2),
                    (float) ResolutionUtils.getRelationalY(position.getY()));
            matrix.preRotate((float) vector.computeRotation() - 90,
                    (float) ResolutionUtils.getRelationalLength(width / 2), 0);
            matrix.preScale(1, (float) (vector.getVectorLength() / maxActionRadius));
            canvas.drawBitmap(animation.getCurrentBitmap(), matrix, Utils.EFFECT_PAINT);
        }
        targets.clear();
    }

    public boolean isActive() {
        return active;
    }
}
