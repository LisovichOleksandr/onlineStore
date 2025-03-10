package lis.shop.billion.controller.errorDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Клас для представлення помилки, що виникла у додатку.
 *
 * Використовується для передачі інформації про помилки клієнту в зручному форматі,
 * наприклад у відповіді REST API.
 *
 * Анотації Lombok:
 *   {@code @Data} — генерує геттери, сеттери, toString(), equals() і hashCode()
 *   {@code @NoArgsConstructor} — генерує конструктор без аргументів
 *   {@code @AllArgsConstructor} — генерує конструктор з усіма полями
 *
 * @param statusCode    HTTP статус-код, який описує тип помилки
 * @param message       Повідомлення з описом помилки
 */

public record AppError (int statusCode, String message){
}
