package com.menin.matchservice.exception;

import com.menin.matchservice.constant.ExceptionMessage;

public class EmptyFieldException extends RuntimeException{
    public EmptyFieldException(){
        super(ExceptionMessage.EMPTY);
    }
}
