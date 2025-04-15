package lis.shop.billion.controller;

import lis.shop.billion.controller.dto.UserDto;
import lis.shop.billion.securityConfiguration.jwt.JwtAuthentication;
import lis.shop.billion.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private JwtAuthentication authentication;

    @Test
    void getUser_ShouldReturnUserDto_WhenAuthentication() {
        // Підготовка даних
        String email = "test@example.com";
        UserDto userDto = new UserDto(1L, null,null,null,33, email, null, null);

        // Мокаєм контекст безпеки
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(email);
        when(userService.getUserDtoByEmail(email)).thenReturn(userDto);
        SecurityContextHolder.setContext(securityContext);

        // Виконуєм метод
        ResponseEntity<UserDto> response = userController.getUser();

        // Перевірка
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(email, response.getBody().email());
    }

    @Test
    void getUser_ShouldReturnUnauthorized_WhenNotAuthenticated() {
        // Мокаем відсутність аутентифікації
        when(securityContext.getAuthentication()).thenReturn(null);
        SecurityContextHolder.setContext(securityContext);

        // Виконання метода
        ResponseEntity<UserDto> response = userController.getUser();

        // Перевірка
        assertNotNull(response);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertNull(response.getBody());
    }
}