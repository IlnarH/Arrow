package com.arrowgame.shop.shopItems;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.arrowgame.R;
import com.arrowgame.util.DataStoreUtils;
import com.arrowgame.util.Utils;
import com.arrowgame.view.PriceView;

public abstract class ShopItem {

    protected String id;
    protected String title;
    protected String description;
    protected int cost;
    protected Button firstButton;
    protected Button secondButton;
    protected TextView titleView;
    protected TextView descriptionView;
    protected PriceView priceView;
    protected View view;
    protected View more;

    protected int purchaseLevel = 0;

    protected ShopItem(String id, String title, String description, int cost) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.cost = cost;
        inflateShopItem();
    }

    public void inflateShopItem() {
        View view = Utils.inflater.inflate(R.layout.shop_list_item, null);

        titleView = (TextView) view.findViewById(R.id.shop_item_title);
        firstButton = (Button) view.findViewById(R.id.shop_item_first_button);
        secondButton = (Button) view.findViewById(R.id.shop_item_second_button);
        priceView = (PriceView) view.findViewById(R.id.shop_item_price);
        descriptionView = (TextView) view.findViewById(R.id.shop_item_description);

        titleView.setTypeface(Utils.FONT);
        firstButton.setTypeface(Utils.FONT);
        secondButton.setTypeface(Utils.FONT);
        descriptionView.setTypeface(Utils.FONT);

        titleView.setText(this.title);
        priceView.setCost(this.cost);
        descriptionView.setText(this.description);

        more = view.findViewById(R.id.shop_item_more);

        this.view = view;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return cost;
    }

    public View getView() {
        return view;
    }

    public abstract void invalidate();

    public void expandOrHide() {
        if (more.getVisibility() == View.GONE) {
            more.setVisibility(View.VISIBLE);
            view.setBackgroundResource(R.drawable.border);
        } else {
            more.setVisibility(View.GONE);
            view.setBackgroundResource(R.color.white);
        }
    }

    public int getPurchaseLevel() {
        return purchaseLevel;
    }

    public void setPurchaseLevel(int purchaseLevel) {
        this.purchaseLevel = purchaseLevel;
        DataStoreUtils.saveItem(this);
        invalidate();
    }

    public abstract boolean isEquipped();

    public abstract void equipOrTakeOff();
}
