package lis.shop.billion.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

/**
 * Сутність для зв’язку "багато до багатьох" між User і Role.
 * Використовує вбудований композитний ключ через клас UserRoleId.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_roles")
public class UserRoles {

    /**
     * Композитний ключ (userId + roleId), вбудований як первинний ключ.
     */
    @EmbeddedId
    private UserRoleId id;

    /**
     * Посилання на користувача.
     * @MapsId("userId") вказує, що поле userId з UserRoleId буде автоматично пов'язано з цим полем.
     * @JsonIgnore — уникнення циклічної серіалізації при передачі JSON.
     */
    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private User user;

    /**
     * Посилання на роль.
     * @MapsId("roleId") вказує, що поле roleId з UserRoleId буде автоматично пов'язано з цим полем.
     * @JsonIgnore — уникнення циклічної серіалізації при передачі JSON.
     */
    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id", nullable = false)
    @JsonIgnore
    private  Role role;

    /**
     * Перевизначення equals() для порівняння по композитному ключу id.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoles userRole = (UserRoles) o;
        return Objects.equals(id, userRole.id);
    }

    /**
     * Генерація hashCode() на основі id.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
