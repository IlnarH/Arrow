package com.arrowgame.gameprocess.powers;

public abstract class Power {

    protected int level = 0;
    protected int maxLevel;

    protected Power(int level, int maxLevel) {
        this.level = level;
        this.maxLevel = maxLevel;
    }

    public abstract void update(double factor);

    public abstract void activate();

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = (level > maxLevel ? maxLevel : level);
    }
}
