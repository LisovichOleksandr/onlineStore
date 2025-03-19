package lis.shop.billion.service;

import lis.shop.billion.entity.Category;
import lis.shop.billion.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Сервіс для управління категоріями товарів.
 * Забезпечує основні CRUD-операції з категоріями:
 * - отримання всіх категорій;
 * - пошук категорії за назвою;
 * - збереження нової або оновлення існуючої категорії.
 *
 * Всі методи виконуються у межах транзакції завдяки анотації @Transactional.
 */

@Service
@Transactional
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    // Репозиторій для доступу до таблиці категорій
    private final CategoryRepository categoryRepository;

    /**
     * Отримати список усіх категорій з бази даних.
     *
     * @return список всіх об'єктів Category
     */
    public List<Category> getAllCategories() {
        List<Category> all = categoryRepository.findAll();
        return all;
    }

    /**
     * Знайти категорію за її назвою.
     *
     * @param name назва категорії
     * @return Optional з об'єктом Category, якщо знайдено
     */
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }

    /**
     * Зберегти нову категорію або оновити існуючу.
     *
     * @param category об'єкт категорії для збереження
     * @return збережений об'єкт Category
     */
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }
}