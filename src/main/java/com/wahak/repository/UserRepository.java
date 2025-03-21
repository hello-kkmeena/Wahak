package com.wahak.repository;

import com.wahak.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author krishna.meena
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByMobile(String mobile);
}
