package com.arrowgame.shop;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;

import com.arrowgame.R;
import com.arrowgame.activity.GameActivity;
import com.arrowgame.shop.shopItems.ShopItemsHolder;
import com.arrowgame.util.ResolutionUtils;
import com.arrowgame.util.Utils;

public class ShopView extends LinearLayout {

    private boolean active = false;
    private Animation downAnim = AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
    private Animation upAnim = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up);

    LinearLayout shopLayout;

    SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    int sound;

    private static ShopView INSTANCE;

    public ShopView(Context context) {
        super(context);

        sound = soundPool.load(getContext(), R.raw.click, 1);

        this.setLayoutParams(new LinearLayout.LayoutParams(ResolutionUtils.DEVICE_SCREEN_WIDTH,
                ResolutionUtils.DEVICE_SCREEN_HEIGHT));
        setOrientation(VERTICAL);
        FrameLayout frameLayout = new FrameLayout(getContext());
        frameLayout.setLayoutParams(new LinearLayout.LayoutParams(ResolutionUtils.DEVICE_SCREEN_WIDTH,
                (int) (ResolutionUtils.DEVICE_SCREEN_HEIGHT * 0.4)));
        addView(frameLayout);


        LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
        shopLayout = (LinearLayout) inflater.inflate(R.layout.shop, null);
        shopLayout.setLayoutParams(new LinearLayout.LayoutParams(ResolutionUtils.DEVICE_SCREEN_WIDTH,
                (int) (ResolutionUtils.DEVICE_SCREEN_HEIGHT * 0.6)));
        addView(shopLayout);

        initShopLayout();


        ((Button) findViewById(R.id.shop_back)).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                move();
                soundPool.play(sound, 1, 1, 0, 0, 1);
            }
        });

        downAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                back();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        setVisibility(INVISIBLE);
        INSTANCE = this;
    }

    public void move() {
        if (active) {
            startAnimation(downAnim);
            setVisibility(INVISIBLE);
        } else {
            setVisibility(VISIBLE);
            startAnimation(upAnim);
        }
        active = !active;
    }

    private void back() {
        ((GameActivity) getContext()).menuScrollView.scrollToActiveScreen(1);
    }

    private void initShopLayout() {

        LinearLayout skins = (LinearLayout) findViewById(R.id.skins_scroll_view);
        ShopItemsHolder.buildSkinsView(skins);
        LinearLayout powers = (LinearLayout) findViewById(R.id.powers_scroll_view);
        ShopItemsHolder.buildPowersView(powers);

        final TabHost tabHost = (TabHost) findViewById(R.id.shop_tabHost);
        tabHost.setup();
//        tabHost.getTabWidget().setShowDividers(SHOW_DIVIDER_NONE);
//        tabHost.getTabWidget().setDividerDrawable(null);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
                    if (i == tabHost.getCurrentTab()) {
                        ((Button) tabHost.getTabWidget().getChildTabViewAt(i)).setShadowLayer(5, 0, 0, Color.WHITE);
                    } else {
                        ((Button) tabHost.getTabWidget().getChildTabViewAt(i)).setShadowLayer(2, 0, 0, Color.BLACK);
                    }
                }
            }
        });

        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec("Skins");
//        tabSpec.setIndicator("Skins");
        tabSpec.setContent(R.id.skins_tab);
        Button button = (Button) inflate(getContext(), R.layout.tab, null);
//        button.setOnTouchListener(Utils.glowTextButtonOnTouchListener);
        button.setText("Skins");
        button.setTypeface(Utils.FONT);
        tabSpec.setIndicator(button);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Powers");
//        tabSpec.setIndicator("Powers");
        tabSpec.setContent(R.id.powers_tab);
        button = (Button) inflate(getContext(), R.layout.tab, null);
//        button.setOnTouchListener(Utils.glowTextButtonOnTouchListener);
        button.setText("Powers");
        button.setTypeface(Utils.FONT);
        tabSpec.setIndicator(button);
        tabHost.addTab(tabSpec);

        /*tabSpec = tabHost.newTabSpec("Premium");
//        tabSpec.setIndicator("Premium");
        tabSpec.setContent(R.id.premium_tab);
        button = (Button) inflate(getContext(), R.layout.tab, null);
//        button.setOnTouchListener(Utils.glowTextButtonOnTouchListener);
        button.setText("Premium");
        button.setTypeface(Utils.FONT);
        tabSpec.setIndicator(button);
        tabHost.addTab(tabSpec);*/

        tabHost.setCurrentTab(0);
    }

    public static void destroy() {
        getInstance().shopLayout = null;
        INSTANCE = null;
    }

    public static ShopView getInstance() {
        return INSTANCE;
    }
}
