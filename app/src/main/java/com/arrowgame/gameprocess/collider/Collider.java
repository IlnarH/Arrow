package com.arrowgame.gameprocess.collider;

import com.arrowgame.gameprocess.gameobject.GamePoint;

public abstract class Collider {

    public GamePoint position;

    double width;
    double height;

    protected Collider(GamePoint position, double width, double height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    public abstract boolean checkCollision(GamePoint gamePoint);

    public abstract boolean checkCollision(Collider collider);

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
