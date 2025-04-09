package lis.shop.billion.controller.dto;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) для представлення користувача.
 *
 * Цей клас використовується для передачі даних користувача між різними шарами додатку
 * між контролером і сервісом без розкриття внутрішньої моделі даних (Entity).
 * відсилається на front-end, передаючи інформацію про користувача, для його особистої сторінки.
 *
 * Використовується Java Record для зменшення шаблонного коду — автоматично створюються конструктор, геттери,
 * equals(), hashCode() і toString().
 *
 * @param id         Унікальний ідентифікатор користувача
 * @param firstName  Ім’я користувача
 * @param lastName   Прізвище користувача
 * @param age        Вік користувача
 * @param email      Email-адреса користувача
 * @param createdAt  Дата та час створення облікового запису
 * @param photoUrl   URL-адреса фото користувача
 */

public record UserDto (
        Long id,
        String nickname,
        String firstName,
        String lastName,
        Integer age,
        String email,
        LocalDateTime createdAt,
        String photoUrl
) {
}
