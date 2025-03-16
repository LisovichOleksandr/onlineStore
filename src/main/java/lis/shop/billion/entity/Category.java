package lis.shop.billion.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Сутність Category представляє категорію товарів в інтернет-магазині.
 *
 * Кожна категорія має унікальний ідентифікатор (id) та унікальну назву (name),
 * яка використовується для групування товарів.
 */

@Entity
@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {

    /**
     * Унікальний ідентифікатор категорії.
     * Генерується автоматично базою даних.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Назва категорії.
     * Обов’язкове поле, має бути унікальним і не перевищувати 100 символів.
     */
    @Column(nullable = false, unique = true, length = 100)
    private String name;
}

