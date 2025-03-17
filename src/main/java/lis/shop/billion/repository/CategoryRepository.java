package lis.shop.billion.repository;

import lis.shop.billion.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторій CategoryRepository надає доступ до операцій CRUD
 * (створення, читання, оновлення, видалення) над сутністю Category
 * за допомогою механізмів Spring Data JPA.
 *
 * JpaRepository<Category, Long> автоматично забезпечує стандартні методи
 * для взаємодії з базою даних, де:
 * - Category — це тип сутності,
 * - Long — тип первинного ключа (ID) цієї сутності.
 *
 * Додатково, оголошено метод findByName(String name), який дозволяє
 * знаходити категорію за її назвою. Повертає Optional<Category>,
 * що дозволяє безпечно працювати з результатом (може бути відсутнім).
 */

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
