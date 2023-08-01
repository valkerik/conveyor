package com.example.conveyor.exceptions;

public class ScoreDenyedException extends Exception{

    public ScoreDenyedException(String message) {
        super(message);
    }

    public ScoreDenyedException() {
    }

    public ScoreDenyedException(String message, Throwable ex) {
        super(message, ex);
    }

    public ScoreDenyedException(Throwable ex) {
        super(ex);
    }
}
