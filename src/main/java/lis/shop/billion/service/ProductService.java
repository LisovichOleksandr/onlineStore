package lis.shop.billion.service;

import lis.shop.billion.entity.Product;
import lis.shop.billion.exception.ResourceNotFoundException;
import lis.shop.billion.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * Сервіс для управління товарами (Product).
 * Надає основні методи для:
 * - отримання всіх товарів;
 * - отримання товару за ідентифікатором;
 * - фільтрації товарів за категорією;
 * - збереження нового або оновлення існуючого товару.
 *
 * У разі, якщо товар за ID не знайдено, викидається виняток ResourceNotFoundException.
 */

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService implements IProductService {

    // Репозиторій для взаємодії з базою даних
    private final ProductRepository productRepository;

    /**
     * Отримати всі товари з бази даних.
     *
     * @return список усіх об'єктів Product
     */
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    /**
     * Отримати товар за його унікальним ID.
     *
     * @param id ідентифікатор товару
     * @return об'єкт Product
     * @throws ResourceNotFoundException якщо товар не знайдено
     */
    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Продукта з id = %s не існує.".formatted(id)));
    }

    /**
     * Отримати список товарів, що належать до певної категорії.
     *
     * @param categoryId ідентифікатор категорії
     * @return список товарів цієї категорії
     */
    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    /**
     * Зберегти новий або оновити існуючий товар.
     *
     * @param product об'єкт товару
     * @return збережений об'єкт Product
     */
    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}