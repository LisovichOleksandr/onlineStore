package lis.shop.billion.entity;

import jakarta.persistence.*;
import lis.shop.billion.entity.utilEnum.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Сутність Payment представляє інформацію про оплату за конкретне замовлення.
 *
 * Містить метод оплати, статус, дату створення та зв'язок із замовленням.
 */

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    /**
     * Унікальний ідентифікатор платежу.
     * Генерується автоматично.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Замовлення, до якого прив'язаний цей платіж.
     * Зв’язок OneToOne із сутністю Order.
     */
    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /**
     * Метод оплати, який використав користувач (наприклад: "credit_card", "paypal").
     */
    @Column(nullable = false, length = 50)
    private String paymentMethod;

    /**
     * Статус платежу (PENDING, COMPLETED, FAILED).
     * Зберігається як текстове значення.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status = PaymentStatus.PENDING;

    /**
     * Дата і час створення запису про платіж.
     * Встановлюється автоматично при створенні.
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
