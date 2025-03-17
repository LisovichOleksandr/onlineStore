package lis.shop.billion.repository;

import lis.shop.billion.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Репозиторій OrderRepository забезпечує доступ до CRUD-операцій
 * (створення, читання, оновлення, видалення) для сутності Order
 * через Spring Data JPA.
 *
 * JpaRepository<Order, Long> означає, що:
 * - Order — це тип сутності, з якою працює репозиторій,
 * - Long — тип первинного ключа (ID) для Order.
 *
 * Додатково, оголошено метод findByUserId(Long userId), який дозволяє
 * знайти всі замовлення, що належать користувачу з вказаним ID.
 *
 * Повертає список об'єктів Order, які пов'язані з цим користувачем.
 */

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}
