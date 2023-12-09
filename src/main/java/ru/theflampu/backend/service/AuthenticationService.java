package ru.theflampu.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import ru.theflampu.backend.request.CreateJwtRequest;
import ru.theflampu.backend.request.CreateUserRequest;
import ru.theflampu.backend.response.JwtResponse;

public interface AuthenticationService extends UserDetailsService {
    @Autowired
    void setAuthenticationManager(AuthenticationManager authenticationManager);

    @Override
    UserDetails loadUserByUsername(String email);

    JwtResponse createJwt(CreateJwtRequest jwtRequest);

    JwtResponse createUser(CreateUserRequest request);
}
