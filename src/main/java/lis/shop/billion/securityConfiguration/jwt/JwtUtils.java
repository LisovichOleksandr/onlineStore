package lis.shop.billion.securityConfiguration.jwt;


import io.jsonwebtoken.Claims;
import lis.shop.billion.entity.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Утилітний клас для створення об'єкта {@link JwtAuthentication} на основі JWT Claims.
 *
 * Призначення:
 * - Використовується переважно в {@link JwtFilter}, коли зчитуються дані з токена й необхідно
 *   створити екземпляр класу, що реалізує {@link org.springframework.security.core.Authentication}.
 *
 * Особливості:
 * - Клас фінальний і має приватний конструктор (@NoArgsConstructor(access = PRIVATE)),
 *   що забороняє створення екземплярів — він лише для статичних методів.
 *
 * Основна логіка:
 * 1. {@link #generate(Claims)}
 * 2. {@link #getRoles(Claims)}
 *
 * ROLE_IDS - Статична мапа
 *
 * Результат:
 * Повертається об'єкт JwtAuthentication, який можна встановити в SecurityContextHolder
 * для подальшої авторизації користувача.
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

    /** ROLE_IDS:
     * - Статична мапа, яка жорстко задає відповідність між назвами ролей та їх ID.
     * - Це дозволяє відновити повноцінний об'єкт Role без доступу до БД.
     */
    private static final Map<String, Long> ROLE_IDS = Map.of(
            "ROLE_CUSTOMER", 1L,
            "ROLE_ADMIN", 2L
    );

    /** 1. {@link #generate(Claims)}:
     *    - Приймає claims із JWT.
     *    - Створює та повертає {@link JwtAuthentication}, заповнюючи:
     *      - username (з subject токена),
     *      - roles (через окремий метод {@link #getRoles(Claims)}).
     */
    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }

    /** 2. {@link #getRoles(Claims)}:
     *    - Витягує список назв ролей з claims.
     *    - Створює об'єкти {@link Role}, заповнюючи:
     *        - name — з claims,
     *        - id — із статичної мапи {@code ROLE_IDS}, щоб уникнути запитів до бази даних.
     */
    private static Set<Role> getRoles(Claims claims) {
        List<String> rolesRaw = claims.get("roles", List.class);
        return rolesRaw.stream()
                .map(roleName -> {
                    Role role = new Role();
                    role.setId(ROLE_IDS.getOrDefault(roleName, 0L));
                    role.setName(roleName);
                    return role;
                })
                .collect(Collectors.toSet());
    }

}