package com.magicpost.circus.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class MagicPostException extends RuntimeException {
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
