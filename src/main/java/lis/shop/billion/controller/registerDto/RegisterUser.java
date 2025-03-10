package lis.shop.billion.controller.registerDto;

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

public record RegisterUser(String username,  String email, String password, String confirmPassword) {
    /**
     * Перевіряє, чи співпадає пароль із підтвердженням пароля.
     *
     * @return true, якщо password дорівнює confirmPassword; false – інакше.
     */
    public boolean isSimilarPassword(){
        return password().equals(confirmPassword());
    }
}
