package lis.shop.billion.securityConfiguration.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.List;

/**
 * Фільтр JWT, який перехоплює всі HTTP-запити та виконує перевірку токена доступу.
 * Наслідує {@link GenericFilterBean}, тому виконується один раз для кожного запиту.
 *
 * Основне призначення — вилучити JWT-токен з заголовка Authorization, перевірити його
 * валідність і, якщо токен дійсний, створити об'єкт автентифікації
 * {@link JwtAuthentication} та передати його в {@link SecurityContextHolder}.
 *
 * Поля:
 * - {@code jwtProvider} — сервіс, який відповідає за перевірку токена та витяг claims з нього.
 *
 * Метод {@code doFilter()}:
 * - Отримує токен з заголовка `Authorization`, якщо він є у форматі `Bearer ...`.
 * - Перевіряє валідність токена через {@code jwtProvider.validateAccessToken()}.
 * - Якщо токен валідний — витягує з нього claims, створює об'єкт автентифікації
 *   через {@code JwtUtils.generate(claims)}, позначає його як автентифікованого
 *   та зберігає в {@code SecurityContextHolder}.
 *
 * Метод {@code getTokenFromRequest()}:
 * - Витягує JWT з заголовка `Authorization`, якщо він починається з `Bearer `.
 *
 * Завдяки цьому фільтру Spring Security зможе розпізнавати користувача на основі JWT,
 * без необхідності використання сесій або стандартної форми логіну.
 */


@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends GenericFilterBean {

    private static final String AUTHORIZATION = "Authorization";

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain fc)
            throws IOException, ServletException {
        // Беремо такен котрий приходить в запиті
        final String token = getTokenFromRequest((HttpServletRequest) request);

        // провіряем валіднсть токена
        if (token != null && jwtProvider.validateAccessToken(token)) {
//            token + secret-key = claims, инфо про користувача і його правах
            final Claims claims = jwtProvider.getAccessClaims(token);
            List<String> roles =  claims.get("roles", List.class);
            // об'єкт типу Authentication
            final JwtAuthentication jwtInfoToken = JwtUtils.generate(claims);
            jwtInfoToken.setAuthenticated(true);
            // поміщаем в SecurityContextHolder
            SecurityContextHolder.getContext().setAuthentication(jwtInfoToken);
        }
        fc.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String bearer = request.getHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }

}