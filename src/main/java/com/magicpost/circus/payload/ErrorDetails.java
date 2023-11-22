package com.magicpost.circus.payload;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@Getter @Setter
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
}
