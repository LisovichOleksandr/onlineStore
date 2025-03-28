package lis.shop.billion.repository;

import lis.shop.billion.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторій ProductRepository надає доступ до стандартних CRUD-операцій
 * (створення, читання, оновлення, видалення) для сутності Product
 * за допомогою Spring Data JPA.
 *
 * JpaRepository<Product, Long> означає, що:
 * - Product — це тип сутності, з якою працює репозиторій,
 * - Long — тип її первинного ключа (ID).
 *
 * Додатково, визначено метод findByCategoryId(Long categoryId), який дозволяє
 * отримати список продуктів, що належать до певної категорії за її ID.
 *
 * Метод повертає List<Product>, що зручно для подальшої обробки або виведення.
 */

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);


    Optional<Product> findByName(String name);
}
