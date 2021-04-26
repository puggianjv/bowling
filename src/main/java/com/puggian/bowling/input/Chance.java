package com.puggian.bowling.input;

public class Chance {

    public Chance(String playerName, int pins) {
        this.playerName = playerName;
        this.pins = pins;
    }

    private String playerName;
    private int pins;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public int getPins() {
        return pins;
    }

    public void setPins(int pins) {
        this.pins = pins;
    }

    public static Chance fromString(String str) {
        String[] split = str.split(" ");
        String playerName = split[0];
        int pins;

        if (split[1].equals("F")) {
            pins = 0;
        } else {
            pins = Integer.parseInt(split[1]);
        }

        return new Chance(playerName, pins);
    }
}
