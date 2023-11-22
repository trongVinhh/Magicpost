package com.magicpost.circus.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class MagicPostException extends RuntimeException {
    @Getter
    private HttpStatus status;
    private String message;

    public MagicPostException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public MagicPostException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
