package lis.shop.billion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lis.shop.billion.entity.Category;
import lis.shop.billion.entity.Product;
import lis.shop.billion.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

class ProductControllerTest {

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

        // then
    }

    @Test
    void getProductById() {
    }

    @Test
    void createProduct() {
    }
}