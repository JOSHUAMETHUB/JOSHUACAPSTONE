package com.menin.ticketservice.exceptionhandler;


import com.menin.ticketservice.exception.EmptyFieldException;
import com.menin.ticketservice.exception.ServiceNotAvailableException;
import com.menin.ticketservice.exception.SportTicketNotFoundException;
import com.menin.ticketservice.exception.TicketMatchNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;


@ControllerAdvice
@EnableWebMvc
public class ServiceExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler({EmptyFieldException.class,
            SportTicketNotFoundException.class,
            TicketMatchNotFoundException.class,
            ServiceNotAvailableException.class
    })
    public ResponseEntity<ApiError> handleTicketServiceExceptions(Exception ex, ServletWebRequest request) {
        ApiError apiError = new ApiError();

        apiError.setTimeStamp(LocalDateTime.now());
        apiError.setPathUri(request.getDescription(false));
        apiError.setStatus(HttpStatus.BAD_REQUEST);
        apiError.setErrors(Arrays.asList(ex.getClass() + " - " + ex.getMessage()));


        return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}

