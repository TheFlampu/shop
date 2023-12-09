package ru.theflampu.backend.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;
import ru.theflampu.backend.entity.User;
import ru.theflampu.backend.request.CreateUserRequest;
import ru.theflampu.backend.response.JwtResponse;
import ru.theflampu.backend.service.impl.DefaultAuthenticationService;
import ru.theflampu.backend.service.impl.DefaultRoleService;
import ru.theflampu.backend.service.impl.DefaultUserService;
import ru.theflampu.backend.util.JwtUtil;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
class AuthenticationServiceTest {
    @Autowired
    private DefaultAuthenticationService authenticationService;
    @MockBean
    private DefaultUserService userService;
    @MockBean
    private DefaultRoleService roleService;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private JwtUtil jwtUtil;

    @Test
    void createUser() {
        CreateUserRequest request = new CreateUserRequest();

        request.setPassword("12345678");

        JwtResponse response = authenticationService.createUser(request);

        Assert.assertNotNull(response);
        Mockito.verify(userService, Mockito.times(1)).save(ArgumentMatchers.any(User.class));
        Mockito.verify(roleService, Mockito.times(1)).getUserRole();
        Mockito.verify(passwordEncoder, Mockito.times(1)).encode(request.getPassword());
        Mockito.verify(jwtUtil, Mockito.times(1)).generateToken(ArgumentMatchers.any(User.class));
    }

    @Test
    void createExistingUser() {
        CreateUserRequest request = new CreateUserRequest();

        request.setEmail("admin@admin.admin");

        Mockito.doReturn(Optional.of(new User()))
                .when(userService)
                .findByEmail("admin@admin.admin");

        Exception exception = Assert.assertThrows(ResponseStatusException.class, () -> authenticationService.createUser(request));

        Assert.assertEquals("400 BAD_REQUEST \"Пользователь с таким email существует\"", exception.getMessage());
        Mockito.verify(userService, Mockito.times(0)).save(ArgumentMatchers.any(User.class));
        Mockito.verify(roleService, Mockito.times(0)).getUserRole();
        Mockito.verify(passwordEncoder, Mockito.times(0)).encode(request.getPassword());
        Mockito.verify(jwtUtil, Mockito.times(0)).generateToken(ArgumentMatchers.any(User.class));
    }
}