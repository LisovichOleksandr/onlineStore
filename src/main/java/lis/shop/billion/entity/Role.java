package lis.shop.billion.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Objects;
import java.util.Set;

/**
 * Сутність Role представляє роль користувача в системі безпеки.
 *
 * Реалізує GrantedAuthority для сумісності з Spring Security.
 * Наприклад: ROLE_USER, ROLE_ADMIN.
 */

@Entity
@Table(name = "roles")
@Data
public class Role implements GrantedAuthority {

    /**
     * Унікальний ідентифікатор ролі.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Назва ролі. Має бути унікальною.
     * Приклад: "ROLE_ADMIN", "ROLE_USER".
     */
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    /**
     * Користувачі, яким призначена ця роль.
     * Зв’язок OneToMany з проміжною таблицею UserRoles.
     *
     * CascadeType.REMOVE — при видаленні ролі, видаляються пов'язані записи у UserRoles.
     * orphanRemoval = true — видаляє "осиротілі" записи з БД.
     */
    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<UserRoles> users;

    /**
     * Реалізація методу інтерфейсу GrantedAuthority.
     * Повертає назву ролі, яка використовується в Spring Security.
     */
    @Override
    public String getAuthority() {
        System.out.println("ROLE getAuthority >> " + name);
        return this.name;
    }

    /**
     * Перевизначення equals для порівняння ролей за id.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    /**
     * Перевизначення hashCode для використання в колекціях.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
