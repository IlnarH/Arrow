package com.arrowgame.menu;

import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arrowgame.R;
import com.arrowgame.activity.GameActivity;

public class MenuViewsHolder {

    private Button startButton;

    MenuScrollView menuScrollView;

    static MenuViewsHolder INSTANCE;

    SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    int sound;

    public MenuViewsHolder(final MenuScrollView menuScrollView) {
        this.menuScrollView = menuScrollView;
        final GameActivity gameActivity = (GameActivity) menuScrollView.getContext();
        sound = soundPool.load(menuScrollView.getContext(), R.raw.click, 1);

        this.startButton = (Button) menuScrollView.findViewById(R.id.start_button);
        startButton.setTypeface(Typeface.createFromAsset(gameActivity.getAssets(), "fonts/SpaceWoozies.ttf"));
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameActivity.startGame();
                menuScrollView.setVisibility(View.GONE);
                soundPool.play(sound, 1, 1, 0, 0, 1);
            }
        });

        ((TextView) menuScrollView.findViewById(R.id.settings_text)).setTypeface(Typeface.createFromAsset(gameActivity.getAssets(), "fonts/SpaceWoozies.ttf"));

        INSTANCE = this;
    }

    public static void stopGame() {
        INSTANCE.soundPool.play(INSTANCE.sound, 1, 1, 0, 0, 1);
        INSTANCE.menuScrollView.setVisibility(View.VISIBLE);
    }
}
