package ayushproject.ayushecommerce.exceptions;
//package com.project.ecommerce.exception;

import java.util.Date;

public class ExceptionResponse {

    //to display when exception has occurred
    private Date timestamps;
    //what exception has occurred
    private String message;
    //detail about the exception
    private String details;

    public ExceptionResponse(Date timestamps, String message, String details) {
        this.timestamps = timestamps;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(Date timestamps) {
        this.timestamps = timestamps;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

}
