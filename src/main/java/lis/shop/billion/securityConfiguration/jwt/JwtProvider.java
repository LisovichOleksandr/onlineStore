package lis.shop.billion.securityConfiguration.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lis.shop.billion.entity.User;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Сервіс для створення, валідації та розбору JWT-токенів (як Access, так і Refresh).
 *
 * Компонент відповідає за повний життєвий цикл JWT: від генерації до перевірки та
 * вилучення claims (корисних даних з токена).
 *
 * Поля:
 * - {@code jwtAccessSecret} — секретний ключ для підпису та перевірки Access токена.
 * - {@code jwtRefreshSecret} — секретний ключ для Refresh токена.
 *
 * Ключі ініціалізуються в конструкторі на основі значень з application.properties
 * (base64-encoded строки).
 *
 * Методи:
 * Генерація токенів:
 * Валідація токенів:
 * Отримання claims (payload):
 *
 * Claims — це дані в тілі токена, які містять інформацію про користувача, наприклад:
 * - roles — список ролей
 * - userName — ім’я користувача
 * - sub (subject) — email
 * - exp — дата закінчення дії токена
 *
 * Цей клас є центральною точкою в логіці обробки JWT в додатку.
 */


@Slf4j
@Component
public class JwtProvider {

    private final SecretKey jwtAccessSecret;
    private final SecretKey jwtRefreshSecret;

    public JwtProvider(
            @Value("${jwt.secret.access}") String jwtAccessSecret,
            @Value("${jwt.secret.refresh}") String jwtRefreshSecret
    ) {
        this.jwtAccessSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtAccessSecret));
        this.jwtRefreshSecret = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtRefreshSecret));
    }

    /**
     * Генерація токенів:
     * - {@link #generateAccessToken(User)} — створює короткоживучий Access токен (5 хвилин),
     * додаючи в claims email (як subject), ролі та ім’я користувача.
     * */
    public String generateAccessToken(@NonNull User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant accessExpirationInstant = now.plusMinutes(5).atZone(ZoneId.systemDefault()).toInstant();
        final Date accessExpiration = Date.from(accessExpirationInstant);

        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(accessExpiration)
                .signWith(jwtAccessSecret)
                .claim("roles", user.getRoles())
                .claim("userName", user.getUsername())
                .compact();
    }

    /**
     * - {@link #generateRefreshToken(User)} — створює Refresh токен на 30 днів без додаткових claims.
     * */
    public String generateRefreshToken(@NonNull User user) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant refreshExpirationInstant = now.plusDays(30).atZone(ZoneId.systemDefault()).toInstant();
        final Date refreshExpiration = Date.from(refreshExpirationInstant);
        return Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(refreshExpiration)
                .signWith(jwtRefreshSecret)
                .compact();
    }

    /** ✅ Валідація токенів:
     * - {@link #validateAccessToken(String)} та {@link #validateRefreshToken(String)} —
     *   перевіряють підпис, структуру та термін дії токенів.
     * */
    public boolean validateAccessToken(@NonNull String accessToken) {
        return validateToken(accessToken, jwtAccessSecret);
    }

    public boolean validateRefreshToken(@NonNull String refreshToken) {
        return validateToken(refreshToken, jwtRefreshSecret);
    }

    /**
     * - {@link #validateToken(String, Key)} — спільний метод, що обробляє всі винятки та логування.
     * */
    private boolean validateToken(@NonNull String token, @NonNull Key secret) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            log.error("Token expired", expEx);
        } catch (UnsupportedJwtException unsEx) {
            log.error("Unsupported jwt", unsEx);
        } catch (MalformedJwtException mjEx) {
            log.error("Malformed jwt", mjEx);
        } catch (SignatureException sEx) {
            log.error("Invalid signature", sEx);
        } catch (Exception e) {
            log.error("invalid token", e);
        }
        return false;
    }

    /** ✅ Отримання claims (payload):
     * - {@link #getAccessClaims(String)} та {@link #getRefreshClaims(String)} —
     *   дозволяють витягти claims з відповідного типу токена.
     * */
    public Claims getAccessClaims(@NonNull String token) {
        return getClaims(token, jwtAccessSecret);
    }

    public Claims getRefreshClaims(@NonNull String token) {
        return getClaims(token, jwtRefreshSecret);
    }

    /**
     * - {@link #getClaims(String, Key)} — внутрішній метод для парсингу та повернення claims.
     * */
    private Claims getClaims(@NonNull String token, @NonNull Key secret) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

}
