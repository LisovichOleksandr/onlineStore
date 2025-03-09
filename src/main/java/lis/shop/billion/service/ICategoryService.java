package lis.shop.billion.service;

import lis.shop.billion.entity.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    public List<Category> getAllCategories();
    public Optional<Category> findByName(String name);
    public Category saveCategory(Category category);
}
