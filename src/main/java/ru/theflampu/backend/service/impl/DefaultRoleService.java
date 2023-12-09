package ru.theflampu.backend.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.theflampu.backend.entity.Role;
import ru.theflampu.backend.repository.RoleRepository;
import ru.theflampu.backend.service.RoleService;

@Service
@AllArgsConstructor
public class DefaultRoleService implements RoleService {
    private RoleRepository roleRepository;

    @Override
    public Role getUserRole() {
        return roleRepository.getRoleByName("ROLE_USER").orElseThrow(RuntimeException::new);
    }
}
