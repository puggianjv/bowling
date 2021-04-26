package com.puggian.bowling.exceptions;

public class FramePinsLimitExceededException extends RuntimeException {

    public FramePinsLimitExceededException(String message) {
        super(message);
    }

}
