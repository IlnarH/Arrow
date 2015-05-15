package com.arrowgame.util;

import android.content.res.Resources;
import android.graphics.Typeface;

import com.arrowgame.activity.GameActivity;
import com.arrowgame.gameprocess.GameManager;
import com.arrowgame.gameprocess.graphics.Camera;
import com.arrowgame.gameprocess.graphics.DrawThread;
import com.arrowgame.gameprocess.graphics.GameSurfaceView;
import com.arrowgame.shop.shopItems.ShopItemsHolder;

public class GameInitializer {

    public static void initGame(int width, int height, Resources resources, GameActivity gameActivity) {

        Utils.RESOURCES = resources;
        Utils.inflater = gameActivity.getLayoutInflater();
        Utils.FONT = Typeface.createFromAsset(gameActivity.getAssets(), "fonts/SpaceWoozies.ttf");
        ResolutionUtils.initGameScreen(width, height, resources.getDisplayMetrics().density);
        PhysicsUtils.init();
        BitmapUtils.initBitmaps();
        SoundUtils.init(gameActivity);
//        Skins.initSkins();
        ShopItemsHolder.init();
        DataStoreUtils.init(gameActivity);
        gameActivity.gameSurfaceView = GameSurfaceView.createINSTANCE(gameActivity);
        DrawThread.createThread(GameSurfaceView.getINSTANCE().getHolder(), GameSurfaceView.getINSTANCE().getContext());
        gameActivity.initMenuScrollView();
    }

    public static void destroyGame() {
        DrawThread.destroyThread();
        GameManager.destroy();
        Camera.destroy();
        ShopItemsHolder.destroy();
        PhysicsUtils.destroy();
        BitmapUtils.destroy();
    }
}
