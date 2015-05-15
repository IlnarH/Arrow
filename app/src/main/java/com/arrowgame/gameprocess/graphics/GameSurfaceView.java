package com.arrowgame.gameprocess.graphics;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.arrowgame.gameprocess.motion.InGameGestureListener;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    GestureDetector gestureDetector;

    public static GameSurfaceView INSTANCE;

    GameSurfaceView(Context context) {
        super(context);
        gestureDetector = new GestureDetector(context, new InGameGestureListener(this));
        getHolder().addCallback(this);

//        for correct gesture handling
        this.setLongClickable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
//        try {
//            DrawThread.getInstance().start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
//        boolean retry = true;
//        DrawThread.getInstance().running = false;
//        while (retry) {
//            try {
//                DrawThread.getInstance().join();
//                retry = false;
//            } catch (InterruptedException e) {
//                if doesn't stop try again and again
//            }
//        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public static GameSurfaceView getINSTANCE() {
        return INSTANCE;
    }

    public static GameSurfaceView createINSTANCE(Context context) {
        INSTANCE = new GameSurfaceView(context);
        return INSTANCE;
    }
}
