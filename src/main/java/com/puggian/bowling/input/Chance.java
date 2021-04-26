package com.puggian.bowling.input;

public class Chance {

    private final String playerName;
    private final int pins;
    private final boolean fault;

    public Chance(String playerName, int pins, boolean fault) {
        this.playerName = playerName;
        this.pins = pins;
        this.fault = fault;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPins() {
        return pins;
    }

    public boolean isFault() {
        return fault;
    }

    public static Chance fromString(String str) {
        String[] split = str.split(" ");
        String playerName = split[0];
        int pins;
        boolean fault;

        if (split[1].equals("F")) {
            pins = 0;
            fault = true;
        } else {
            pins = Integer.parseInt(split[1]);
            fault = false;
        }

        return new Chance(playerName, pins, fault);
    }
}
