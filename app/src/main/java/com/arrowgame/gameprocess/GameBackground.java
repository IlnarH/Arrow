package com.arrowgame.gameprocess;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

import com.arrowgame.gameprocess.gameobject.Arrow;
import com.arrowgame.gameprocess.gameobject.Cloud;
import com.arrowgame.gameprocess.gameobject.Decoration;
import com.arrowgame.gameprocess.gameobject.GamePoint;
import com.arrowgame.gameprocess.gameobject.Ground;
import com.arrowgame.gameprocess.graphics.Camera;
import com.arrowgame.util.BitmapUtils;
import com.arrowgame.util.ResolutionUtils;
import com.arrowgame.util.Utils;

import java.util.ArrayList;
import java.util.List;

public class GameBackground {

    List<Decoration> decorations = new ArrayList<Decoration>();

    int backgroundColor = Color.rgb(153, 221, 251);
    int backgroundColor2 = Color.rgb(83, 150, 224);
    int backgroundColor3 = Color.rgb(40, 40, 40);
    double nextCloudAfter = 0;

    public GameBackground() {
    }

    public void update(double factor) {
        double delta = Camera.getInstance().getDelta();
        if (delta == 0) {
            delta = factor / 2;
        }
        nextCloudAfter -= delta;
        if (nextCloudAfter < 0) {
            generateCloud();
            nextCloudAfter = Utils.random.nextInt(40);
        }

        for (int i = 0; i < decorations.size(); i++) {
            if (!decorations.get(i).update(factor)) {
                i--;
            }
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawColor(backgroundColor2);

        for (Decoration decoration : decorations) {
            decoration.draw(canvas);
        }
    }

    private void generateCloud() {
        if (Utils.random.nextBoolean()) {
            decorations.add(new Cloud(new GamePoint(Utils.random.nextInt((int) ResolutionUtils.GAME_SCREEN_WIDTH + 20) - 10,
                    -Utils.random.nextInt(100) + Camera.getInstance().getTopBorder() - 100)));
        } else {
            decorations.add(new Cloud(new GamePoint(Utils.random.nextInt(50)
                    + Camera.getInstance().getRightBorder() + 50,
                    -Utils.random.nextInt((int) ResolutionUtils.GAME_SCREEN_HEIGHT) + Camera.getInstance().getBottomBorder())));
        }
//        Log.e("Clouds", decorations.get(decorations.size() - 1).getPosition().toString() + " cam " + Camera.getInstance().getTopBorder());
    }

    public void addDecoration(Decoration decoration) {
        decorations.add(decoration);
    }

    public void removeDecoration(Decoration decoration) {
        decorations.remove(decoration);
    }
}
