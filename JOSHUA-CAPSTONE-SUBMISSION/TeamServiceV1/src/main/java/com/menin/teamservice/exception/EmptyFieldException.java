package com.menin.teamservice.exception;


import com.menin.teamservice.constant.TeamExceptionMessage;

public class EmptyFieldException extends RuntimeException{
    public EmptyFieldException(){
        super(TeamExceptionMessage.EMPTY);
    }

    public EmptyFieldException(String s){
        super(s);
    }
}
