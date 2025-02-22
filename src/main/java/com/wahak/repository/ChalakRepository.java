package com.wahak.repository;

import com.wahak.entity.Chalak;
import com.wahak.entity.OtpDetails;
import com.wahak.enums.OtpType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @author krishna.meena
 */
public interface ChalakRepository extends JpaRepository<Chalak,Integer> {


    @Query("SELECT o FROM OtpDetails o WHERE o.isUsed = false AND o.attempts < 10 ORDER BY o.createdAt DESC LIMIT 1")
    Optional<OtpDetails> findLatestUnusedOtp(int attempts, int userId, OtpType otpType);
}
