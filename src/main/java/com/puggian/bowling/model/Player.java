package com.puggian.bowling.model;

import com.puggian.bowling.exceptions.BallsLimitExceededException;

public class Player {
    private final String name;
    private final int number;
    private final Frame[] frames;
    private final Integer[] bonusBallsPins;

    public Player(String name, int number) {
        this.name = name;
        this.number = number;
        this.frames = new Frame[10];
        this.bonusBallsPins = new Integer[2];
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }

    public Frame getFrame(int frame) {
        if (frames[frame] == null) {
            frames[frame] = new Frame();
        }
        return frames[frame];
    }

    public int getBonusBallPins(int ball) {
        return this.bonusBallsPins[ball];
    }

    public boolean addBonusBall(int pins) {
        if (bonusBallsPins[0] == null) {
            bonusBallsPins[0] = pins;
            return false;
        }
        if (bonusBallsPins[1] == null) {
            bonusBallsPins[1] = pins;
            return true;
        }
        throw new BallsLimitExceededException("Exceeded the limit of balls.");
    }
}
