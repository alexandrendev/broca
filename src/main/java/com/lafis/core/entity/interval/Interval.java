package com.lafis.core.entity.interval;

public class Interval {
    private double value;
    private Effect effect;

    public Interval(double value, Effect effect) {
        this.value = value;
        this.effect = effect;
    }

    public double getValue() {
        return value;
    }

    public Effect getEffect() {
        return effect;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }
}
