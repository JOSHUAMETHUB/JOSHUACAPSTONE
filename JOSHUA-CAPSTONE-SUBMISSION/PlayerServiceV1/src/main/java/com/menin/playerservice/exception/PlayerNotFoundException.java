package com.menin.playerservice.exception;



public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(String msg) {
        super(msg);
    }
}
