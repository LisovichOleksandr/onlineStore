package lis.shop.billion.controller.registerDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Record-клас RegisterUser представляє DTO (Data Transfer Object) для реєстрації нового користувача.
 * Він містить поля username, email, password та confirmPassword.
 *
 * Метод isSimilarPassword() використовується для перевірки, чи співпадає пароль з підтвердженням пароля.
 *
 * Приклад використання:
 * RegisterUser user = new RegisterUser("user1", "user1@example.com", "pass123", "pass123");
 * if (user.isSimilarPassword()) {
 *     // Паролі співпадають, можна продовжити реєстрацію
 * }
 */

public record RegisterUser(
        @NotBlank(message = "Ім'я користувача є обовязкове")
        String username,
        @NotBlank(message = "Електронна адреса є обовязкова")
        @Email(message = "Електронна адреса повинна бути дійсною")
        String email,
        @Size(min = 6, message = "Пароль менше 6 символів")
        String password,
        @Size(min = 6, message = "Пароль менше 6 символів")
        String confirmPassword) {
    /**
     * Перевіряє, чи співпадає пароль із підтвердженням пароля.
     *
     * @return true, якщо password дорівнює confirmPassword; false – інакше.
     */
    public boolean isSimilarPassword(){
        return password().equals(confirmPassword());
    }
}
