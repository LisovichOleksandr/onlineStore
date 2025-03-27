package lis.shop.billion.controller;

import lis.shop.billion.controller.dto.ProductDto;
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
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

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
        List<ProductDto> listProductDto = products.stream().
                map(product -> ProductDto.fromProduct(product))
                .collect(Collectors.toList());
        Assertions.assertEquals(productController.getAllProducts(), listProductDto);
    }

    @Test
    void getProductById() {
        // given
        Long id = 1L;
        Product product = new Product(id, "Telephone", "He is hard worker", new BigDecimal(200000),
                200, new Category(2L, "PI"), LocalDateTime.now());

        // when
        Mockito.when(productService.getProductById(id)).thenReturn(product);

        // then
        Assertions.assertEquals(productController.getProductById(id), ProductDto.fromProduct(product));
    }

    @Test
    void createProduct() {
        // given
        LocalDateTime date = LocalDateTime.parse("2025-03-26T06:22:59.241945340");
        Product product = new Product(null, "Telephone", "He is hard worker", new BigDecimal(200000),
                200, new Category(null, "PI"), null);
        Product productReturn = new Product(1L, "Telephone", "He is hard worker", new BigDecimal(200000),
                200, new Category(2L, "PI"), date);
        // null поля для id та createdAt тому-що id встановлюється в БД, а createdAt в service
        ProductDto productDtoRequest = new ProductDto(
                null, "Telephone", "He is hard worker", new BigDecimal(200000), 200, "PI", null
        );
        ProductDto productDtoResponse = new ProductDto(
                1L, "Telephone", "He is hard worker", new BigDecimal(200000), 200, "PI", date);

        // when
        Mockito.when(productService.saveProduct(any(Product.class))).thenReturn(productReturn);

        // then
        Assertions.assertEquals(productController.createProduct(productDtoRequest), productDtoResponse);
    }
}