package ru.theflampu.backend.service;

import ru.theflampu.backend.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByEmail(String email);

    void save(User user);
}
