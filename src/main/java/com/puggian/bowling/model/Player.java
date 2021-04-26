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

    public void calculateScore() {
        int score = 0;
        for (int i = 0; i < 8; i++) {
            score = getFrameScore(score, i);
            frames[i].setScore(score);
        }
        score = getLastButOneFrameScore(score, 8);
        frames[8].setScore(score);

        score = getLastFrameScore(score, 9);
        frames[9].setScore(score);
    }

    private int getLastFrameScore(int initialScore, int i) {
        int score = initialScore;
        Integer firstBallPins = this.frames[i].getFirstBallPins();
        score += firstBallPins;
        if (firstBallPins == 10) {
            score += this.getBonusBallPins(0);
            score += this.getBonusBallPins(1);
        } else {
            Integer secondBallPins = this.frames[i].getSecondBallPins();
            score += secondBallPins;
            if (firstBallPins + secondBallPins == 10) {
                score += this.getBonusBallPins(0);
            }
        }
        return score;
    }

    private int getLastButOneFrameScore(int initialScore, int i) {
        int score = initialScore;
        Integer firstBallPins = this.frames[i].getFirstBallPins();
        score += firstBallPins;
        if (firstBallPins == 10) {
            Frame nextFrame = this.frames[i+1];
            score += nextFrame.getFirstBallPins();
            if (nextFrame.getFirstBallPins() == 10) {
                score += this.getBonusBallPins(0);
            }
        } else {
            Integer secondBallPins = this.frames[i].getSecondBallPins();
            score += secondBallPins;
            if (firstBallPins + secondBallPins == 10) {
                score += this.frames[i+1].getFirstBallPins();
            }
        }
        return score;
    }

    private int getFrameScore(int initialScore, int i) {
        int score = initialScore;
        Integer firstBallPins = this.frames[i].getFirstBallPins();
        score += firstBallPins;
        if (firstBallPins == 10) {
            Frame nextFrame = this.frames[i + 1];
            score += nextFrame.getFirstBallPins();
            if (nextFrame.getFirstBallPins() == 10) {
                score += this.frames[i +2].getFirstBallPins();
            } else {
                score += nextFrame.getSecondBallPins();
            }
        } else {
            Integer secondBallPins = this.frames[i].getSecondBallPins();
            score += secondBallPins;
            if (firstBallPins + secondBallPins == 10) {
                score += this.frames[i +1].getFirstBallPins();
            }
        }
        return score;
    }
}
