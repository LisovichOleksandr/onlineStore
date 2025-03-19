package lis.shop.billion.service;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lis.shop.billion.securityConfiguration.jwt.JwtAuthentication;
import lis.shop.billion.securityConfiguration.jwt.JwtProvider;
import lis.shop.billion.controller.jwtDto.JwtRequest;
import lis.shop.billion.controller.jwtDto.JwtResponse;
import lis.shop.billion.entity.User;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Сервіс для обробки аутентифікації користувачів у додатку.
 * Відповідає за:
 * - логін користувача та перевірку паролю;
 * - генерацію JWT access та refresh токенів;
 * - оновлення access токена за допомогою refresh токена;
 * - повне оновлення обох токенів;
 * - отримання інформації про поточного авторизованого користувача.
 *
 * Для збереження виданих refresh токенів використовується внутрішнє in-memory сховище (Map).
 * У випадку реального застосування рекомендується зберігати refresh токени у базі даних або Redis.
 */

@Service
@RequiredArgsConstructor
public class AuthService {

    // Сервіс для роботи з користувачами
    private final UserService userService;

    // Сховище для refresh-токенів користувачів
    private final Map<String, String> refreshStorage = new HashMap<>();

    // Провайдер для створення та валідації JWT токенів
    private final JwtProvider jwtProvider;

    // Енкодер для перевірки паролів
    private final PasswordEncoder passwordEncoder;

    /**
     * Метод для авторизації користувача.
     * Приймає логін і пароль, перевіряє пароль і повертає JWT токени (access + refresh).
     *
     * @param authRequest - об'єкт із email та паролем
     * @return JwtResponse з accessToken і refreshToken
     * @throws AuthException - якщо пароль невірний
     */
    public JwtResponse login(@NonNull JwtRequest authRequest) throws AuthException {
        final User user = (User) userService.findByEmail(authRequest.email());


        if (authenticateUser(authRequest.password(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshStorage.put(user.getEmail(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new AuthException("Неправильный пароль");
        }
    }

    /**
     * Метод для перевірки пароля користувача.
     *
     * @param rawPassword - пароль з форми
     * @param storedHashedPassword - захешований пароль з бази
     * @return true, якщо паролі збігаються
     */
    public boolean authenticateUser(String rawPassword, String storedHashedPassword) {
        return passwordEncoder.matches(rawPassword, storedHashedPassword);
    }

    /**
     * Отримати новий accessToken, використовуючи refreshToken.
     *
     * @param refreshToken - дійсний refresh токен
     * @return JwtResponse з новим accessToken, якщо токен валідний
     * @throws AuthException
     */
    public JwtResponse getAccessToken(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = (User) userService.findByEmail(email);
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    /**
     * Повне оновлення токенів: генерує нові access та refresh токени.
     *
     * @param refreshToken - старий refresh токен
     * @return JwtResponse з новими токенами
     * @throws AuthException - якщо токен невалідний або не збігається зі збереженим
     */
    public JwtResponse refresh(@NonNull String refreshToken) throws AuthException {
        if (jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String email = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(email);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final User user = (User) userService.findByEmail(email);
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(user.getEmail(), newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Невалидный JWT токен");
    }

    /**
     * Отримання інформації про авторизованого користувача з SecurityContext.
     *
     * @return JwtAuthentication об'єкт, що містить дані користувача
     */
    public JwtAuthentication getAuthInfo() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }

}