package com.arrowgame.menu;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.arrowgame.R;
import com.arrowgame.gameprocess.graphics.Camera;
import com.arrowgame.shop.ShopView;
import com.arrowgame.util.ResolutionUtils;

import java.util.ArrayList;
import java.util.List;

public class MenuScrollView extends HorizontalScrollView {

    public static final int MIN_SWIPE_DISTANCE = (int) (20 * ResolutionUtils.getDPI());
    public static final int MIN_SWIPE_VELOCITY = 300;

    private List<View> screens;
    private int activeScreen = 0;
    private GestureDetector gestureDetector;

    private MenuViewsHolder menuViewsHolder;

    public boolean scrollable = true;

    public MenuScrollView(Context context) {
        super(context);
        setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setHorizontalScrollBarEnabled(false);
        generateContent();
        menuViewsHolder = new MenuViewsHolder(this);
    }

    public void generateContent() {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        screens = new ArrayList<View>();
        screens.add(inflater.inflate(R.layout.settings, null));
        screens.add(inflater.inflate(R.layout.main_screen, null));
        screens.add(new ShopView(getContext()));

        LinearLayout wrapper = new LinearLayout(getContext());
        addView(wrapper);
        wrapper.setOrientation(LinearLayout.HORIZONTAL);
        wrapper.setLayoutParams(new FrameLayout.LayoutParams(ResolutionUtils.DEVICE_SCREEN_WIDTH * screens.size(),
                ViewGroup.LayoutParams.MATCH_PARENT));

        screens.get(0).setLayoutParams(new LinearLayout.LayoutParams(ResolutionUtils.DEVICE_SCREEN_WIDTH,
                ViewGroup.LayoutParams.MATCH_PARENT));
        screens.get(1).setLayoutParams(new LinearLayout.LayoutParams(ResolutionUtils.DEVICE_SCREEN_WIDTH,
                ViewGroup.LayoutParams.MATCH_PARENT));

        wrapper.addView(screens.get(0));
        wrapper.addView(screens.get(1));
        wrapper.addView(screens.get(2));

        gestureDetector = new GestureDetector(getContext(), new MenuGestureDetector());
        gestureDetector.setIsLongpressEnabled(false);

        scrollToActiveScreen(0);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (!scrollable) {
                    return true;
                }

                if (gestureDetector.onTouchEvent(motionEvent)) {
                    return true;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP || motionEvent.getAction() == MotionEvent.ACTION_CANCEL) {

                    int scrollX = getScrollX();
                    int screenWidth = view.getWidth();
                    activeScreen = (scrollX + screenWidth / 2) / screenWidth;
                    scrollToActiveScreen(activeScreen);
                    return true;
                } else {
                    return false;
                }
            }
        });

    }

    public void scrollToActiveScreen(int activeScreen) {
        this.activeScreen = activeScreen;
        smoothScrollTo(activeScreen * ResolutionUtils.DEVICE_SCREEN_WIDTH, 0);
        Camera.getInstance().xSmoothScrollTo(activeScreen);
        if (activeScreen == 2) {
            ((ShopView) screens.get(2)).move();
            scrollable = false;

        } else {
            scrollable = true;
        }
        Log.e("Active", activeScreen + "");
    }

    class MenuGestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            if (e1.getX() - e2.getX() > MIN_SWIPE_DISTANCE && Math.abs(velocityX) > MIN_SWIPE_VELOCITY) {

                activeScreen = activeScreen < screens.size() - 1 ? activeScreen + 1 : activeScreen;
                scrollToActiveScreen(activeScreen);
                return true;
            } else if (e2.getX() - e1.getX() > MIN_SWIPE_DISTANCE && Math.abs(velocityX) > MIN_SWIPE_VELOCITY) {

                activeScreen = activeScreen > 0 ? activeScreen - 1 : 0;
                scrollToActiveScreen(activeScreen);
                return true;
            }

            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            {Camera.getInstance().xScrollBy(distanceX / ResolutionUtils.getRESOLUTION_FACTOR());
            return false;}
        }
    }
}
