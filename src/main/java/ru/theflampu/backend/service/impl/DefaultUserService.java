package ru.theflampu.backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.theflampu.backend.entity.User;
import ru.theflampu.backend.repository.UserRepository;
import ru.theflampu.backend.service.UserService;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DefaultUserService implements UserService {
    private UserRepository userRepository;

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }
}
