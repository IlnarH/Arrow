package com.arrowgame.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.arrowgame.R;
import com.arrowgame.util.Utils;

public class PriceView extends LinearLayout {

    private TextView cost;

    public PriceView(Context context) {
        this(context, null);
    }

    public PriceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Utils.inflater.inflate(R.layout.price, this);
        View coin = findViewById(R.id.coin);
        ViewGroup.LayoutParams params = coin.getLayoutParams();
        params.height = params.width;
        coin.setLayoutParams(params);
        cost = (TextView) findViewById(R.id.cost);
        cost.setTypeface(Utils.FONT);
    }

    public void setCost(int cost) {
        this.cost.setText(Integer.toString(cost));
    }
}
