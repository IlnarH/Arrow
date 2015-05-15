package com.arrowgame.shop.shopItems;

import android.util.Log;
import android.view.View;

import com.arrowgame.gameprocess.GameManager;
import com.arrowgame.gameprocess.animation.ArrowAnimation;
import com.arrowgame.shop.skins.SkinType;

public class SkinShopItem extends ShopItem {

    private Skin skin;

    public SkinShopItem(String id, String title, String description, int cost, Skin skin) {
        super(id, title, description, cost);
        this.skin = skin;
    }

    public void inflateShopItem() {
        super.inflateShopItem();

        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setPurchaseLevel(1);
                Log.e("1 button", "first");
            }
        });
        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equipOrTakeOff();
                Log.e("2 button", "second");
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopItemsHolder.skinSelected(SkinShopItem.this);
            }
        });
    }

    public void invalidate() {
        if (purchaseLevel == 0) {
            priceView.setVisibility(View.VISIBLE);
            firstButton.setVisibility(View.VISIBLE);
            secondButton.setVisibility(View.GONE);
        } else {
            priceView.setVisibility(View.GONE);
            firstButton.setVisibility(View.GONE);
            secondButton.setVisibility(View.VISIBLE);
        }
        if (isEquipped()) {
            secondButton.setText("Take off");
        } else {
            secondButton.setText("Equip");
        }
    }

    public SkinType getType() {
        return skin.getType();
    }

    public Skin getSkin() {
        return skin;
    }

    @Override
    public boolean isEquipped() {
        return GameManager.getArno().getSkin(skin.getType()) == skin;
    }

    @Override
    public void equipOrTakeOff() {
        if (isEquipped()) {
            GameManager.getArno().takeOff(skin);
        } else {
            GameManager.getArno().setSkin(skin);
        }
        GameManager.getManikin().invalidate();
        ShopItemsHolder.invalidateAll();
    }
}
