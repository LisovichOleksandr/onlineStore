package lis.shop.billion.controller;

import lis.shop.billion.entity.Product;
import lis.shop.billion.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

/**
 * Контролер для обробки HTTP-запитів, пов’язаних з товарами.
 * Доступні операції: отримати всі товари, отримати товар за ID, створити новий товар.
 */

@RestController
@RequestMapping("/api/products")    // Базовий шлях для всіх запитів до цього контролера
@RequiredArgsConstructor            // Генерує конструктор з обов’язковими фінальними полями
public class ProductController {
    // Сервіс для бізнес-логіки, пов’язаної з товарами
    private final ProductService productService;

    /**
     * Обробляє GET-запит на /api/products
     * Повертає список усіх товарів.
     *
     * @return список об'єктів Product
     */
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    /**
     * Обробляє GET-запит на /api/products/{id}
     * Повертає товар за вказаним ідентифікатором.
     *
     * @param id ідентифікатор товару
     * @return об'єкт Product
     */
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    /**
     * Обробляє POST-запит на /api/products
     * Створює новий товар на основі даних у тілі запиту.
     *
     * @param product об'єкт Product, що буде створено
     * @return створений об'єкт Product
     */
    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }
}
