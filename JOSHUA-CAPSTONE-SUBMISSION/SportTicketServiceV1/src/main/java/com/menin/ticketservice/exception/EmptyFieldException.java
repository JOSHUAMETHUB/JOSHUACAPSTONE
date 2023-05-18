package com.menin.ticketservice.exception;

import com.menin.ticketservice.constant.TicketMessage;

public class EmptyFieldException extends RuntimeException{
    public EmptyFieldException(){
        super(TicketMessage.EMPTY);
    }
}
