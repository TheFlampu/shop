package ru.theflampu.backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.theflampu.backend.entity.User;
import ru.theflampu.backend.request.CreateJwtRequest;
import ru.theflampu.backend.request.CreateUserRequest;
import ru.theflampu.backend.response.JwtResponse;
import ru.theflampu.backend.service.AuthenticationService;
import ru.theflampu.backend.util.JwtUtil;

import java.util.Collections;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultAuthenticationService implements AuthenticationService {
    private final DefaultUserService userService;
    private final DefaultRoleService roleService;

    private AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userService.findByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь " + email + " не найден"));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList())
        );
    }

    @Override
    public JwtResponse createJwt(CreateJwtRequest jwtRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getEmail(), jwtRequest.getPassword()));

        User user = userService.findByEmail(jwtRequest.getEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        return new JwtResponse(jwtUtil.generateToken(user));
    }

    @Override
    public JwtResponse createUser(CreateUserRequest request) {
        if (userService.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Пользователь с таким email существует");
        }

        User newUser = new User();

        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRoles(Collections.singleton(roleService.getUserRole()));

        userService.save(newUser);

        return new JwtResponse(jwtUtil.generateToken(newUser));
    }
}
