package lis.shop.billion.repository;

import lis.shop.billion.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long> {

    @Query(value = "SELECT ud FROM UserDetails ud WHERE ud.user.id = :userId")
    Optional<UserDetails> findByUserId(@Param("userId") Long userId);
}
