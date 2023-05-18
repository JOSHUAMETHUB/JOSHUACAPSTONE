package com.menin.ticketservice.exception;

public class TicketMatchNotFoundException extends RuntimeException {
    public TicketMatchNotFoundException(String matchNotFound) {
        super(matchNotFound);
    }
}
