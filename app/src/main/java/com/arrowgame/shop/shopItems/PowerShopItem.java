package com.arrowgame.shop.shopItems;

import android.util.Log;
import android.view.View;

import com.arrowgame.gameprocess.powers.Power;

/**
 * Created by Ильнар on 29.01.2015.
 */
public class PowerShopItem extends ShopItem {

    private Power power;

    public PowerShopItem(String id, String title, String description, int cost, Power power) {
        super(id, title, description, cost);
        this.power = power;
    }

    public void inflateShopItem() {
        super.inflateShopItem();

        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("1 button", "first");
            }
        });
        secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("2 button", "second");
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShopItemsHolder.powerSelected(PowerShopItem.this);
            }
        });
    }

    //    TODO invalidate buttons then purchase or equip states changed
    public void invalidate() {

    }

    public Power getPower() {
        return power;
    }

    @Override
    public void setPurchaseLevel(int purchaseLevel) {
        super.setPurchaseLevel(purchaseLevel);
        getPower().setLevel(purchaseLevel);
        //TODO upgrade
    }

    @Override
    public boolean isEquipped() {
        return false;
    }

    @Override
    public void equipOrTakeOff() {

    }
}
