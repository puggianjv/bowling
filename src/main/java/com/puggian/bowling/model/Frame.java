package com.puggian.bowling.model;

import com.puggian.bowling.exceptions.FrameBallsLimitExceededException;
import com.puggian.bowling.exceptions.FramePinsLimitExceededException;

public class Frame {

    private Ball firstBall = null;
    private Ball secondBall = null;
    private boolean finished = false;
    private int score = 0;

    public boolean addBall(int pins, boolean fault) {
        if (firstBall == null) {
            firstBall = new Ball(pins, fault);
            if (pins == 10) {
                finished = true;
            }
            return finished;
        } else if (secondBall == null) {
            secondBall = new Ball(pins, fault);
            validatePinsLimit();
            finished = true;
            return true;
        } else {
            throw new FrameBallsLimitExceededException("Frames can only have 2 balls");
        }
    }

    public Ball getFirstBall() {
        return firstBall;
    }

    public Ball getSecondBall() {
        return secondBall;
    }

    private void validatePinsLimit() {
        if (this.firstBall.getPins() + this.secondBall.getPins() > 10) {
            throw new FramePinsLimitExceededException("Frames can only sum a maximum of 10 pins");
        }
    }

    public boolean isFinished() {
        return finished;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
