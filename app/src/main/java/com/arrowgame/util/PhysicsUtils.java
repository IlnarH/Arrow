package com.arrowgame.util;

public class PhysicsUtils {

    public final static double GRAVITY = 0.05f;
    public final static double AIR_FRICTION = 0.006f;
    public static double TIME_SPEED;

    public static void setTimeSpeed(double timeSpeed) {
        TIME_SPEED = timeSpeed;
    }

    public static void setDefaultTimeSpeed() {
        TIME_SPEED = 1;
    }

    public static void init() {
        TIME_SPEED = 1;
    }

    public static void destroy() {
        TIME_SPEED = 0;
    }
}
