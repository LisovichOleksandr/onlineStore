package lis.shop.billion.service;

import lis.shop.billion.entity.Category;
import lis.shop.billion.entity.Product;
import lis.shop.billion.exception.ResourceNotFoundException;
import lis.shop.billion.repository.ProductRepository;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;


    @Test
    void getAllProducts() {
        Product product = new Product(1L, "Alex", "He is hard worker",
                new BigDecimal(200000), 200, new Category(2L, "PI"), LocalDateTime.now());
        Product product1 = new Product(2L, "Bob", "He is bad worker",
                new BigDecimal(1100), 20, new Category(3L, "GI"), LocalDateTime.now());
        List<Product> products = List.of(product, product1);
        // Імітуєм поведінку репозиторія
        Mockito.when(productRepository.findAll()).thenReturn(products);  // Arrange

        // Визиваємо клас для тесту
        List<Product> allProducts = productService.getAllProducts();  // Act

        // Порівнюємо очікуване з тим що видає сервіс.
        Assertions.assertEquals(products, allProducts);  // Assert
    }

    @Test
    void getProductById_ValidId_Product() {
        Product product = new Product(1L, "Alex", "He is hard worker",
                new BigDecimal(200000), 200, new Category(2L, "PI"), LocalDateTime.now());
        // Імітуєм поведінку репозиторія
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));  // Arrange

        // Визиваємо клас для тесту
        Product productFromService = productService.getProductById(1L);  // Act

        // Порівнюємо очікуване з тим що видає сервіс.
        Assertions.assertEquals(productFromService, product);  // Assert
    }

    @Test
    void getProductById_InValidId_ResourceNotFoundException() {
        // Імітуєм поведінку репозиторія
        Mockito.when(productRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);  // Arrange

        // Провыряэмчи викидаэться виключення
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));  // Assert
    }

    @Test
    void getProductsByCategory() {
    }

    @Test
    void saveProduct() {
    }
}