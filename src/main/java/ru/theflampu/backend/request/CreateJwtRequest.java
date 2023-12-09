package ru.theflampu.backend.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateJwtRequest {
    private String email;

    private String password;
}
