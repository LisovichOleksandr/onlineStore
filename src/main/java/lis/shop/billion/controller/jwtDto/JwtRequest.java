package lis.shop.billion.controller.jwtDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object (DTO) для authentication користувача.
 *
 * Цей клас використовується для передачі даних користувача в контролері і
 * формування access і refresh токенів
 *
 * @param password   Пароль для входу в систему
 * @param email      Email-адреса користувача
 */

public record JwtRequest (
        @NotBlank(message = "Електронна адреса є обовязкова")
        @Email(message = "Електронна адреса повинна бути дійсною")
        String email,
        @Size(min = 6, message = "Пароль менше 6 символів")
        String password){
}
