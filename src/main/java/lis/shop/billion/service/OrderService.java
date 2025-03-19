package lis.shop.billion.service;

import lis.shop.billion.entity.Order;
import lis.shop.billion.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Сервіс для управління замовленнями (Order).
 * Надає методи для:
 * - отримання замовлень певного користувача;
 * - пошуку замовлення за ідентифікатором;
 * - збереження нового або оновлення існуючого замовлення.
 *
 * Всі методи виконуються у межах транзакції (@Transactional).
 */

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    // Репозиторій для взаємодії з таблицею замовлень
    private final OrderRepository orderRepository;

    /**
     * Отримати всі замовлення, що належать користувачу.
     *
     * @param userId ідентифікатор користувача
     * @return список замовлень користувача
     */
    public List<Order> getOrdersByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    /**
     * Отримати замовлення за його ідентифікатором.
     *
     * @param id ідентифікатор замовлення
     * @return Optional з замовленням, якщо знайдено
     */
    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    /**
     * Зберегти нове або оновити існуюче замовлення.
     *
     * @param order об'єкт замовлення для збереження
     * @return збережений об'єкт Order
     */
    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }
}