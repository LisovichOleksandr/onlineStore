package lis.shop.billion.service;

import lis.shop.billion.entity.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();

    Product getProductById(Long id);

    List<Product> getProductsByCategory(Long categoryId);

    Product saveProduct(Product product);
}
