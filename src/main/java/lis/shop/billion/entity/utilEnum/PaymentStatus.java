package lis.shop.billion.entity.utilEnum;

/**
 * Перерахування статусів оплати.
 *
 * Визначає можливі стани платежу:
 * - PENDING: Очікується підтвердження або завершення оплати.
 * - COMPLETED: Оплата успішно завершена.
 * - FAILED: Оплата не пройшла або була відхилена.
 */
public enum PaymentStatus {
    PENDING,    // Очікує завершення
    COMPLETED,  // Оплата завершена успішно
    FAILED      // Помилка оплати
}
