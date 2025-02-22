package com.wahak.repository;

import com.wahak.entity.OtpDetails;
import com.wahak.enums.OtpType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author krishna.meena
 */
public interface OtpDetailsRepository extends JpaRepository<OtpDetails,Integer> {

    @Query("SELECT o FROM OtpDetails o WHERE o.isUsed = false AND o.userId = ?1 AND o.otpType = ?2 ORDER BY o.updatedAt DESC LIMIT 1")
    Optional<OtpDetails> findLatestUnusedOtp(Integer userId, OtpType otpType);
}
