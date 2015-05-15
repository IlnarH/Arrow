package com.arrowgame.gameprocess.gameobject;

import com.arrowgame.gameprocess.GameManager;
import com.arrowgame.gameprocess.animation.Animation;
import com.arrowgame.gameprocess.graphics.Camera;

public class Decoration extends GameObject {

    public Decoration(GamePoint position, Animation animation) {
        super(position, 0, animation, null, null);
    }

    @Override
    public boolean update(double factor) {
        updatePosition(factor);
        if (!checkVisible()) {
            remove();
            return false;
        }
        return true;
    }

    @Override
    protected void updatePosition(double factor) {

    }

    @Override
    public boolean checkVisible() {
        return Camera.getInstance().checkDecorationOnScreen(position);
    }

    @Override
    public void add() {
        GameManager.getBackground().addDecoration(this);
    }

    @Override
    public void remove() {
        GameManager.getBackground().removeDecoration(this);
    }

    @Override
    public void destroy() {
        remove();
    }
}
