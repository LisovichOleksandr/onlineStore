package lis.shop.billion.controller;

import lis.shop.billion.entity.Category;
import lis.shop.billion.entity.Product;
import lis.shop.billion.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    /**
     * Назва метода складається з трьох частин: назва метода + Test _ умова _ очікувана поведінка
     * */
    @Test
    void getAllProducts_ReturnsListOfProducts() throws Exception {
        // given
        var products = List.of(
                new Product(1L, "Telephone", "He is hard worker", new BigDecimal(200000),
                        200, new Category(2L, "PI"), LocalDateTime.now()),
                new Product(2L, "Computer", "He is bad worker", new BigDecimal(1100),
                        20, new Category(3L, "GI"), LocalDateTime.now()));

        // when
        Mockito.when(productService.getAllProducts()).thenReturn(products);

        // then
        Assertions.assertEquals(productController.getAllProducts(), products);
    }

    @Test
    void getProductById() {
        // given
        Long id = 1L;
        var products = List.of(
                new Product(1L, "Telephone", "He is hard worker", new BigDecimal(200000),
                        200, new Category(2L, "PI"), LocalDateTime.now()),
                new Product(2L, "Computer", "He is bad worker", new BigDecimal(1100),
                        20, new Category(3L, "GI"), LocalDateTime.now()));

        // when
        Mockito.when(productService.getProductById(id)).thenReturn(products.get(1));

        // then
        Assertions.assertEquals(productController.getProductById(id), products.get(1));
    }

    @Test
    void createProduct() {
        // given
        Product product = new Product(1L, "Telephone", "He is hard worker", new BigDecimal(200000),
                200, new Category(2L, "PI"), LocalDateTime.now());

        // when
        Mockito.when(productService.saveProduct(product)).thenReturn(product);

        // then
        Assertions.assertEquals(productController.createProduct(product), product);
    }
}