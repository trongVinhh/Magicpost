package com.magicpost.circus.controller.transaction;

import com.magicpost.circus.payload.TrackingDto;
import com.magicpost.circus.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/tracking")
public class TrackingController {
    @Autowired
    private CustomerService customerService;

    public TrackingController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<TrackingDto> getOrderByCode(@RequestParam("orderCode") String orderCode) {
        TrackingDto dto = this.customerService.trackingOrder(orderCode);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
