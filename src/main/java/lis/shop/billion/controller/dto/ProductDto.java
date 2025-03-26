package lis.shop.billion.controller.dto;

import lis.shop.billion.entity.Product;

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
        String category,
        LocalDateTime createdAt
) {
    // Статичний фабричний метод для створення ProductDto з Product
    public static ProductDto fromProduct(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCategory().getName(),
                product.getCreatedAt()
        );
    }
}
