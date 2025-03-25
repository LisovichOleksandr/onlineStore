package lis.shop.billion.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Інтеграційні тести для контролера
 * */

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createProduct() throws Exception {
        // given
       String jsonRequest = "{\"name\":\"Telephone\", \"description\":\"He is hard worker\",\"price\":\"200\" , \"stockQuantity\":\"200000\"}";

        // when
        var response = mockMvc.perform(
                post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
        ).andReturn();

        // then
        String resultJson = response.getResponse().getContentAsString();

        Assertions.assertEquals("{\"id\":1, \"name\":\"Telephone\", \"description\":\"He is hard worker\",\"price\":200 , \"stockQuantity\":200000}", jsonRequest);
// TODO test не працює. Доробити!
    }
}