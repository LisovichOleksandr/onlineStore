package lis.shop.billion.repository;

import lis.shop.billion.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserDetailsCustomRepository extends JpaRepository<UserDetails, Long> {

    @Query(value = "SELECT ud FROM UserDetails ud WHERE ud.user.id = :userId")
    Optional<UserDetails> findByUserId(@Param("userId") Long userId);

    @Modifying
    @Query(value = "UPDATE UserDetails u SET u.photoUrl = :namePhoto WHERE u.user.email = :email")
    void savePhotoNameByEmail(@Param("email") String email, @Param("namePhoto")String namePhoto);

    @Query(value = "SELECT ud.photoUrl FROM UserDetails ud WHERE ud.user.email= :email")
    String findPhotoNameByEmail(@Param("email") String email);
}
