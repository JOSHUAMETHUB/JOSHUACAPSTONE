package com.menin.tournamentservice.exception;

public class TournamentNotFoundException extends RuntimeException{

    public TournamentNotFoundException(String msg){
        super(msg);
    }
}
