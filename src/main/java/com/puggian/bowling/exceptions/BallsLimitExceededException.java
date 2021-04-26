package com.puggian.bowling.exceptions;

public class BallsLimitExceededException extends RuntimeException {

    public BallsLimitExceededException(String message) {
        super(message);
    }

}
