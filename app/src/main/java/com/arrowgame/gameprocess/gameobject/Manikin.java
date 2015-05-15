package com.arrowgame.gameprocess.gameobject;

import com.arrowgame.gameprocess.GameManager;
import com.arrowgame.gameprocess.animation.ArrowAnimation;
import com.arrowgame.shop.skins.SkinType;

import java.util.Map;

public class Manikin extends Arrow {

    public Manikin(GamePoint position) {
        super(position);
        rotation = 0;
    }

    public void invalidate() {
        for (Map.Entry<SkinType, ArrowAnimation> entry : GameManager.getArno().skins.entrySet()) {
            skins.put(entry.getKey(), entry.getValue());
        }
    }

    @Override
    public boolean update(double factor) {
        updateAnimation(factor, rotation);
        return true;
    }

    @Override
    public void add() {
        GameManager.setManikin(this);
    }

    @Override
    public void remove() {
        GameManager.setManikin(null);
    }

    @Override
    public void destroy() {
        remove();
    }
}
