-- Database: ticketdb

-- DROP DATABASE IF EXISTS ticketdb;

CREATE DATABASE ticketdb
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'English_United States.1252'
    LC_CTYPE = 'English_United States.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1
    IS_TEMPLATE = False;
	
	
	select * from matches;
	
	select * from tournaments;
	
	select * from tickets;
	
	select * from sportfields;