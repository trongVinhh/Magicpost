package com.magicpost.circus.entity.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistory {
    private Long id;
    private String status;
    private Order transactionId;
}
