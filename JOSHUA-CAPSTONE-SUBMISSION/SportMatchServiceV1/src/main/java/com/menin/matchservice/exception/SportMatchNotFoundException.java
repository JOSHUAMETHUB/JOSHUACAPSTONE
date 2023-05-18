package com.menin.matchservice.exception;

public class SportMatchNotFoundException extends RuntimeException{
    public SportMatchNotFoundException(String msg){
        super(msg);
    }
}
