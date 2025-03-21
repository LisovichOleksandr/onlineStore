package lis.shop.billion.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;
import lis.shop.billion.controller.registerDto.RegisterResponse;
import lis.shop.billion.controller.registerDto.RegisterUser;
import lis.shop.billion.controller.jwtDto.JwtRequest;
import lis.shop.billion.controller.jwtDto.JwtResponse;
import lis.shop.billion.controller.jwtDto.RefreshJwtRequest;
import lis.shop.billion.entity.User;
import lis.shop.billion.service.AuthService;
import lis.shop.billion.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Контролер для обробки запитів, пов'язаних з аутентифікацією та авторизацією користувачів.
 *
 * Включає функціонал реєстрації, входу в систему, а також оновлення JWT токенів.
 * Повертає відповіді у форматі JSON.
 */

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    /**
     * Реєстрація нового користувача.
     *
     * Перед збереженням перевіряється, чи збігаються паролі.
     * У разі успіху повертається інформація про користувача (без токенів).
     *
     * @param registerUser DTO з даними для реєстрації
     * @return 200 OK — у разі успіху, 400 BAD_REQUEST — якщо паролі не збігаються або інша помилка
     */
    @Operation(summary = "Реєстрація нового користувача")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Користувача успішно зареєстровано"),
            @ApiResponse(responseCode = "400", description = "Паролі не співпадають або невірні дані")
    })
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterUser registerUser) {

        if (!registerUser.isSimilarPassword()) {
            // Перевірка на збігання паролів
            return ResponseEntity.badRequest().body(Map.of("password", "Паролі не збігаються"));
        }

        User user = userService.saveUser(registerUser);
        RegisterResponse registerResponse = new RegisterResponse(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(registerResponse);
    }

    /**
     * Вхід користувача в систему.
     *
     * Перевіряє логін і пароль. У разі успіху повертає JWT токен.
     *
     * @param jwtRequest DTO з логіном і паролем
     * @return 200 OK — з токеном, 401 UNAUTHORIZED — якщо аутентифікація не пройшла
     */
    @Operation(summary = "Авторизація користувача та отримання JWT токена")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Авторизація успішна"),
            @ApiResponse(responseCode = "401", description = "Невірний логін або пароль")
    })
    @PostMapping("login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody JwtRequest jwtRequest) {
        final JwtResponse token;
        try {
            token = authService.login(jwtRequest);
        } catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
        return ResponseEntity.ok(token);
    }

    /**
     * Отримання нового access токена по refresh токену.
     *
     * Корисно, коли access токен протермінувався.
     *
     * @param request DTO з refresh токеном
     * @return 200 OK — з новим access токеном
     * @throws AuthException якщо refresh токен недійсний
     */
    @Operation(summary = "Отримати новий access токен по refresh токену")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новий access токен успішно згенеровано"),
            @ApiResponse(responseCode = "401", description = "Недійсний або протермінований refresh токен")
    })
    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = authService.getAccessToken(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    /**
     * Отримання нового refresh токена.
     *
     * Використовується для продовження сесії користувача.
     *
     * @param request DTO з refresh токеном
     * @return 200 OK — з новим refresh та access токеном
     * @throws AuthException якщо refresh токен недійсний
     */
    @Operation(summary = "Отримати новий refresh токен")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Новий refresh та access токени успішно згенеровані"),
            @ApiResponse(responseCode = "401", description = "Недійсний або протермінований refresh токен")
    })
    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> getNewRefreshToken(@RequestBody RefreshJwtRequest request) throws AuthException {
        final JwtResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }
}
