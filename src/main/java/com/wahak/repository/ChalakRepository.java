package com.wahak.repository;

import com.wahak.entity.Chalak;
import com.wahak.entity.OtpDetails;
import com.wahak.enums.OtpType;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @author krishna.meena
 */
public interface ChalakRepository extends JpaRepository<Chalak,Integer> {


    @Query("SELECT o FROM OtpDetails o WHERE o.isUsed = false AND o.attempts < 10 ORDER BY o.createdAt DESC LIMIT 1")
    Optional<OtpDetails> findLatestUnusedOtp(int attempts, int userId, OtpType otpType);

    @Query("SELECT c FROM Chalak c " +
            "inner join RiderStoreAvailability rsa on rsa.riderId=c.id " +
            "inner join RiderWallet rw on rw.riderId=c.id " +
            "WHERE c.isVerified = true AND c.isActive = true AND c.isBlocked=false " +
            "AND rsa.storeId=?1 AND rw.amount < rw.maxAmount" +
            " ORDER BY rw.amount DESC")
    List<Chalak> findAvailableRiders(Integer id);

    @Query("SELECT c FROM Chalak c where c.id=?1 AND c.isVerified = true AND c.isActive = true AND c.isBlocked=false ")
    Optional<Chalak> findByActiveId(Integer id);

    @Query("SELECT c FROM Chalak c WHERE c.mobile=?1 AND c.isVerified = true AND c.isActive = true AND c.isBlocked=false ")
    Optional<Chalak> findByMobileActive(@NotBlank(message = "mobile is required") String mobile);
}
