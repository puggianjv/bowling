package com.puggian.bowling.model;

import com.puggian.bowling.exceptions.FrameBallsLimitExceededException;
import com.puggian.bowling.exceptions.FramePinsLimitExceededException;

public class Frame {

    private Integer firstBallPins = null;
    private Integer secondBallPins = null;
    private boolean finished = false;
    private int score = 0;

    public boolean addBall(int pins) {
        if (firstBallPins == null) {
            firstBallPins = pins;
            if (pins == 10) {
                finished = true;
            }
            return finished;
        } else if (secondBallPins == null) {
            secondBallPins = pins;
            validatePinsLimit();
            finished = true;
            return true;
        } else {
            throw new FrameBallsLimitExceededException("Frames can only have 2 balls");
        }
    }

    public Integer getFirstBallPins() {
        return firstBallPins;
    }

    public Integer getSecondBallPins() {
        return secondBallPins;
    }

    private void validatePinsLimit() {
        if (this.firstBallPins + this.secondBallPins > 10) {
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
