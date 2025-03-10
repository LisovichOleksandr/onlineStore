package lis.shop.billion.controller.registerDto;

/**
 * Data Transfer Object (DTO) для реєстрації користувача.
 *
 * Цей клас використовується для відповіді при реєстрації
 *
 * @param email      Email-адреса користувача
 * @param username   ім'я користувача
 */


public record RegisterResponse(String username, String email) {
}
