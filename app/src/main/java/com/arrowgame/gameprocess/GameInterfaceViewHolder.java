package com.arrowgame.gameprocess;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.arrowgame.R;
import com.arrowgame.gameprocess.powers.Energy;
import com.arrowgame.menu.MenuViewsHolder;
import com.arrowgame.util.Utils;


public class GameInterfaceViewHolder {

    private View gameInterfaceLayout;
    private TextView remainingTime;
    private View energyBar;
    private View energyBarSpace;
    private View fliedDistance;
    private View score;

    Handler handler;

    private TableRow.LayoutParams barParams;
    private TableRow.LayoutParams spaceParams;

    private static GameInterfaceViewHolder INSTANCE;

    private GameInterfaceViewHolder() {
        gameInterfaceLayout = Utils.inflater.inflate(R.layout.game_interface_layout, null);
        remainingTime = (TextView) gameInterfaceLayout.findViewById(R.id.remainingTime);
        remainingTime.setTypeface(Utils.FONT);
        energyBar = gameInterfaceLayout.findViewById(R.id.energy_bar);
        barParams = new TableRow.LayoutParams(0, 20, 1);
        energyBar.setLayoutParams(barParams);
        energyBarSpace = gameInterfaceLayout.findViewById(R.id.energy_bar_space);
        spaceParams = new TableRow.LayoutParams(0, 20, 0);
        energyBarSpace.setLayoutParams(spaceParams);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                invalidate();
            }
        };

        gameInterfaceLayout.findViewById(R.id.stop_game).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GameManager.stopGame();
                MenuViewsHolder.stopGame();
            }
        });

        gameInterfaceLayout.setVisibility(View.INVISIBLE);
    }

    public static GameInterfaceViewHolder getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new GameInterfaceViewHolder();
        }
        return INSTANCE;
    }

    public static View getGameInterfaceLayout() {
        return getINSTANCE().gameInterfaceLayout;
    }

    public static TextView getRemainingTime() {
        return getINSTANCE().remainingTime;
    }

    public static View getEnergyBar() {
        return getINSTANCE().energyBar;
    }

    private void invalidate() {
        remainingTime.setText(Double.toString(GameManager.getInstance().getFormattedTimeLeft()));

        double percents = Energy.getEnergyPercent();
        barParams.weight = (float) percents;
        spaceParams.weight = (float) (1 - percents);
        energyBar.setLayoutParams(barParams);
        energyBarSpace.setLayoutParams(spaceParams);
    }

    public static void destroy() {
        INSTANCE = null;
    }

    public static void sendInvalidateRequest() {
        getINSTANCE().handler.sendEmptyMessage(0);
    }

    public static void showOrHide() {
        if (getINSTANCE().gameInterfaceLayout.getVisibility() == View.INVISIBLE) {
            getINSTANCE().gameInterfaceLayout.setVisibility(View.VISIBLE);
        } else {
            getINSTANCE().gameInterfaceLayout.setVisibility(View.INVISIBLE);
        }
    }
}
