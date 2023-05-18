package com.menin.tournamentservice.exception;

public class ServiceNotAvailableException extends RuntimeException {
    public ServiceNotAvailableException(String s) {
        super(s);
    }
}
