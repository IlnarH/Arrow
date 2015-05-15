package com.arrowgame.gameprocess.powers;

import android.view.View;

import com.arrowgame.gameprocess.GameInterfaceViewHolder;
import com.arrowgame.util.Utils;

public class Energy {

    private static Energy INSTANCE;

    private View view;

    private int maxEnergy;
    private double currentEnergy;
    private double energyRestore;

    private Energy() {
        maxEnergy = 100;
        currentEnergy = 100;
        energyRestore = 0.1;
        inflateView();
    }

//    TODO create layout and inflate
    private View inflateView() {
        return null;
    }

    public static View getView() {
        return getINSTANCE().view;
    }

    public static void update(double factor) {
        restoreEnergy(getINSTANCE().energyRestore * factor);
    }

    /**
     * Decreases current energy value; if specified amount of energy was removed returns true, else if specified value more than
     * current value returns false (energy will not be decreased)
     * @param amount energy to decrease
     * @return false if specified value more than current energy, otherwise true
     */
    public static boolean decreaseEnergy(double amount) {
        if (getINSTANCE().currentEnergy - amount < 0) {
            return false;
        } else {
            getINSTANCE().setCurrentEnergy(getINSTANCE().currentEnergy - amount);
            return true;
        }
    }

    public static Energy getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new Energy();
        }
        return INSTANCE;
    }

    public static void restoreEnergy(double energyToRestore) {
        getINSTANCE().setCurrentEnergy(getINSTANCE().currentEnergy + energyToRestore > getINSTANCE().maxEnergy
                ? getINSTANCE().maxEnergy : getINSTANCE().currentEnergy + energyToRestore);
    }

    public static void fillUpEnergy() {
        restoreEnergy(getINSTANCE().maxEnergy);
    }

    public static void destroy() {
        INSTANCE = null;
    }

    private void setCurrentEnergy(double energy) {
        currentEnergy = energy;
    }

    public static double getEnergyPercent() {
        return getINSTANCE().currentEnergy / getINSTANCE().maxEnergy;
    }
}
