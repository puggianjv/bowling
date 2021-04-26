package com.puggian.bowling.exceptions;

public class WrongOrderException extends RuntimeException {

    public WrongOrderException(String message) {
        super(message);
    }

}
