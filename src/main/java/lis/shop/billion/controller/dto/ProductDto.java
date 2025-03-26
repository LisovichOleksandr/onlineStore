package lis.shop.billion.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Об'єкт передачі даних (DTO) для представлення інформації про продукт.
 * Використовується для передачі даних про продукти між різними рівнями додатка,
 * таким як контролери, та front-end, передаючи інформацію.
 */

public record ProductDto(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Integer stockQuantity,
        String Category,
        LocalDateTime createdAt
) {
}
