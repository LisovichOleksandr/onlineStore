package lis.shop.billion.repository;

import lis.shop.billion.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторій OrderItemRepository надає стандартний набір CRUD-операцій
 * (створення, читання, оновлення, видалення) для сутності OrderItem
 * за допомогою функціональності Spring Data JPA.
 *
 * JpaRepository<OrderItem, Long> означає, що:
 * - OrderItem — це тип сутності, з якою працює репозиторій,
 * - Long — тип ідентифікатора (ID) цієї сутності.
 *
 * У цьому інтерфейсі поки що не визначено жодних кастомних методів.
 * Але при потребі можна легко додати, наприклад, пошук за orderId або productId.
 */

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}