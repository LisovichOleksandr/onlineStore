package lis.shop.billion.service;

import lis.shop.billion.entity.Category;
import lis.shop.billion.entity.Product;
import lis.shop.billion.exception.ResourceNotFoundException;
import lis.shop.billion.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    private Map<String, Long> categoriesName;

    @BeforeEach
    void setUp() {
        // ініціалізуємо categoriesName вручну
        categoriesName = new HashMap<>();
        categoriesName.put("електроніка", 1L);
        categoriesName.put("одяг", 2L);
    }

    @Test
    void getAllProducts() {
        Product product = new Product(1L, "Alex", "He is hard worker",
                new BigDecimal(200000), 200, new Category(2L, "PI"), LocalDateTime.now());
        Product product1 = new Product(2L, "Bob", "He is bad worker",
                new BigDecimal(1100), 20, new Category(3L, "GI"), LocalDateTime.now());
        List<Product> products = List.of(product, product1);
        // Імітуєм поведінку репозиторія
        when(productRepository.findAll()).thenReturn(products);  // Arrange

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
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));  // Arrange

        // Визиваємо клас для тесту
        Product productFromService = productService.getProductById(1L);  // Act

        // Порівнюємо очікуване з тим що видає сервіс.
        Assertions.assertEquals(productFromService, product);  // Assert
    }

    @Test
    void getProductById_InValidId_ResourceNotFoundException() {
        // Імітуєм поведінку репозиторія
        when(productRepository.findById(1L)).thenThrow(ResourceNotFoundException.class);  // Arrange

        // Провыряэмчи викидаэться виключення
        Assertions.assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(1L));  // Assert
    }

    @Test
    void getProductsByCategory() {
        // given
        Long categoryId = 2L;

        List<Product> mockProducts = new ArrayList<>();

        Product product = new Product(1L, "Товар 1", "He is hard worker",
                new BigDecimal(200000), 200, new Category(2L, "PI"), LocalDateTime.now());
        Product product1 = new Product(2L, "Товар 2", "He is bad worker",
                new BigDecimal(1100), 20, new Category(2L, "PI"), LocalDateTime.now());
        mockProducts.add(product);
        mockProducts.add(product1);

        when(productRepository.findByCategoryId(categoryId)).thenReturn(mockProducts);

        // when
        List<Product> result = productService.getProductsByCategory(categoryId);

        // then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Товар 1", result.get(0).getName());
        assertEquals(2L, result.get(0).getCategory().getId());

        verify(productRepository, times(1)).findByCategoryId(categoryId);
    }

    @Test
    void saveProduct_WithExistingCategory() {
        // given
        Category category = new Category();
        category.setName("електроніка");

        Product inputProduct = new Product();
        inputProduct.setCategory(category);

        Product savedProduct = new Product();
        savedProduct.setId(100L);
        savedProduct.setCategory(new Category(1L, "електроніка"));

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // when
        Product result = productService.saveProduct(inputProduct);

        // then
        assertNotNull(result);
        assertEquals(1L, result.getCategory().getId());
        assertEquals("електроніка", result.getCategory().getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testSaveProduct_WithMissingCategory() {
        // given
        Product inputProduct = new Product();
        inputProduct.setCategory(null); // відсутня категорія

        Product savedProduct = new Product();
        savedProduct.setId(101L);
        savedProduct.setCategory(new Category(5L, "загальна"));

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // when
        Product result = productService.saveProduct(inputProduct);

        // then
        assertNotNull(result);
        assertEquals(5L, result.getCategory().getId());
        assertEquals("загальна", result.getCategory().getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testSaveProduct_WithUnknownCategoryName() {
        // given
        Category unknownCategory = new Category();
        unknownCategory.setName("іграшки"); // цієї категорії немає в map

        Product inputProduct = new Product();
        inputProduct.setCategory(unknownCategory);

        Product savedProduct = new Product();
        savedProduct.setId(102L);
        savedProduct.setCategory(new Category(5L, "загальна"));

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        // when
        Product result = productService.saveProduct(inputProduct);

        // then
        assertNotNull(result);
        assertEquals(5L, result.getCategory().getId());
        assertEquals("загальна", result.getCategory().getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }
}