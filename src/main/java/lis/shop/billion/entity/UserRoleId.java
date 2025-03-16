package lis.shop.billion.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Клас-композитний ключ для таблиці user_roles.
 *
 * Включає в себе ID користувача і ID ролі.
 * Повинен реалізовувати Serializable та мати:
 * - порожній конструктор (обов'язково)
 * - equals() та hashCode() (генеруються через @Data)
 */

@Embeddable
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Data
public class UserRoleId implements Serializable{

    /**
     * Ідентифікатор користувача.
     */
    private Long userId;

    /**
     * Ідентифікатор ролі.
     */
    private Long roleId;

}
