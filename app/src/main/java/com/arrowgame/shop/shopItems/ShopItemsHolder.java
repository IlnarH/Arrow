package com.arrowgame.shop.shopItems;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arrowgame.R;
import com.arrowgame.gameprocess.GameManager;
import com.arrowgame.gameprocess.animation.ArrowAnimation;
import com.arrowgame.gameprocess.powers.Power;
import com.arrowgame.gameprocess.powers.SlowTimePower;
import com.arrowgame.shop.skins.ArrowEyes;
import com.arrowgame.shop.skins.ArrowSkin;
import com.arrowgame.shop.skins.LanternEyes;
import com.arrowgame.shop.skins.SkinType;
import com.arrowgame.util.DataStoreUtils;
import com.arrowgame.util.Utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShopItemsHolder {

    private static Map<String, SkinShopItem> skins;
    private static SkinShopItem selectedSkin;
    private static ArrayList<Integer> headerIndices;

    private static Map<String, PowerShopItem> powers;
    private static PowerShopItem selectedPower;

    private static LinearLayout skinsLayout;
    private static LinearLayout powersLayout;

    private ShopItemsHolder() {
    }

    public static void init() {
        skins = new LinkedHashMap<String, SkinShopItem>();
        powers = new LinkedHashMap<String, PowerShopItem>();
        selectedSkin = null;
        headerIndices = new ArrayList<Integer>();
        fillSkinsList();
        fillPowersList();
        invalidateAll();
    }

    public static void fillSkinsList() {

        if (!skins.isEmpty()) return;

        addSkinItem(new SkinShopItem(ArrowEyes.ITEM_ID, "Eyes", "Normal eyes", 0, new ArrowEyes()));
        addSkinItem(new SkinShopItem(LanternEyes.ITEM_ID, "Lantern eyes", "Afraid of the night? Shine your way with the power of lantern!",
                50, new LanternEyes()));

        addSkinItem(new SkinShopItem(ArrowSkin.ITEM_ID, "Arrow", "Normal arrow", 0, new ArrowSkin()));

    }

    public static void fillPowersList() {

        if (!powers.isEmpty()) return;

        addPowerItem(new PowerShopItem(SlowTimePower.ITEM_ID, "Slow time", "Activate this power to slow the time and burst all the balloons!", 50, new SlowTimePower(0)));
    }

    public static void buildSkinsView(LinearLayout layout) {
        ShopItemsHolder.skinsLayout = layout;

        SkinType header = null;
        for (SkinShopItem item : new ArrayList<SkinShopItem>(skins.values())) {
            SkinType skinType = item.getType();
            if (header != skinType) {
                View headerLayout = Utils.inflater.inflate(R.layout.shop_list_header, null);
                ((TextView) headerLayout.findViewById(R.id.header_title)).setText(getHeaderName(skinType));
                ((TextView) headerLayout.findViewById(R.id.header_title)).setTypeface(Utils.FONT);
                if (layout.getChildCount() > 0) {
                    layout.removeViewAt(layout.getChildCount() - 1);
                }
                layout.addView(headerLayout);
                header = skinType;
                headerIndices.add(layout.getChildCount() - 1);
            }
            layout.addView(item.getView());
            layout.addView(Utils.inflater.inflate(R.layout.divider, null));
        }
        layout.removeViewAt(layout.getChildCount() - 1);
    }

    public static void buildPowersView(LinearLayout layout) {
        ShopItemsHolder.powersLayout = layout;

        for (PowerShopItem item : new ArrayList<PowerShopItem>(powers.values())) {
            layout.addView(item.getView());
            layout.addView(Utils.inflater.inflate(R.layout.divider, null));
        }
        layout.removeViewAt(layout.getChildCount() - 1);
    }

    private static void addSkinItem(SkinShopItem shopItem) {
        skins.put(shopItem.getId(), shopItem);
    }

    private static void addPowerItem(PowerShopItem shopItem) {
        powers.put(shopItem.getId(), shopItem);
    }

    public static void skinSelected(SkinShopItem item) {
        if (selectedSkin == item) {
            item.expandOrHide();
            showOrHideSkinDividers(item);
            selectedSkin = null;
            GameManager.getManikin().invalidate();
            return;
        }

        if (selectedSkin != null) {
            selectedSkin.expandOrHide();
            showOrHideSkinDividers(selectedSkin);
            GameManager.getManikin().invalidate();
        }
        item.expandOrHide();
        showOrHideSkinDividers(item);
        GameManager.getManikin().setSkin(item.getSkin());

        selectedSkin = item;
    }

    public static void powerSelected(PowerShopItem item) {

        item.expandOrHide();
        showOrHidePowerDividers(item);

        if (selectedPower == null) {
            selectedPower = item;
        } else if (selectedPower != item) {
            selectedPower.expandOrHide();
            showOrHidePowerDividers(selectedPower);
            selectedPower = item;
        } else {
            selectedPower = null;
        }
    }

    /**
     * Show or hide dividers around specified skin item
     *
     * @param item item, around which dividers must be shown/hidden
     */
    private static void showOrHideSkinDividers(SkinShopItem item) {
        int i = skinsLayout.indexOfChild(item.getView());
        int topView = i - 1;
        if (!headerIndices.contains(topView)) {
            View divider = skinsLayout.getChildAt(topView);
            if (divider.getVisibility() == View.INVISIBLE) {
                divider.setVisibility(View.VISIBLE);
            } else {
                divider.setVisibility(View.INVISIBLE);
            }
        }
        int bottomView = i + 1;
        if (!(headerIndices.contains(bottomView) || bottomView == skinsLayout.getChildCount())) {
            View divider = skinsLayout.getChildAt(bottomView);
            if (divider.getVisibility() == View.INVISIBLE) {
                divider.setVisibility(View.VISIBLE);
            } else {
                divider.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * Show or hide dividers around specified power item
     *
     * @param item item, around which dividers must be shown/hidden
     */
    private static void showOrHidePowerDividers(PowerShopItem item) {
        int i = powersLayout.indexOfChild(item.getView());
        if (i != 0) {
            View divider = powersLayout.getChildAt(i - 1);
            if (divider.getVisibility() == View.INVISIBLE) {
                divider.setVisibility(View.VISIBLE);
            } else {
                divider.setVisibility(View.INVISIBLE);
            }
        }
        if (i != powersLayout.getChildCount() - 1) {
            View divider = powersLayout.getChildAt(i + 1);
            if (divider.getVisibility() == View.INVISIBLE) {
                divider.setVisibility(View.VISIBLE);
            } else {
                divider.setVisibility(View.INVISIBLE);
            }
        }
    }

    private static String getHeaderName(SkinType type) {
        switch (type) {
            case EYES:
                return "Eyes";
            case BODY:
                return "Arrow";
            default:
                return "Other";
        }
    }

    public static Skin getSkin(String key) {
        return skins.get(key).getSkin();
    }

    public static Power getPower(String key) {
        return powers.get(key).getPower();
    }

    public static void invalidateAll() {
        for (SkinShopItem skinShopItem : skins.values()) {
            skinShopItem.invalidate();
        }
        for (PowerShopItem powerShopItem : powers.values()) {
            powerShopItem.invalidate();
        }
    }

    public static void syncItemsWithLocalStorage() {
        SharedPreferences.Editor editor = DataStoreUtils.getItemsPreferences().edit();
        Map<String, Integer> storageItems = DataStoreUtils.getPurchasedItems();
        for (SkinShopItem skin : skins.values()) {
            int storagePurchaseLevel = storageItems.get(skin.getId()) == null ? 0 : storageItems.get(skin.getId());
            if (storagePurchaseLevel > skin.getPurchaseLevel()) {
                skin.setPurchaseLevel(storagePurchaseLevel);
            } else {
                editor.putInt(skin.getId(), skin.getPurchaseLevel());
                editor.commit();
            }
        }
        for (PowerShopItem power : powers.values()) {
            int storagePurchaseLevel = storageItems.get(power.getId()) == null ? 0 : storageItems.get(power.getId());
            if (storagePurchaseLevel > power.getPurchaseLevel()) {
                power.setPurchaseLevel(storagePurchaseLevel);
            } else {
                editor.putInt(power.getId(), power.getPurchaseLevel());
                editor.commit();
            }
        }
    }

    public static void destroy() {
        headerIndices = null;
        selectedSkin = null;
        skins = null;
        powers = null;
        selectedPower = null;
    }
}
