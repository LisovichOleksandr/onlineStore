package lis.shop.billion.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Сутність User представляє користувача в системі.
 * Реалізує UserDetails для підтримки Spring Security.
 */

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails {

    /**
     * Унікальний ідентифікатор користувача.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Унікальне ім’я користувача.
     */
    @Column(nullable = false, unique = true, length = 50)
    private String username;

    /**
     * Унікальна email-адреса користувача, яка використовується для входу в систему.
     */
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    /**
     * Захешований пароль користувача.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Ролі, прив'язані до користувача через проміжну таблицю UserRoles.
     *
     * FetchType.EAGER — ролі завантажуються разом із користувачем.
     * Cascade.ALL — всі дії з користувачем відображаються на пов’язані ролі.
     * orphanRemoval — при видаленні зв’язку з роллю вона видаляється з БД.
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<UserRoles> userRoles = new HashSet<>();

    /**
     * Дата та час створення користувача.
     * Встановлюється автоматично.
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    /**
     * Метод для отримання ролей користувача у вигляді Set<String>.
     */
    public Set<String> getRoles(){
        return userRoles.stream()
                .map(userRole -> userRole.getRole().getName())
                .collect(Collectors.toSet());
    }

    /**
     * Повертає колекцію GrantedAuthority, яка використовується Spring Security.
     *
     * Кожна роль конвертується у SimpleGrantedAuthority.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream()
                .map(SimpleGrantedAuthority::new) // Преобразуем String в SimpleGrantedAuthority
                .collect(Collectors.toSet());
    }

    /**
     * Вказує, чи є акаунт простроченим. true — акаунт активний.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Вказує, чи є акаунт заблокованим. true — акаунт не заблоковано.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Вказує, чи не прострочені облікові дані. true — не прострочені.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Вказує, чи активовано користувача. true — користувач активний.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
