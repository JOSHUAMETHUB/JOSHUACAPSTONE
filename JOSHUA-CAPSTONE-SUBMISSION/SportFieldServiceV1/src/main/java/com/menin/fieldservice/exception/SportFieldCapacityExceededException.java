package com.menin.fieldservice.exception;

public class SportFieldCapacityExceededException extends RuntimeException{
    public SportFieldCapacityExceededException(String msg){
        super(msg);
    }
}
