package lis.shop.billion.repository;

import lis.shop.billion.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * Репозиторій UserRepository надає стандартні CRUD-операції
 * (створення, читання, оновлення, видалення) для сутності User
 * за допомогою Spring Data JPA.
 *
 * JpaRepository<User, Long> означає, що:
 * - User — це тип сутності, з якою працює репозиторій,
 * - Long — тип первинного ключа (ID) для користувача.
 *
 * Додатково, визначено два методи пошуку:
 *
 * - findByUsername(String username):
 *   Повертає Optional<User>, що дозволяє знайти користувача за його іменем користувача.
 *   Часто використовується під час аутентифікації.
 *
 * - findByEmail(String email):
 *   Повертає Optional<User>, що дозволяє знайти користувача за його електронною поштою.
 *   Корисно при реєстрації або відновленні паролю.
 *
 * Обидва методи повертають Optional, щоб зручно й безпечно обробляти випадки,
 * коли користувача з вказаними параметрами не знайдено.
 */

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
