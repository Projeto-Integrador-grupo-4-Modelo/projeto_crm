package com.generation.crm.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ResponseError {

    private String message;
    private HttpStatus httpStatus;
    private LocalDateTime time;

    public ResponseError(String message, HttpStatus httpStatus, LocalDateTime time) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }


}
