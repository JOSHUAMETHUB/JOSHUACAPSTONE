package com.menin.playerservice.exception;

import com.menin.playerservice.constant.PlayerExceptionMsg;

public class EmptyFieldException extends RuntimeException{
    public EmptyFieldException(){
        super(PlayerExceptionMsg.EMPTY);
    }
}
