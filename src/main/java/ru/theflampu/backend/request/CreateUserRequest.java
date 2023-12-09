package ru.theflampu.backend.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class CreateUserRequest {
    @NotBlank(message = "Имя не может быть пустым")
    @Size(max = 32, message = "Имя слишком длинное")
    private String firstName;

    @NotBlank(message = "Фамилия не может быть пустой")
    @Size(max = 32, message = "Фамилия слишком длинная")
    private String lastName;

    @NotBlank(message = "Email не может быть пустым")
    @Size(max = 128, message = "Email слишком длинный")
    @Pattern(regexp = ".+@.+\\..+", message = "Почта должна быть правильная")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 8, message = "Пароль слишком короткий")
    @Size(max = 256, message = "Пароль слишком длинный")
    private String password;
}
