package com.magicpost.circus.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TrackingDto {
    private String orderCode;
    private Long totalPrice;
    private Long mass;
    private String receiveAddress;
    private String receiverName;
    private String phoneNumber;
    private String nameCurrentStorage;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private String status;
}
