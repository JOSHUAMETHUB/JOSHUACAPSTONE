package com.menin.ticketservice.exception;

public class ServiceNotAvailableException extends RuntimeException{

    public ServiceNotAvailableException(String msg){
        super(msg);
    }
}
