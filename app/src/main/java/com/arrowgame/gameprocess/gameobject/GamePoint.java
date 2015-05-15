package com.arrowgame.gameprocess.gameobject;

public class GamePoint {

    double x;
    double y;

    public GamePoint() {
        x = 0;
        y = 0;
    }

    public GamePoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getVectorLength() {
        return (double) Math.sqrt(x * x + y * y);
    }

    public GamePoint getNormalized() {
        double s = getVectorLength();
        if (s == 0) {
            return new GamePoint();
        }
        return new GamePoint(x / s, y / s);
    }

    public GamePoint getNormalized(double length) {
        double s = getVectorLength() / length;
        return new GamePoint(x / s, y / s);
    }

    public boolean getNormalized(double length, GamePoint outPoint) {
        double s = getVectorLength() / length;
        if (s == 0) return false;

        outPoint.x = x / s;
        outPoint.y = y / s;
        return true;
    }

    public static GamePoint getNormalized(double length, GamePoint p1, GamePoint p2) {
        double s = getLength(p1, p2) / length;
        if (s == 0) return null;

        return new GamePoint((p2.x - p1.x) / s, (p2.y - p2.y) / s);
    }

    public double computeRotation() {
        GamePoint gamePoint = getNormalized();
        if (gamePoint.x == 0 && gamePoint.y == 0) {
            return 90;
        }
        double rotation = Math.toDegrees(Math.atan(gamePoint.y / gamePoint.x));
        if (gamePoint.x < 0) {
            rotation += 180;
        }
        if (rotation < 0) {
            rotation += 360;
        }
        return rotation;
    }

    public static double getLength(GamePoint point1, GamePoint point2) {
        return Math.sqrt((point1.x - point2.x) * (point1.x - point2.x) +
                (point1.y - point2.y) * (point1.y - point2.y));
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
