package com.magicpost.circus.service;

import com.magicpost.circus.payload.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}
