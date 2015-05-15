package com.arrowgame.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.arrowgame.R;
import com.arrowgame.gameprocess.GameInterfaceViewHolder;
import com.arrowgame.gameprocess.graphics.DrawThread;
import com.arrowgame.gameprocess.graphics.GameSurfaceView;
import com.arrowgame.menu.MenuScrollView;
import com.arrowgame.shop.ShopView;
import com.arrowgame.shop.shopItems.ShopItemsHolder;
import com.arrowgame.util.GameInitializer;

public class GameActivity extends Activity {

    public GameSurfaceView gameSurfaceView;
    public MenuScrollView menuScrollView;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("AVAILABLE MEMORY", Integer.toString(((ActivityManager) getSystemService(ACTIVITY_SERVICE)).getMemoryClass()));

        setContentView(R.layout.game_layout);
        initGame();

        ((RelativeLayout) findViewById(R.id.game_layout)).addView(GameInterfaceViewHolder.getGameInterfaceLayout());
    }

    private void initGame() {
        GameInitializer.initGame(getWindowManager().getDefaultDisplay().getWidth(),
                getWindowManager().getDefaultDisplay().getHeight(), getResources(), this);

        try {
            ((FrameLayout) findViewById(R.id.gameSurface)).addView(gameSurfaceView);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startGame() {
        DrawThread.getInstance().startGame();
//        ShopItemsHolder.destroy();
//        ShopView.destroy();
    }

    public void initMenuScrollView() {
        menuScrollView = new MenuScrollView(this);
        ((RelativeLayout) findViewById(R.id.game_layout)).addView(menuScrollView);
    }

    public MenuScrollView getMenuScrollView() {
        return menuScrollView;
    }

    @Override
    protected void onPause() {
        super.onPause();
        pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GameInitializer.destroyGame();

    }

    public void changeState() {
        if (isRunning()) {
            onPause();
        } else {
            onResume();
        }
    }

    public boolean isRunning() {
        return DrawThread.getInstance().running;
    }

    private synchronized void pause() {
        DrawThread.getInstance().pauseGame();
    }

    private synchronized void resume() {
        DrawThread.getInstance().resumeGame();
    }
}