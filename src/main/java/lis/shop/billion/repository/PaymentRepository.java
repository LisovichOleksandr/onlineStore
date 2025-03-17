package lis.shop.billion.repository;

import lis.shop.billion.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторій PaymentRepository забезпечує стандартні CRUD-операції
 * (створення, читання, оновлення, видалення) для сутності Payment
 * за допомогою Spring Data JPA.
 *
 * JpaRepository<Payment, Long> означає, що:
 * - Payment — це тип сутності, з якою працює репозиторій,
 * - Long — тип первинного ключа (ID) цієї сутності.
 *
 * Додатково, визначено метод findByOrderId(Long orderId), який дозволяє
 * знайти платіж, пов’язаний із певним замовленням за його ідентифікатором.
 * Метод повертає Optional<Payment>, що дозволяє безпечно обробляти
 * ситуацію, коли платіж може бути відсутнім.
 */

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(Long orderId);
}