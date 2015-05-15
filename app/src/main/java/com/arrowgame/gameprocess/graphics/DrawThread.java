package com.arrowgame.gameprocess.graphics;

import android.content.Context;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

import com.arrowgame.activity.GameActivity;
import com.arrowgame.gameprocess.GameManager;
import com.arrowgame.gameprocess.gameobject.Arrow;
import com.arrowgame.gameprocess.gameobject.GamePoint;
import com.arrowgame.util.PhysicsUtils;
import com.arrowgame.util.Utils;

import java.security.PrivateKey;

public class DrawThread extends Thread {

    public static DrawThread drawThread;

    SurfaceHolder surfaceHolder;
    Context context;
    private long lastIteration;

    private long lastFps;
    private long fpsCounter;

    public boolean running;

    private boolean gameStarted = false;

    private long last;
    private long counter = 0;
    private double averageFPS;

    private DrawThread(SurfaceHolder surfaceHolder, Context context, boolean gameStarted) {
        this.surfaceHolder = surfaceHolder;
        this.context = context;

        running = true;
        this.gameStarted = gameStarted;
        lastIteration = System.currentTimeMillis();
        lastFps = System.currentTimeMillis();
        fpsCounter = 0;
    }

//    main game cycle
    @Override
    public void run() {
        Canvas canvas = null;
        while (running) {
            long now = System.currentTimeMillis();
            if (now - lastIteration > 15) {
                double factor = countFPS(now);
                factor *= PhysicsUtils.TIME_SPEED;
                try {
                    canvas = surfaceHolder.lockCanvas(null);
                    if (canvas == null) continue;

//                    update and draw background and manikin
                    GameManager.updateAndDrawBackground(factor, canvas);
                    GameManager.updateAndDrawManikin(factor, canvas);


                    if (gameStarted) {
                        GameManager.update(factor, canvas);

                        if (fpsCounter == 0) {
                            averageFPS = (counter * averageFPS + last) / ++counter;
                        }
                    }

//                    updating camera
                    Camera.getInstance().updateCamera(factor);

                } finally {
                    if (canvas != null) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }
        }
    }

    public void startGame() {
        GameManager.getInstance().startGame();
        gameStarted = true;
    }

    public void pauseGame() {
        running = false;
        boolean retry = true;
        gameStarted = false;
        DrawThread.getInstance().running = false;
        while (retry) {
            try {
                DrawThread.getInstance().join();
                retry = false;
            } catch (InterruptedException e) {
//                if doesn't stop try again and again
            }
        }
    }

    public void resumeGame() {
        recreateThread();
        drawThread.start();
        gameStarted = true;
    }

    private double countFPS(long now) {
        if (now - lastFps > 1000) {
            Log.e("FPS", " : " + fpsCounter);
            Runtime info = Runtime.getRuntime();
            Log.e("Memory", " : " + ((info.totalMemory() - info.freeMemory()) / 10000000.0));
            lastFps = now;
            last = fpsCounter;
            fpsCounter = 0;
        } else {
            fpsCounter++;
        }
        double factor = Utils.getFrameRateFactor(now - lastIteration);
        lastIteration = now;
        return factor;
    }

    public static void createThread(SurfaceHolder surfaceHolder, Context context) {
        if (drawThread != null) {
            Log.e("Draw thread error", "Thread currently exists.");
//            System.exit(1);
            drawThread.pauseGame();
            recreateThread();
            return;
        }
        drawThread = new DrawThread(surfaceHolder, context, false);
    }

    public static void recreateThread() {
        if (drawThread == null) {
            Log.e("Draw thread error", "Thread does not exist");
            System.exit(1);
        }
        drawThread = new DrawThread(drawThread.surfaceHolder, drawThread.context, drawThread.gameStarted);
    }

    public static DrawThread getInstance() {
        if (drawThread == null) {
            Log.e("Draw thread error", "Thread does not exist.");
            System.exit(1);
        }
        return drawThread;
    }

    public static void destroyThread() {
        drawThread.pauseGame();
        Log.e("Average fps", drawThread.averageFPS + "");
        drawThread = null;
    }

    public boolean isGameStarted() {
        return gameStarted;
    }
}