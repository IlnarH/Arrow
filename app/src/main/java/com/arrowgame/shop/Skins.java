package com.arrowgame.shop;

import com.arrowgame.gameprocess.animation.ArrowAnimation;
import com.arrowgame.shop.skins.ArrowEyes;
import com.arrowgame.shop.skins.ArrowSkin;

import java.util.HashMap;
import java.util.Map;

public class Skins {

    private Skins() {
    }

    public static Map<String, ArrowAnimation> arrowSkins;

    public static void initSkins() {

        arrowSkins = new HashMap<String, ArrowAnimation>();

        arrowSkins.put(ArrowSkin.ITEM_ID, new ArrowSkin());
        arrowSkins.put(ArrowEyes.ITEM_ID, new ArrowEyes());
    }

    public static ArrowAnimation getArrowSkin(String ITEM_ID) {
        return arrowSkins.get(ITEM_ID);
    }
}
