package com.puggian.bowling.model;

import com.puggian.bowling.exceptions.BallsLimitExceededException;

public class Player {
    private final String name;
    private final int number;
    private final Frame[] frames;
    private final Ball[] bonusBalls;

    public Player(String name, int number) {
        this.name = name;
        this.number = number;
        this.frames = new Frame[10];
        this.bonusBalls = new Ball[2];
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

    public Ball getBonusBall(int ball) {
        return this.bonusBalls[ball];
    }

    public boolean addBonusBall(int pins, boolean fault) {
        if (bonusBalls[0] == null) {
            bonusBalls[0] = new Ball(pins, fault);
            return false;
        }
        if (bonusBalls[1] == null) {
            bonusBalls[1] = new Ball(pins, fault);
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
        int firstBallPins = this.frames[i].getFirstBall().getPins();
        score += firstBallPins;
        if (firstBallPins == 10) {
            score += this.getBonusBall(0).getPins();
            score += this.getBonusBall(1).getPins();
        } else {
            int secondBallPins = this.frames[i].getSecondBall().getPins();
            score += secondBallPins;
            if (firstBallPins + secondBallPins == 10) {
                score += this.getBonusBall(0).getPins();
            }
        }
        return score;
    }

    private int getLastButOneFrameScore(int initialScore, int i) {
        int score = initialScore;
        int firstBallPins = this.frames[i].getFirstBall().getPins();
        score += firstBallPins;
        if (firstBallPins == 10) {
            Frame nextFrame = this.frames[i+1];
            score += nextFrame.getFirstBall().getPins();
            if (nextFrame.getFirstBall().getPins() == 10) {
                score += this.getBonusBall(0).getPins();
            }
        } else {
            int secondBallPins = this.frames[i].getSecondBall().getPins();
            score += secondBallPins;
            if (firstBallPins + secondBallPins == 10) {
                score += this.frames[i+1].getFirstBall().getPins();
            }
        }
        return score;
    }

    private int getFrameScore(int initialScore, int i) {
        int score = initialScore;
        int firstBallPins = this.frames[i].getFirstBall().getPins();
        score += firstBallPins;
        if (firstBallPins == 10) {
            Frame nextFrame = this.frames[i + 1];
            score += nextFrame.getFirstBall().getPins();
            if (nextFrame.getFirstBall().getPins() == 10) {
                score += this.frames[i +2].getFirstBall().getPins();
            } else {
                score += nextFrame.getSecondBall().getPins();
            }
        } else {
            int secondBallPins = this.frames[i].getSecondBall().getPins();
            score += secondBallPins;
            if (firstBallPins + secondBallPins == 10) {
                score += this.frames[i +1].getFirstBall().getPins();
            }
        }
        return score;
    }
}
