package com.arrowgame.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.arrowgame.shop.shopItems.ShopItem;
import com.arrowgame.shop.shopItems.ShopItemsHolder;
import com.arrowgame.shop.skins.ArrowEyes;
import com.arrowgame.shop.skins.ArrowSkin;

import java.util.Map;

/**
 * Created by Ильнар on 29.01.2015.
 */
public class DataStoreUtils {


    public static String SHARED_PREFERENCES = "ARROW_GAME_SHARED_PREFERENCES";
    private static String PURCHASED_ITEMS_PREFERENCES = "ARROW_GAME_PURCHASED_ITEMS";
    public static String FIRST_LAUNCH = "FIRST_LAUNCH";

    private static Context context;


    private DataStoreUtils(){}


    /**
     * Initializes shared preferences. Return value indicates the app launched first time.
     *
     * @param context
     * @return true if app launched first time on this device, otherwise false
     */
    public static boolean init(Context context) {
        DataStoreUtils.context = context;
        SharedPreferences preferences = context.getSharedPreferences(DataStoreUtils.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        if (!preferences.contains(DataStoreUtils.FIRST_LAUNCH)) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(DataStoreUtils.FIRST_LAUNCH, false);
            editor.commit();
            SharedPreferences.Editor itemsEditor = getItemsPreferences().edit();
            itemsEditor.putInt(ArrowEyes.ITEM_ID, 1);
            itemsEditor.putInt(ArrowSkin.ITEM_ID, 1);
            itemsEditor.commit();
            ShopItemsHolder.syncItemsWithLocalStorage();
            return true;
        }
        ShopItemsHolder.syncItemsWithLocalStorage();
        return false;
    }

    public static Map<String,Integer> getPurchasedItems() {
        return (Map<String, Integer>) getItemsPreferences().getAll();
    }

    public static void saveItem(ShopItem item) {
        SharedPreferences.Editor editor = getItemsPreferences().edit();
        editor.putInt(item.getId(), item.getPurchaseLevel());
        editor.commit();
    }

    public static SharedPreferences getItemsPreferences() {
        SharedPreferences preferences = null;
        try {
            preferences = context.getSharedPreferences(PURCHASED_ITEMS_PREFERENCES, Context.MODE_PRIVATE);
        } catch (NullPointerException e) {
            Log.e("ERROR", "Context not initialized while saving shared preferences");
            System.exit(1);
        }
        return preferences;
    }
}
