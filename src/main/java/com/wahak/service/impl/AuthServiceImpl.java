package com.wahak.service.impl;

import com.wahak.dto.ResetPasswordRequest;
import com.wahak.entity.User;
import com.wahak.entity.UserTokenMapper;
import com.wahak.enums.OtpType;
import com.wahak.repository.UserRepository;
import com.wahak.repository.UserTokenMapperRepository;
import com.wahak.service.AuthService;
import com.wahak.service.OtpService;
import com.wahak.utils.jwt.JwtUtil;
import com.wahak.utils.UserUtils;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author krishna.meena
 */

@Service
public class AuthServiceImpl implements AuthService {

    private final UserTokenMapperRepository userTokenMapperRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final OtpService otpService;

    private Logger logger= LoggerFactory.getLogger(AuthServiceImpl.class);

    private static final int OTP_EXPIRY_TIME=10;

    public AuthServiceImpl(UserTokenMapperRepository userTokenMapperRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, OtpService otpService) {
        this.userTokenMapperRepository = userTokenMapperRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.otpService = otpService;
    }

    @Override
    public boolean isValidToken(String token) {
        UserTokenMapper mapper=userTokenMapperRepository.findByToken(token);
        return mapper != null && mapper.getExpireAt().isAfter(LocalDateTime.now(Clock.systemUTC())) && !mapper.isExpired();
    }

    @Override
    public Object createPassword(String token, ResetPasswordRequest request) {

        if(!isValidToken(token)) {
            throw new RuntimeException("Invalid token");
        }
        UserTokenMapper mapper=userTokenMapperRepository.findByToken(token);
        Optional<User> userOptional=userRepository.findById(mapper.getUserId());
        User user= userOptional.orElse(null);
        if(user == null ) {
            throw new RuntimeException("User not found");
        }
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        logger.info("Password updated successfully for user "+user.getEmail());
        return "Success";

    }

    @Override
    public Map<String,Object> login(String mobile) {

        Map<String,Object> response=new HashMap<>();
        response.put("status","");
        response.put("message","");
        Optional<User> userOptional=userRepository.findByMobile(mobile);

        if(userOptional.isPresent()){
            User user= userOptional.get();

            if(UserUtils.validateUserForLogin(user,response)) {
                logger.info("User Validate successfully {}", user.getMobile());
                postValidationLogin(user);
                String token= JwtUtil.generateToken(mobile,user.getId()+"",
                        Date.from(LocalDateTime.now(Clock.systemUTC()).plusMinutes(OTP_EXPIRY_TIME).atZone(ZoneId.systemDefault()).toInstant()));
                response.put("token",token);
                response.put("status",true);
                response.put("message","otp sent successfully");
            }
        }else {
            response.put("status",false);
            response.put("message","User not found");
        }
        return response;
    }

    @Override
    public Map<String, Object> verifyOtp(String otpToken, String otp) {
        Map<String,Object> response=new HashMap<>();
        response.put("status","");
        response.put("message","");
        Claims claims=JwtUtil.parseToken(otpToken);
        if(isValidClaims(claims)) {

            String mobile=claims.getSubject();
            Optional<User> userOptional=userRepository.findByMobile(mobile);
            if(userOptional.isPresent()) {
                User user=userOptional.get();
                if(otpService.verifyOtp(user.getMobile(),user.getId(),otp,OtpType.USER_LOGIN)) {
                    response.put("status",true);
                    response.put("message","Login successfully");
                    return response;
                }
                response.put("status",false);
                response.put("message","Invalid OTP");
                return response;
            }
            response.put("status",false);
            response.put("message","User not found");
            return response;
        }
        return response;
    }

    private boolean isValidClaims(Claims claims) {
        if(claims==null) {
            return false;
        }
        if(claims.getExpiration().after(Date.from(LocalDateTime.now(Clock.systemUTC()).atZone(ZoneId.systemDefault()).toInstant())) ) {
            return false;
        }
        return true;
    }

    private void postValidationLogin(User user) {
        otpService.sendOtp(user.getMobile(),user.getId(), OtpType.USER_LOGIN);
//        send otp on message
    }
}
