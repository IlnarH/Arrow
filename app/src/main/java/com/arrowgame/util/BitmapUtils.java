package com.arrowgame.util;

import android.graphics.Bitmap;
import android.util.Log;

import com.arrowgame.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BitmapUtils {

    private static Map<String, Bitmap> bitmaps;

    public static void initBitmaps() {

        bitmaps = new HashMap<String, Bitmap>();

//        bitmaps.put("arrow_3", Utils.makeScaledBitmap(R.drawable.arrow_2_1));
//        bitmaps.put("arrow_4", Utils.makeScaledBitmap(R.drawable.arrow_2_2));
//        bitmaps.put("arrow_1", Utils.getFlippedBitmap(bitmaps.get("arrow_3")));
//        bitmaps.put("arrow_2", Utils.getFlippedBitmap(bitmaps.get("arrow_4")));

        bitmaps.put("ARROW_SKIN", Utils.makeScaledBitmap(R.drawable.arrow_body));
        bitmaps.put("ARROW_EYES_OPEN_LEFT", Utils.makeScaledBitmap(R.drawable.arrow_eyes));
        bitmaps.put("ARROW_EYES_CLOSE_LEFT", Utils.makeScaledBitmap(R.drawable.arrow_eyes_closed));
        bitmaps.put("ARROW_EYES_OPEN_RIGHT", Utils.getFlippedBitmap(getBitmap("ARROW_EYES_OPEN_LEFT")));
        bitmaps.put("ARROW_EYES_CLOSE_RIGHT", Utils.getFlippedBitmap(getBitmap("ARROW_EYES_CLOSE_LEFT")));

        bitmaps.put("ARROW_LANTERN_EYES_OPEN_LEFT", Utils.makeScaledBitmap(R.drawable.arrow_eyes_light));
        bitmaps.put("ARROW_LANTERN_EYES_CLOSE_LEFT", Utils.makeScaledBitmap(R.drawable.arrow_eyes_light_close));
        bitmaps.put("ARROW_LANTERN_EYES_OPEN_RIGHT", Utils.getFlippedBitmap(getBitmap("ARROW_LANTERN_EYES_OPEN_LEFT")));
        bitmaps.put("ARROW_LANTERN_EYES_CLOSE_RIGHT", Utils.getFlippedBitmap(getBitmap("ARROW_LANTERN_EYES_CLOSE_LEFT")));


        bitmaps.put("OrangeBalloon", Utils.makeScaledBitmap(R.drawable.baloon_3));
        bitmaps.put("RedBalloon", Utils.makeScaledBitmap(R.drawable.red_balloon));

        bitmaps.put("BlueBalloon_1", Utils.makeScaledBitmap(R.drawable.blue_balloon_1));
        bitmaps.put("BlueBalloon_2", Utils.makeScaledBitmap(R.drawable.blue_balloon_2));
        bitmaps.put("BlueBalloon_3", Utils.makeScaledBitmap(R.drawable.blue_balloon_3));


        bitmaps.put("burst", Utils.makeScaledBitmap(R.drawable.burst));

        bitmaps.put("cloud_1_l", Utils.makeScaledBitmap(R.drawable.cloud));
        bitmaps.put("cloud_1_m", Utils.makeScaledBitmap(R.drawable.cloud, 0.75));
        bitmaps.put("cloud_1_s", Utils.makeScaledBitmap(R.drawable.cloud, 0.5));

        bitmaps.put("cloud_2_l", Utils.makeScaledBitmap(R.drawable.cloud_2));
        bitmaps.put("cloud_2_m", Utils.makeScaledBitmap(R.drawable.cloud_2, 0.75));
        bitmaps.put("cloud_2_s", Utils.makeScaledBitmap(R.drawable.cloud_2, 0.5));

        bitmaps.put("aura", Utils.makeScaledBitmap(R.drawable.aura));

        bitmaps.put("electric_whip_1", Utils.makeScaledBitmap(R.drawable.elecric_whip_1));
        bitmaps.put("electric_whip_2", Utils.makeScaledBitmap(R.drawable.elecric_whip_2));

        bitmaps.put("electric_field_1", Utils.makeScaledBitmap(R.drawable.electric_field_1));
        bitmaps.put("electric_field_2", Utils.makeScaledBitmap(R.drawable.electric_field_2));
    }

    public static void destroy() {
        for (Bitmap bitmap : bitmaps.values()) {
            bitmap.recycle();
            bitmap = null;
        }
        bitmaps = null;
    }

    public static Bitmap getBitmap(String key) {
        if (bitmaps.get(key) == null) {
            Log.e("BitmapUtils", "Bitmap with given key doesn't exist.");
            return null;
        }
        return bitmaps.get(key);
    }
}
