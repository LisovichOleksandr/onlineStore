package lis.shop.billion.securityConfiguration.jwt;


import io.jsonwebtoken.Claims;
import lis.shop.billion.entity.Role;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

//Клас призначенний для виклику в фільтрі доступу, створює класс інтерфейса Authentication
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class JwtUtils {

//    id для Role в системе чтоби не лезть  в БД
    private static final Map<String, Long> ROLE_IDS = Map.of(
            "ROLE_CUSTOMER", 1L,
            "ROLE_ADMIN", 2L
    );

    public static JwtAuthentication generate(Claims claims) {
        final JwtAuthentication jwtInfoToken = new JwtAuthentication();
        jwtInfoToken.setRoles(getRoles(claims));
        jwtInfoToken.setUsername(claims.getSubject());
        return jwtInfoToken;
    }

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