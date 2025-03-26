package lis.shop.billion.controller;

import lis.shop.billion.controller.dto.ProductDto;
import lis.shop.billion.entity.Product;
import lis.shop.billion.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контролер для обробки HTTP-запитів, пов’язаних з товарами.
 * Доступні операції: отримати всі товари, отримати товар за ID, створити новий товар.
 *
 * Кожен контролер повертає DTO об'єкт
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
     * @return список об'єктів ProductDto
     */
    @GetMapping
    public List<ProductDto> getAllProducts() {
        List<Product> allProducts = productService.getAllProducts();
        List<ProductDto> allProductDto = allProducts.stream()
                .map(product -> ProductDto.fromProduct(product))
                .collect(Collectors.toList());
        return allProductDto;
    }

    /**
     * Обробляє GET-запит на /api/products/{id}
     * Повертає товар за вказаним ідентифікатором.
     *
     * @param id ідентифікатор товару
     * @return об'єкт ProductDto
     */
    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        Product productById = productService.getProductById(id);
        return ProductDto.fromProduct(productById);
    }

    /**
     * Обробляє POST-запит на /api/products
     * Створює новий товар на основі даних у тілі запиту.
     *
     * @param productDto об'єкт Product, що буде створено
     * @return створений об'єкт Product
     */
    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto) {

        Product savedProduct = productService.saveProduct(Product.fromProductDto(productDto));
        return ProductDto.fromProduct(savedProduct);
    }
}
