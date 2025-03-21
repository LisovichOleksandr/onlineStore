package lis.shop.billion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lis.shop.billion.service.AuthService;
import lis.shop.billion.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Реєстрація: паролі не збігаються")
    void registerUser_PasswordMismatch() throws Exception{

    }

    @Test
    void loginUser() {
    }

    @Test
    void getNewAccessToken() {
    }

    @Test
    void getNewRefreshToken() {
    }
}