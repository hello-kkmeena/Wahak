package com.wahak.repository;

import com.wahak.entity.UserTokenMapper;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author krishna.meena
 */
public interface UserTokenMapperRepository extends JpaRepository<UserTokenMapper, Integer> {
    UserTokenMapper findByToken(String token);
}
