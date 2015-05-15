package com.arrowgame.gameprocess;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

import com.arrowgame.gameprocess.effect.Effect;
import com.arrowgame.gameprocess.gameobject.Arrow;
import com.arrowgame.gameprocess.gameobject.Manikin;
import com.arrowgame.gameprocess.gameobject.balloons.Balloon;
import com.arrowgame.gameprocess.gameobject.CollidingObject;
import com.arrowgame.gameprocess.gameobject.Decoration;
import com.arrowgame.gameprocess.gameobject.GamePoint;
import com.arrowgame.gameprocess.gameobject.balloons.BlueBalloon;
import com.arrowgame.gameprocess.gameobject.balloons.OrangeBalloon;
import com.arrowgame.gameprocess.gameobject.balloons.RedBalloon;
import com.arrowgame.gameprocess.graphics.Camera;
import com.arrowgame.gameprocess.graphics.DrawThread;
import com.arrowgame.gameprocess.powers.Energy;
import com.arrowgame.gameprocess.powers.Power;
import com.arrowgame.gameprocess.powers.SlowTimePower;
import com.arrowgame.shop.skins.ArrowEyes;
import com.arrowgame.shop.skins.LanternEyes;
import com.arrowgame.util.PhysicsUtils;
import com.arrowgame.util.ResolutionUtils;
import com.arrowgame.util.Utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameManager {

    private static GameManager INSTANCE;

    private double timeLeft;

    public List<CollidingObject> collidingObjects = new ArrayList<CollidingObject>();
    public List<Effect> effects = new ArrayList<Effect>();
    public List<Arrow> arrows = new ArrayList<Arrow>();
    private GameBackground background = new GameBackground();
    private Manikin manikin;
    public Arrow Arno;

    private Power firstPower;
    private Power secondPower;

    private double nextBalloonAfter = 0;

    public static GameManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameManager();
        }
        return INSTANCE;
    }

    private GameManager() {
        int height = (int) ResolutionUtils.GAME_SCREEN_HEIGHT;
        int width = (int) ResolutionUtils.GAME_SCREEN_WIDTH;
        GamePoint point = new GamePoint(width * 0.5 + width * Camera.getScrollingFactor(), -height * 0.8);
        this.manikin = new Manikin(point);
        this.Arno = new Arrow(Camera.getInstance().getCenter());
        firstPower = new SlowTimePower(2);
    }

    public static void startGame() {
        getInstance().timeLeft = 30;
        getInstance().arrows.add(getInstance().Arno);
        getInstance().Arno.setPosition(Camera.getInstance().getCenter());
        GameInterfaceViewHolder.showOrHide();
    }

    public static void stopGame() {
        DrawThread.getInstance().pauseGame();
        getInstance().arrows.remove(getInstance().Arno);
        GameInterfaceViewHolder.showOrHide();
        Iterator<CollidingObject> iterator = getInstance().collidingObjects.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            iterator.remove();
        }
        Iterator<Effect> effectIterator = getInstance().effects.iterator();
        while (effectIterator.hasNext()) {
            effectIterator.next();
            effectIterator.remove();
        }
        Camera.reset();
        DrawThread.getInstance().resumeGame();
    }

    public static void moveArrows(MotionEvent e1, MotionEvent e2) {
        for (Arrow arrow : getInstance().arrows) {
            double f = (Utils.random.nextInt(10) + 90) / 100.0f;
            arrow.setVelocity(((e2.getX() - e1.getX()) / ResolutionUtils.getDPI()) * Utils.FORCE * f,
                    ((e2.getY() - e1.getY()) / ResolutionUtils.getDPI()) * Utils.FORCE * f);
        }
    }

    public void generateBalls() {
        nextBalloonAfter -= Camera.getInstance().getDelta();
        if (nextBalloonAfter < 0) {
            generateBalloon();
            nextBalloonAfter = Utils.random.nextInt(50) + 5;
        }
    }

    private static void generateBalloon() {
        GamePoint point = new GamePoint(Utils.random.nextInt((int) ResolutionUtils.GAME_SCREEN_WIDTH - 20) + 10,
                -Utils.random.nextInt(200) + Camera.getInstance().getTopBorder() - 50);
        int random = Utils.random.nextInt(10);
        if (random == 0) {
            new BlueBalloon(point).add();
        } else if (random == 1) {
            new RedBalloon(point).add();
        } else {
            new OrangeBalloon(point).add();
        }
    }

    public static void update(double factor, Canvas canvas) {
        updateTime(factor);
        Energy.update(factor);
        if (getInstance().firstPower != null) {
            getInstance().firstPower.update(factor);
        }
        if (getInstance().secondPower != null) {
            getInstance().secondPower.update(factor);
        }
        getInstance().generateBalls();
        updateObjects(factor);
        drawObjects(canvas);

        GameInterfaceViewHolder.sendInvalidateRequest();
    }

    private static void updateTime(double factor) {
        getInstance().timeLeft -= (factor * 15) / 1000;
    }

    public double getFormattedTimeLeft() {
        return ((int) (timeLeft * 10)) / 10.0;
    }

    public static void addBonusTime(double time) {
        getInstance().timeLeft += time;
    }

    public static void updateObjects(double factor) {

        List<CollidingObject> collidingObjects = getInstance().collidingObjects;
        List<Effect> effects = getInstance().effects;
        List<Arrow> arrows = getInstance().arrows;
        for (int i = 0; i < collidingObjects.size(); i++) {
            if (!collidingObjects.get(i).update(factor)) {
                i--;
            }
        }
        for (int i = 0; i < effects.size(); i++) {
            if (!effects.get(i).update(factor)) {
                i--;
            }
        }
        for (int i = 0; i < arrows.size(); i++) {
            Arrow arrow = arrows.get(i);
            arrow.update(factor);
        }
    }

    public static void drawObjects(Canvas canvas) {

        List<CollidingObject> collidingObjects = getInstance().collidingObjects;
        List<Effect> effects = getInstance().effects;
        List<Arrow> arrows = getInstance().arrows;
//        drawing objects
        for (int i = 0; i < effects.size(); i++) {
            effects.get(i).draw(canvas);
        }
        for (int i = 0; i < collidingObjects.size(); i++) {
            collidingObjects.get(i).draw(canvas);
        }
        for (int i = 0; i < arrows.size(); i++) {
            arrows.get(i).draw(canvas);
        }
    }

    public static void updateAndDrawBackground(double factor, Canvas canvas) {
        getInstance().background.update(factor);
        getInstance().background.draw(canvas);
    }

    public static void updateAndDrawManikin(double factor, Canvas canvas) {
        getInstance().manikin.update(factor);
        getInstance().manikin.draw(canvas);
    }

    public static Manikin getManikin() {
        return getInstance().manikin;
    }

    public static Arrow getArno() {
        return getInstance().Arno;
    }

    public static void setManikin(Manikin manikin) {
        getInstance().manikin = manikin;
    }

    public static void destroy() {
        INSTANCE = null;
    }

    public static void setFirstPower(Power power) {
        getInstance().firstPower = power;
    }

    public static void setSecondPower(Power power) {
        getInstance().secondPower = power;
    }

    public static void activateFirstPower() {
        getInstance().firstPower.activate();
    }

    public static void activateSecondPower() {
        getInstance().secondPower.activate();
    }

    public static GameBackground getBackground() {
        return getInstance().background;
    }

    public static void setBackground(GameBackground background) {
        getInstance().background = background;
    }
}