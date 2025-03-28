package lis.shop.billion.entity.utilEnum;

/**
 * Перерахування статусів замовлення в інтернет-магазині.
 *
 * Визначає основні етапи життєвого циклу замовлення:
 * - PENDING: Замовлення створене, але ще не оплачене.
 * - PAID: Замовлення оплачене і готується до відправки.
 * - SHIPPED: Замовлення відправлене покупцеві.
 * - DELIVERED: Замовлення доставлене покупцеві.
 * - CANCELED: Замовлення скасоване.
 */
public enum OrderStatus {
    PENDING,   // Очікує оплати
    PAID,      // Оплачено
    SHIPPED,   // Відправлено
    DELIVERED, // Доставлено
    CANCELED   // Скасовано
}
