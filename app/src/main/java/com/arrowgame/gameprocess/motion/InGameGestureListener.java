package com.arrowgame.gameprocess.motion;

import android.view.GestureDetector;
import android.view.MotionEvent;

import com.arrowgame.activity.GameActivity;
import com.arrowgame.gameprocess.GameManager;
import com.arrowgame.gameprocess.graphics.DrawThread;
import com.arrowgame.gameprocess.graphics.GameSurfaceView;
import com.arrowgame.util.PhysicsUtils;

public class InGameGestureListener extends GestureDetector.SimpleOnGestureListener {

    GameSurfaceView surfaceView;

    public InGameGestureListener(GameSurfaceView surfaceView) {
        this.surfaceView = surfaceView;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return super.onDown(e);
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        GameManager.getInstance().moveArrows(e1, e2);
        return super.onFling(e1, e2, velocityX, velocityY);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        GameManager.activateFirstPower();
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        GameManager.activateFirstPower();
        return false;
    }
}
