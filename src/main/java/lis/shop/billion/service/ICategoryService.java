package lis.shop.billion.service;

import lis.shop.billion.entity.Category;

import java.util.List;
import java.util.Optional;

/**
 * Інтерфейс для сервісу управління категоріями товарів.
 * Визначає основні методи для взаємодії з категоріями:
 * - отримання всіх категорій;
 * - пошук категорії за назвою;
 * - збереження нової або оновлення існуючої категорії.
 */

public interface ICategoryService {

    /**
     * Отримати список усіх категорій.
     *
     * @return список об'єктів Category
     */
    public List<Category> getAllCategories();

    /**
     * Знайти категорію за назвою.
     *
     * @param name назва категорії
     * @return Optional з категорією, якщо знайдено
     */
    public Optional<Category> findByName(String name);

    /**
     * Зберегти або оновити категорію.
     *
     * @param category категорія для збереження
     * @return збережена категорія
     */
    public Category saveCategory(Category category);
}
