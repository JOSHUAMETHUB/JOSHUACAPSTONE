package com.menin.tournamentservice.exception;



import com.menin.tournamentservice.constant.ExceptionMessage;

public class EmptyFieldException extends RuntimeException{
    public EmptyFieldException(){
        super(ExceptionMessage.EMPTY);
    }

    public EmptyFieldException(String s){
        super(s);
    }
}
