package com.puggian.bowling.exceptions;

public class FrameBallsLimitExceededException extends RuntimeException {

    public FrameBallsLimitExceededException(String message) {
        super(message);
    }

}
