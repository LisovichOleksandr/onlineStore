package lis.shop.billion.securityConfiguration.jwt;

import lis.shop.billion.entity.Role;
import lombok.Data;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Реалізація інтерфейсу {@link Authentication} для представлення автентифікованого користувача,
 * який був ідентифікований за допомогою JWT.
 *
 * Цей клас використовується для збереження інформації про користувача після того,
 * як JWT-токен був успішно розшифрований і перевірений.
 *
 * Основні поля:
 * - {@code username} — ім’я користувача (зазвичай email).
 * - {@code roles} — ролі користувача, які використовуються для авторизації.
 * - {@code authenticated} — прапорець, що показує, чи автентифікований користувач.
 *
 * Основні методи:
 * - {@code getAuthorities()} — перетворює ролі користувача в об’єкти {@link SimpleGrantedAuthority}
 *   для використання в механізмах перевірки прав доступу.
 * - {@code getPrincipal()} — повертає ім’я користувача (username).
 * - {@code isAuthenticated()}, {@code setAuthenticated()} — визначають статус автентифікації.
 *
 * Інші методи {@code getCredentials()}, {@code getDetails()} не використовуються в цьому контексті
 * і повертають {@code null}.
 *
 * Цей клас дозволяє інтегрувати JWT із внутрішнім механізмом Spring Security без використання сесій.
 */

@Data
public class JwtAuthentication implements Authentication {

    private boolean authenticated;
    private String username;
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(Role::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());
    }

    @Override
    public Object getCredentials() { return null; }

    @Override
    public Object getDetails() { return null; }

    @Override
    public Object getPrincipal() { return username; }

    @Override
    public boolean isAuthenticated() { return authenticated; }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() { return username; }

}