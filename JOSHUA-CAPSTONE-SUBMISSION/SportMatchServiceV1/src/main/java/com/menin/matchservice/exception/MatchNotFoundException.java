package com.menin.matchservice.exception;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException(String sportmatchNotFound) {
        super(sportmatchNotFound);
    }
}
