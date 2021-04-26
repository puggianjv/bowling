package com.puggian.bowling.model;

public class Ball {
    private int pins;
    private boolean fault;

    public Ball(int pins, boolean fault) {
        this.pins = pins;
        this.fault = fault;
    }

    public int getPins() {
        return pins;
    }

    public void setPins(int pins) {
        this.pins = pins;
    }

    public boolean isFault() {
        return fault;
    }

    public void setFault(boolean fault) {
        this.fault = fault;
    }

    @Override
    public String toString() {
        if (fault) {
            return "F";
        } else {
            return String.valueOf(pins);
        }
    }
}
