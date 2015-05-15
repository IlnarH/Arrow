package com.arrowgame.gameprocess.collider;

import com.arrowgame.gameprocess.gameobject.GamePoint;

public class CircleCollider extends Collider {

    public double radius;

    public CircleCollider(GamePoint position, double radius) {
        super(position, radius * 2, radius * 2);
        this.radius = radius;
    }

    @Override
    public boolean checkCollision(GamePoint gamePoint) {
        if (Math.abs(position.getX() - gamePoint.getX()) > radius) {
            return false;
        }
        if (Math.abs(position.getY() - gamePoint.getY()) > radius) {
            return false;
        }
        return GamePoint.getLength(position, gamePoint) <= radius;
    }

    @Override
    public boolean checkCollision(Collider collider) {
        if (collider instanceof CircleCollider) {
            double radiusSum = this.radius + ((CircleCollider) collider).radius;
            if (Math.abs(position.getX() - collider.position.getX()) > radiusSum) {
                return false;
            }
            if (Math.abs(position.getY() - collider.position.getY()) > radiusSum) {
                return false;
            }
            return GamePoint.getLength(this.position, collider.position) < radiusSum;
        }
        return false;
    }
}
