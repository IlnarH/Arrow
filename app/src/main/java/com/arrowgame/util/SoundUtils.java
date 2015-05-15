package com.arrowgame.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

import com.arrowgame.R;

/**
 * Created by Ильнар on 31.01.2015.
 */
public class SoundUtils {

    private static SoundPool soundPool;

    public static int TIME_SLOW;
    public static int TIME_NORMAL;

    public static void init(Context context) {
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        TIME_SLOW = soundPool.load(context, R.raw.time_slow, 1);
        TIME_NORMAL = soundPool.load(context, R.raw.time_normal, 1);
    }

    public static void play(int sound) {
        soundPool.play(sound, 1, 1, 0, 0, 1);
    }
}
