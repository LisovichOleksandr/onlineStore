package lis.shop.billion.repository;

import lis.shop.billion.entity.UserRoleId;
import lis.shop.billion.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторій UserRolesRepository надає доступ до CRUD-операцій
 * (створення, читання, оновлення, видалення) для зв’язуючої сутності UserRoles,
 * яка представляє зв’язок "багато до багатьох" між користувачами та ролями.
 *
 * JpaRepository<UserRoles, UserRoleId> означає, що:
 * - UserRoles — це сутність, яка описує зв’язок між User та Role,
 * - UserRoleId — це складений ключ (Embeddable або @IdClass), який містить
 *   ID користувача та ID ролі.
 *
 * Цей репозиторій може використовуватись для керування призначенням ролей
 * користувачам або перевірки їхніх прав доступу.
 *
 * За потреби тут можна додати методи для пошуку всіх ролей користувача
 * або всіх користувачів, що мають певну роль.
 */

public interface UserRolesRepository extends JpaRepository<UserRoles, UserRoleId> {
}
