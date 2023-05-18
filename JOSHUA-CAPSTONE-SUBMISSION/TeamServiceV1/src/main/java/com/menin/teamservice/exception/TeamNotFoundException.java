package com.menin.teamservice.exception;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(String m) {
        super(m);
    }
}
