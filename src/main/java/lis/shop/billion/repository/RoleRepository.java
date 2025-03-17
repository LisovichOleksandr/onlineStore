package lis.shop.billion.repository;

import lis.shop.billion.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторій RoleRepository надає доступ до CRUD-операцій
 * (створення, читання, оновлення, видалення) для сутності Role
 * за допомогою Spring Data JPA.
 *
 * JpaRepository<Role, Long> означає, що:
 * - Role — це тип сутності, з якою працює репозиторій,
 * - Long — тип її первинного ключа (ID).
 *
 * Додатково, оголошено метод findByName(String name), який дозволяє
 * знайти роль за її назвою. Це зручно при реалізації авторизації,
 * коли потрібно отримати роль користувача (наприклад, "ADMIN", "USER").
 *
 * Метод повертає Optional<Role>, що дозволяє уникнути NullPointerException
 * у випадку, якщо роль із заданим ім’ям не існує.
 */

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
