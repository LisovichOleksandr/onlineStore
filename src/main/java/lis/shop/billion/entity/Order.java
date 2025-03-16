package lis.shop.billion.entity;

import jakarta.persistence.*;
import lis.shop.billion.entity.utilEnum.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сутність Order представляє замовлення користувача в інтернет-магазині.
 *
 * Містить інформацію про користувача, статус замовлення, загальну суму,
 * список товарів у замовленні та дату створення.
 */

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    /**
     * Унікальний ідентифікатор замовлення.
     * Генерується автоматично.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Користувач, який створив замовлення.
     * Зв’язок ManyToOne з сутністю User.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Статус замовлення (PENDING, PAID, SHIPPED тощо).
     * Зберігається у вигляді рядка.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status = OrderStatus.PENDING;

    /**
     * Загальна сума замовлення.
     * Зберігається з точністю до копійок.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    /**
     * Список товарів у цьому замовленні.
     * Зв’язок OneToMany з OrderItem.
     * CascadeType.ALL — всі операції (збереження, видалення) передаються дочірнім об'єктам.
     * orphanRemoval=true — видалення товару із замовлення призведе до видалення з БД.
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    /**
     * Дата і час створення замовлення.
     * Значення встановлюється при створенні і не оновлюється.
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
