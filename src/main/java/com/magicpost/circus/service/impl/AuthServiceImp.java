package com.magicpost.circus.service.impl;

import com.magicpost.circus.entity.person.Employee;
import com.magicpost.circus.exception.GlobalExceptionHandler;
import com.magicpost.circus.exception.MagicPostException;
import com.magicpost.circus.exception.ResourceNotFoundException;
import com.magicpost.circus.payload.LoginDto;
import com.magicpost.circus.repository.EmployeeRepository;
import com.magicpost.circus.security.JwtTokenProvider;
import com.magicpost.circus.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImp implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthServiceImp(AuthenticationManager authenticationManager,
                          JwtTokenProvider jwtTokenProvider,
                          EmployeeRepository employeeRepository,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.employeeRepository = employeeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
    @Override
    public String login(LoginDto loginDto) {

        if(loginDto.getUsernameOrEmail() == null || loginDto.getPassword() == null){
            throw new MagicPostException(HttpStatus.BAD_REQUEST, "Please fill in username or email and password");
        }

        if (loginDto.getUsernameOrEmail().isEmpty() || loginDto.getPassword().isEmpty()) {
            throw new MagicPostException(HttpStatus.BAD_REQUEST, "Please fill in username or email and password");
        }

        // check username have space character
        if (loginDto.getUsernameOrEmail().contains(" ")) {
            throw new MagicPostException(HttpStatus.BAD_REQUEST, "Username or email must not contain space character");
        }

        if (loginDto.getUsernameOrEmail().length() < 3 ) {
            throw new MagicPostException(HttpStatus.BAD_REQUEST, "Username or email and password must be at least 3 characters");
        }

        if (!this.employeeRepository.existsByUsername(loginDto.getUsernameOrEmail())) {
            throw new ResourceNotFoundException(loginDto.getUsernameOrEmail());
        }

        if (this.employeeRepository.existsByUsername(loginDto.getUsernameOrEmail())) {
            Optional<Employee> employee = this.employeeRepository.findByUsername(loginDto.getUsernameOrEmail());
            if (employee.isPresent()) {
                String password = employee.get().getPassword();
                if (!this.bCryptPasswordEncoder.matches(loginDto.getPassword(), password)) {
                    throw new MagicPostException(HttpStatus.BAD_REQUEST, "Password is incorrect");
                }
            }
        }


        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsernameOrEmail(),
                        loginDto.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }
}
