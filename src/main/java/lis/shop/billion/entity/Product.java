package lis.shop.billion.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Сутність Product представляє товар, доступний у магазині.
 *
 * Містить назву, опис, ціну, кількість на складі, категорію
 * та дату створення товару.
 */

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    /**
     * Унікальний ідентифікатор товару.
     * Генерується автоматично базою даних.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Назва товару.
     * Обов’язкове поле з максимальною довжиною 255 символів.
     */
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * Опис товару.
     * Зберігається як текст без обмеження довжини.
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * Ціна товару з точністю до копійок.
     * Не може бути null.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    /**
     * Кількість товару, доступного на складі.
     * Необхідне поле для контролю залишків.
     */
    @Column(nullable = false)
    private Integer stockQuantity;

    /**
     * Категорія, до якої належить товар.
     * Зв’язок ManyToOne з сутністю Category.
     */
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    /**
     * Дата і час створення запису про товар.
     * Встановлюється автоматично при створенні, не змінюється при оновленні.
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}

