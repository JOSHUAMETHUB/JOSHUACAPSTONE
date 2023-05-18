package com.menin.matchservice.constant;

public class ExceptionMessage {

    private ExceptionMessage(){

    }

    public static final String SPORTMATCH_NOT_FOUND =  "sport-match not found or sportmatches is empty.";
    public static final String SPORT_TICKET_NOT_FOUND = "sport-ticket not found or sport tickets is empty.";
    public static final String EMPTY = "Any field must not be null, empty, or blank! Note: either Participants or Teams can be none but not both!";
}
