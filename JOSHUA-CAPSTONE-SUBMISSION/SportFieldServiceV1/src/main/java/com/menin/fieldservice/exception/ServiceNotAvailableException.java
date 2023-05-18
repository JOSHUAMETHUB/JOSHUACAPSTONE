package com.menin.fieldservice.exception;

public class ServiceNotAvailableException extends RuntimeException {
    public ServiceNotAvailableException(String authenticationServiceIsUnavailable) {
        super(authenticationServiceIsUnavailable);
    }
}
