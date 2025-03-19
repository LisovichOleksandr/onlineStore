package lis.shop.billion.service;
import lis.shop.billion.entity.OrderItem;
import lis.shop.billion.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервіс для управління одиницями замовлення (OrderItem).
 * Відповідає за збереження окремих позицій у замовленні.
 *
 * Всі методи виконуються у межах транзакції (@Transactional).
 */

@Service
@Transactional
@RequiredArgsConstructor
public class OrderItemService {

    // Репозиторій для доступу до таблиці OrderItem
    private final OrderItemRepository orderItemRepository;

    /**
     * Зберегти окрему одиницю замовлення (позицію товару).
     *
     * @param orderItem об'єкт OrderItem для збереження
     * @return збережений об'єкт OrderItem
     */
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }
}