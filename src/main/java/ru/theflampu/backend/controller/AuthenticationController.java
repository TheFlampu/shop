package ru.theflampu.backend.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.theflampu.backend.request.CreateJwtRequest;
import ru.theflampu.backend.request.CreateUserRequest;
import ru.theflampu.backend.response.JwtResponse;
import ru.theflampu.backend.service.AuthenticationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public JwtResponse createJwt(@RequestBody CreateJwtRequest request) {
        return authenticationService.createJwt(request);
    }

    @PostMapping("/register")
    public JwtResponse createNewUser(@RequestBody @Valid CreateUserRequest request) {
        return authenticationService.createUser(request);
    }
}
