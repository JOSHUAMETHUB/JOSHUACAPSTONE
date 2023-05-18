package com.menin.teamservice.exception;

public class TeamAlreadyExistException extends RuntimeException {
    public TeamAlreadyExistException(String msg) {
        super(msg);
    }
}
