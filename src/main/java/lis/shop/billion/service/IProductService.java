package lis.shop.billion.service;

import lis.shop.billion.entity.Product;

import java.util.List;

/**
 * Інтерфейс сервісу для управління товарами (продуктами).
 * Визначає основні операції для роботи з товарами:
 * - отримання всіх товарів;
 * - пошук товару за ID;
 * - отримання товарів за категорією;
 * - збереження або оновлення товару.
 */

public interface IProductService {

    /**
     * Отримати список усіх товарів.
     *
     * @return список об'єктів Product
     */
    List<Product> getAllProducts();

    /**
     * Знайти товар за його унікальним ідентифікатором.
     *
     * @param id ідентифікатор товару
     * @return об'єкт Product
     */
    Product getProductById(Long id);

    /**
     * Отримати список товарів, що належать до певної категорії.
     *
     * @param categoryId ідентифікатор категорії
     * @return список товарів
     */
    List<Product> getProductsByCategory(Long categoryId);

    /**
     * Зберегти новий товар або оновити існуючий.
     *
     * @param product об'єкт товару
     * @return збережений або оновлений об'єкт Product
     */
    Product saveProduct(Product product);
}
