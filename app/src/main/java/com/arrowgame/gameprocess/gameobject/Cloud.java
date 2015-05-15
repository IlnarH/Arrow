package com.arrowgame.gameprocess.gameobject;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.Log;

import com.arrowgame.gameprocess.animation.Animation;
import com.arrowgame.util.BitmapUtils;
import com.arrowgame.util.ResolutionUtils;
import com.arrowgame.util.Utils;

public class Cloud extends Decoration {

    private double scale;

    public Cloud(GamePoint position) {
        super(position, null);
        scale = Utils.random.nextInt(3);
        Bitmap bitmap = null;
        if (Utils.random.nextInt(2) == 0) {
            if (scale == 0)
                bitmap = BitmapUtils.getBitmap("cloud_1_l");
            if (scale == 1)
                bitmap = BitmapUtils.getBitmap("cloud_1_m");
            if (scale == 2)
                bitmap = BitmapUtils.getBitmap("cloud_1_s");
        } else {
            if (scale == 0)
                bitmap = BitmapUtils.getBitmap("cloud_2_l");
            if (scale == 1)
                bitmap = BitmapUtils.getBitmap("cloud_2_m");
            if (scale == 2)
                bitmap = BitmapUtils.getBitmap("cloud_2_s");
        }
        scale = 1 - scale * 0.25;
        animation = new Animation(bitmap);
        initWidthAndHeight();
    }

    @Override
    protected void updatePosition(double factor) {
        position.x -= factor * scale / 2.5;
    }

    @Override
    public void draw(Canvas canvas) {
        matrix.reset();
        matrix.preTranslate((float) ResolutionUtils.getRelationalX(position.x - width / 2 * scale),
                (float) ResolutionUtils.getRelationalY(position.y - height / 2 * scale));
//        matrix.preScale((float) scale, (float) scale);

        canvas.drawBitmap(animation.getCurrentBitmap(), matrix, Utils.FILTERED_PAINT);
    }
}
