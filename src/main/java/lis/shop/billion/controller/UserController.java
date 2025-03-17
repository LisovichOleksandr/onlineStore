package lis.shop.billion.controller;
import lis.shop.billion.controller.dto.UserDto;
import lis.shop.billion.entity.User;
import lis.shop.billion.securityConfiguration.jwt.JwtAuthentication;
import lis.shop.billion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

/**
 * Контролер для роботи з користувачами.
 * Надає можливість отримати інформацію про поточного користувача або користувача за ім'ям.
 */

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    // Сервіс для логіки, пов’язаної з користувачами
    private final UserService userService;

    /**
     * Отримує поточного автентифікованого користувача за email, витягнутим з JWT-токена.
     * Повертає DTO з інформацією про користувача.
     *
     * @return ResponseEntity з UserDto або статус UNAUTHORIZED, якщо користувач не автентифікований
     */
    @GetMapping
    private ResponseEntity<UserDto> getUser(){
        // Отримуємо JWT-аутентифікацію з контексту безпеки
        JwtAuthentication auth = (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            // Якщо користувач не автентифікований — повертаємо статус 401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        // Отримуємо email з JWT і шукаємо користувача в базі
        String email = (String) auth.getPrincipal();
        UserDto userDto =  userService.getUserDtoByEmail(email);
        return ResponseEntity.ok().body(userDto);
    }
}
