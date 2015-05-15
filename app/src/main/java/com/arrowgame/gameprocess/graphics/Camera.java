package com.arrowgame.gameprocess.graphics;

import android.util.Log;

import com.arrowgame.gameprocess.GameManager;
import com.arrowgame.gameprocess.gameobject.GamePoint;
import com.arrowgame.util.ResolutionUtils;

public class Camera {

    private double previousBottomBorder = 0;
    private double bottomBorder = 0;
    private double leftBorder = -160;

    private double visibilityOffset = 50;

    private static double scrollingFactor = 0.5;
    private int currentScreen = 0;
    private static double scrollSpeed = 10;
    private boolean move = true;

    private static Camera INSTANCE;

    private Camera() {
    }

    public static Camera getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Camera();
        }
        return INSTANCE;
    }

    public double getBottomBorder() {
        return bottomBorder;
    }

    public double getTopBorder() {
        return bottomBorder - ResolutionUtils.GAME_SCREEN_HEIGHT;
    }

    public double getLeftBorder() {
        return leftBorder;
    }

    public double getRightBorder() {
        return leftBorder + ResolutionUtils.GAME_SCREEN_WIDTH;
    }

    public void updateCamera(double factor) {
        if (DrawThread.getInstance().isGameStarted()) {
            previousBottomBorder = bottomBorder;
            bottomBorder -= factor / 2;
        }

        if (!GameManager.getInstance().arrows.isEmpty()) {
            double y = GameManager.getInstance().Arno.getPosition().getY();
            if (y < getTopBorder() + 35) {
                bottomBorder = y - 35 + ResolutionUtils.GAME_SCREEN_HEIGHT;
            }
        }
        Log.e("CAMERA", leftBorder + " " + bottomBorder);
//        camera scrolling
        if (!move) return;
        double targetX = getScreenX(currentScreen);
        if (targetX > leftBorder) {
            double x = leftBorder + scrollSpeed;
            if (x > targetX) {
                leftBorder = targetX;
                move = false;
            } else {
                leftBorder = x;
            }
        } else if (targetX < leftBorder) {
            double x = leftBorder - scrollSpeed;
            if (x < targetX) {
                leftBorder = targetX;
                move = false;
            } else {
                leftBorder = x;
            }
        }
        Log.e("CAMERA AFTER", leftBorder + " " + bottomBorder);
    }

    public GamePoint getCenter() {
        return new GamePoint(ResolutionUtils.GAME_SCREEN_WIDTH / 2, (getTopBorder() - bottomBorder) / 2 + bottomBorder);
    }

    public static void reset() {
        INSTANCE.bottomBorder = 0;
        INSTANCE.previousBottomBorder = 0;
    }

    public double getDelta() {
        return previousBottomBorder - bottomBorder;
    }

    public static void destroy() {
        INSTANCE = null;
    }

    public boolean checkOnScreen(GamePoint point) {
        if (point.getX() < getLeftBorder() - visibilityOffset ||
                point.getX() > getRightBorder() + visibilityOffset || point.getY() > getBottomBorder() + visibilityOffset) {
            return false;
        }
        return true;
    }

    public boolean checkDecorationOnScreen(GamePoint point) {
        if (point.getX() < getLeftBorder() - visibilityOffset * 2 ||
                point.getX() > getRightBorder() + visibilityOffset * 2 || point.getY() > getBottomBorder() + visibilityOffset * 2) {
            return false;
        }
        return true;
    }

    public void xScrollBy(double x) {
        leftBorder += x * scrollingFactor;
    }

    public void xScrollTo(int screen) {
        leftBorder = getScreenX(screen);
        currentScreen = screen;
        move = true;
    }

    public void xSmoothScrollTo(int screen) {
        currentScreen = screen;
        move = true;
    }

    private double getScreenX(int screen) {
        return (screen - 1) * ResolutionUtils.GAME_SCREEN_WIDTH * scrollingFactor;
    }

    public static double getScrollingFactor() {
        return scrollingFactor;
    }
}
