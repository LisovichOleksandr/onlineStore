package lis.shop.billion.repository;

import lis.shop.billion.entity.UserRoleId;
import lis.shop.billion.entity.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolesRepository extends JpaRepository<UserRoles, UserRoleId> {
}
