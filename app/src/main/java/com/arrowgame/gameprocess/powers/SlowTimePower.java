package com.arrowgame.gameprocess.powers;

import com.arrowgame.util.PhysicsUtils;
import com.arrowgame.util.SoundUtils;

public class SlowTimePower extends Power {

    private static int PERCENT_PER_LEVEL = 30;
    private static double ENERGY_COST = 0.5;
    private boolean active = false;

    public static String ITEM_ID = "SLOW_TIME_POWER";

    public SlowTimePower(int level) {
        super(level, 3);
    }

    @Override
    public void update(double factor) {
        if (active) {
            if (!Energy.decreaseEnergy(factor * ENERGY_COST)) {
                activate();
            }
        }
    }

    @Override
    public void activate() {
        if (active) {
            SoundUtils.play(SoundUtils.TIME_NORMAL);
            restoreTime();
        } else {
            SoundUtils.play(SoundUtils.TIME_SLOW);
            slowdownTime();
        }
        active = !active;
    }

    public void slowdownTime() {
        PhysicsUtils.setTimeSpeed(Math.pow(1 - PERCENT_PER_LEVEL / 100.0, level));
    }

    public void restoreTime() {
        PhysicsUtils.setDefaultTimeSpeed();
    }
}
