package lis.shop.billion.service;
import lis.shop.billion.entity.Payment;
import lis.shop.billion.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

/**
 * Сервіс для управління оплатами (Payment).
 * Відповідає за збереження інформації про оплату та отримання її за ідентифікатором замовлення.
 *
 * Всі операції виконуються у межах транзакції (@Transactional).
 */

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {
    // Репозиторій для роботи з таблицею оплат
    private final PaymentRepository paymentRepository;

    /**
     * Отримати платіж за ідентифікатором замовлення.
     *
     * @param orderId ідентифікатор замовлення
     * @return Optional з об'єктом Payment, якщо знайдено
     */
    public Optional<Payment> getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    /**
     * Зберегти новий платіж або оновити існуючий.
     *
     * @param payment об'єкт оплати для збереження
     * @return збережений об'єкт Payment
     */
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }
}
