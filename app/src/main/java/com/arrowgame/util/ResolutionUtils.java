package com.arrowgame.util;

import android.util.Log;

import com.arrowgame.gameprocess.graphics.Camera;

public class ResolutionUtils {

    public static final double GAME_SCREEN_WIDTH = 213.33;
    public static double GAME_SCREEN_HEIGHT;

    public static int DEVICE_SCREEN_WIDTH;
    public static int DEVICE_SCREEN_HEIGHT;

    private static double RESOLUTION_FACTOR;
    public static final double HIGHEST_RESOLUTION_FACTOR = 8;

    private static double BITMAP_RESOLUTION_FACTOR;

    private static float DPI;

    private ResolutionUtils() {
    }

    public static void initGameScreen(int width, int height, float DPI) {
        DEVICE_SCREEN_WIDTH = width;
        DEVICE_SCREEN_HEIGHT = height;
        ResolutionUtils.DPI = DPI;

        RESOLUTION_FACTOR = DEVICE_SCREEN_WIDTH / GAME_SCREEN_WIDTH;
        GAME_SCREEN_HEIGHT = DEVICE_SCREEN_HEIGHT / RESOLUTION_FACTOR;

        BITMAP_RESOLUTION_FACTOR = RESOLUTION_FACTOR / HIGHEST_RESOLUTION_FACTOR;

        Log.e("DPI", DPI + "");
    }

    public static double getRelationalX(double x) {
        return (x - Camera.getInstance().getLeftBorder()) * RESOLUTION_FACTOR;
    }

    public static double getRelationalY(double y) {
        return (y - Camera.getInstance().getTopBorder()) * RESOLUTION_FACTOR;
    }

    public static double getRelationalLength(double length) {
        return length * RESOLUTION_FACTOR;
    }

    public static double getBITMAP_RESOLUTION_FACTOR() {
        return BITMAP_RESOLUTION_FACTOR;
    }

    public static double getRESOLUTION_FACTOR() {
        return RESOLUTION_FACTOR;
    }

    public static float getDPI() {
        return DPI;
    }

    public static double getScaledBitmapSize(double a) {
        return a * BITMAP_RESOLUTION_FACTOR;
    }

    public static double getScaledSize(double a) {
        return a / HIGHEST_RESOLUTION_FACTOR;
    }
}