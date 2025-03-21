package com.wahak.entity;

import com.wahak.enums.OtpType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

/**
 * @author krishna.meena
 */
@Entity
@Getter
@Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"otp", "userId","otpType"})
})
public class OtpDetails extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer otp;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private OtpType otpType;

    private String mobile;

    @Column(nullable = false)
    private boolean isUsed;

    @Column(updatable = false, nullable = false)
    private LocalDateTime expiredAt;

    private Integer attempts;

    private Integer maxAttempts;

}
