package com.magicpost.circus.controller.Auth;

import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.exception.MagicPostException;
import com.magicpost.circus.payload.EmployeeDto;
import com.magicpost.circus.payload.ErrorDetails;
import com.magicpost.circus.payload.JWTAuthResponse;
import com.magicpost.circus.payload.LoginDto;
import com.magicpost.circus.service.AuthService;
import com.magicpost.circus.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private AuthService authService;
    private EmployeeService employeeService;

    @Autowired
    public AuthController(AuthService authService, EmployeeService employeeService) {
        this.authService = authService;
        this.employeeService = employeeService;
    }

    // Build your login endpoint here
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);
        EmployeeDto employeeDto = this.employeeService.getEmployeeByUsername(loginDto.getUsernameOrEmail());
        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        String role = employeeDto.getRole().stream().findFirst().get().getName();
        Long id = employeeDto.getId();
        jwtAuthResponse.setRole(role);
        jwtAuthResponse.setId(id);
        jwtAuthResponse.setUsername(employeeDto.getUsername());
        return ResponseEntity.ok(jwtAuthResponse);
    }
}
