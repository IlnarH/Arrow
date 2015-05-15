package com.arrowgame.gameprocess.gameobject;

import android.graphics.Canvas;

import com.arrowgame.gameprocess.GameManager;
import com.arrowgame.gameprocess.animation.ArrowAnimation;
import com.arrowgame.gameprocess.graphics.Camera;
import com.arrowgame.gameprocess.graphics.DrawThread;
import com.arrowgame.shop.shopItems.ShopItemsHolder;
import com.arrowgame.shop.shopItems.Skin;
import com.arrowgame.shop.skins.ArrowEyes;
import com.arrowgame.shop.skins.ArrowSkin;
import com.arrowgame.shop.skins.SkinType;
import com.arrowgame.util.ResolutionUtils;
import com.arrowgame.util.Utils;

import java.util.HashMap;
import java.util.Map;

public class Arrow extends GameObject {

    public boolean attached = false;

    public GamePoint pikePosition = new GamePoint();

    public Map<SkinType, ArrowAnimation> skins = new HashMap<SkinType, ArrowAnimation>();

    public Arrow(GamePoint position) {
        super(position, 0, null, null, null);

        height = ResolutionUtils.getScaledSize(176);

        setSkin(ShopItemsHolder.getSkin(ArrowSkin.ITEM_ID));
        setSkin(ShopItemsHolder.getSkin(ArrowEyes.ITEM_ID));
    }

    @Override
    public boolean update(double factor) {
        if (!attached) {
            updateVelocity(factor);
            updateRotation();
        }
        updateAnimation(factor, rotation);

        if (!DrawThread.getInstance().isGameStarted()) return true;

        updatePosition(factor);
        updatePikePosition();
        return true;
    }

    @Override
    protected void updatePosition(double factor) {
        boolean b = false;
        double px = position.x;
        double py = position.y;

        position.x += velocity.x * factor;
        position.y += velocity.y * factor;

        double dx = Math.cos(Math.toRadians(rotation)) * height / 2;
        if (position.x + dx <= 0) {
            position.x = -dx;
            b = true;

        } else if (position.x + dx >= ResolutionUtils.GAME_SCREEN_WIDTH) {
            position.x = ResolutionUtils.GAME_SCREEN_WIDTH - dx;
            b = true;
        }

        if (b && attached) {
            position.x = px;
            position.y = py;
        } else {
            attached = b;
        }

        if (position.y > Camera.getInstance().getBottomBorder()) {
            position = Camera.getInstance().getCenter();
            velocity.x = 0;
            velocity.y = 0;
        }

        if (px == position.x) {
            velocity.x = 0;
        }
        if (py == position.y) {
            velocity.y = 0;
        }

        if (attached) {
            velocity.x = 0;
            velocity.y = 0;
        }
    }

    @Override
    protected void updateRotation() {
        super.updateRotation();
    }

    protected void updatePikePosition() {
        pikePosition.x = Math.cos(Math.toRadians(rotation)) * height / 2 + position.x;
        pikePosition.y = Math.sin(Math.toRadians(rotation)) * height / 2 + position.y;
    }

    public void setVelocity(double x, double y) {
        this.velocity.x = x;
        this.velocity.y = y;
    }

    @Override
    public void draw(Canvas canvas) {
        for (ArrowAnimation arrowAnimation : skins.values()) {
            if (arrowAnimation != null) {
                matrix.reset();
                matrix.preTranslate((float) (ResolutionUtils.getRelationalX(position.x) + arrowAnimation.getXOffset()),
                        (float) (ResolutionUtils.getRelationalY(position.y) + arrowAnimation.getYOffset()));
                matrix.preRotate((float) (rotation - 90), (float) -arrowAnimation.getXOffset(),
                        (float) -arrowAnimation.getYOffset());
                canvas.drawBitmap(arrowAnimation.getCurrentBitmap(), matrix, Utils.FILTERED_PAINT);
            }
        }
    }

    protected void updateAnimation(double factor, double rotation) {
        for (ArrowAnimation arrowAnimation : skins.values()) {
            if (arrowAnimation != null) {
                arrowAnimation.update(factor, rotation);
            }
        }
    }

    public void setSkin(Skin skin) {
        skins.put(skin.getType(), (ArrowAnimation) skin);
    }

    public Skin getSkin(SkinType type) {
        return skins.get(type);
    }

    public void takeOff(Skin skin) {
        skins.put(skin.getType(), null);
        if (skin.getType() == SkinType.BODY) {
            setSkin(ShopItemsHolder.getSkin(ArrowSkin.ITEM_ID));
        } else if (skin.getType() == SkinType.EYES) {
            setSkin(ShopItemsHolder.getSkin(ArrowEyes.ITEM_ID));
        }
    }

    public GamePoint getVelocity() {
        return velocity;
    }

    @Override
    public void add() {
        GameManager.getInstance().arrows.add(this);
    }

    @Override
    public void remove() {
        GameManager.getInstance().arrows.remove(this);
    }

    @Override
    public void destroy() {
        remove();
    }
}