package lis.shop.billion.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/**
 * Сутність OrderItem представляє окрему позицію в замовленні користувача.
 *
 * Містить інформацію про товар, його кількість та ціну на момент оформлення замовлення.
 */

@Entity
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    /**
     * Унікальний ідентифікатор позиції замовлення.
     * Генерується автоматично.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Замовлення, до якого належить ця позиція.
     * Зв’язок ManyToOne з Order.
     */
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /**
     * Товар, який було додано до замовлення.
     * Зв’язок ManyToOne з Product.
     */
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    /**
     * Кількість одиниць товару у замовленні.
     */
    @Column(nullable = false)
    private Integer quantity;

    /**
     * Ціна одиниці товару на момент додавання до замовлення.
     * Зберігається з точністю до копійок.
     */
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}
