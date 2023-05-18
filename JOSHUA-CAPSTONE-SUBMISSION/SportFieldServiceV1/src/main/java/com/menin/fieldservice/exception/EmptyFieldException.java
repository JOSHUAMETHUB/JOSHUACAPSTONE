package com.menin.fieldservice.exception;

import com.menin.fieldservice.constant.ExceptionMessage;

public class EmptyFieldException extends RuntimeException{
    public EmptyFieldException(){
        super(ExceptionMessage.EMPTY);
    }
}
