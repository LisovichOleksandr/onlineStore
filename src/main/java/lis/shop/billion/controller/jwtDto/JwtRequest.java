package lis.shop.billion.controller.jwtDto;

/**
 * Data Transfer Object (DTO) для authentication користувача.
 *
 * Цей клас використовується для передачі даних користувача в контролері і
 * формування access і refresh токенів
 *
 * @param password   Пароль для входу в систему
 * @param email      Email-адреса користувача
 */

public record JwtRequest (String email, String password){
}
