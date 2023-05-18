package com.menin.fieldservice.constant;

public class ExceptionMessage {

    private ExceptionMessage(){

    }
    public static final String SPORTFIELD_INVALID_NAME = "name for SportField is either null/empty or invalid.";
    public static final String SPORTFIELD_CAPACITY_EXCEEDED = "capacity for Sport-field exceeded.";

    public static final String SPORTFIELD_NOT_FOUND = "sport-field not found or sportfields is empty.";
    public static final String SPORTMATCH_NOT_FOUND =  "sport-match not found or sportmatches is empty.";
    public static final String SPORT_TICKET_NOT_FOUND = "sport-ticket not found or sport tickets is empty.";
    public static final String EMPTY = "Any field must not be empty, blank, or null";
}
