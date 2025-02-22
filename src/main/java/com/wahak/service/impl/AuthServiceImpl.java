package com.wahak.service.impl;

import com.wahak.dto.ResetPasswordRequest;
import com.wahak.entity.User;
import com.wahak.entity.UserTokenMapper;
import com.wahak.repository.UserRepository;
import com.wahak.repository.UserTokenMapperRepository;
import com.wahak.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * @author krishna.meena
 */

@Service
public class AuthServiceImpl implements AuthService {

    private final UserTokenMapperRepository userTokenMapperRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Logger log=Logger.getLogger(AuthServiceImpl.class.getName());

    public AuthServiceImpl(UserTokenMapperRepository userTokenMapperRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userTokenMapperRepository = userTokenMapperRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        log.info("Password updated successfully for user "+user.getEmail());
        return "Success";

    }
}
