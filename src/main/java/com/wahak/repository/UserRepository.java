package com.wahak.repository;

import com.wahak.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author krishna.meena
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByMobile(String mobile);

    @Query("SELECT u FROM User u WHERE u.id=?1 " +
            "AND u.isActive = true " +
            "AND u.isBlocked=false " +
            " AND u.isEnabled=true")
    Optional<User> findByActiveId(Integer id);
}
