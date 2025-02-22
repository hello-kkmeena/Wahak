package com.wahak.service.impl;

import com.wahak.dto.UserDTO;
import com.wahak.entity.User;
import com.wahak.entity.UserTokenMapper;
import com.wahak.repository.UserRepository;
import com.wahak.repository.UserTokenMapperRepository;
import com.wahak.service.EmailService;
import com.wahak.service.UserService;
import com.wahak.utils.EntityConverterUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author krishna.meena
 */

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final EmailService emailService;

   private final UserTokenMapperRepository userTokenMapperRepository;

    @Value("${passowrd.base.url}")
    private String baseUrl;

    @Value("${password.set.path}")
    private String path;



    public UserServiceImpl(UserRepository userRepository, EmailService emailService, UserTokenMapperRepository userTokenMapperRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.userTokenMapperRepository = userTokenMapperRepository;
    }

    @Override
    public UserDTO createUser(final UserDTO userDto) {
        User user = EntityConverterUtils.convertUserDtoToEntity(userDto);
        user.setActive(false);
        user.setEnabled(false);
        userRepository.save(user);
        userDto.setId(user.getId());
        postUserCreation(user);
        return userDto;
    }

    private void postUserCreation(User user) {

        String url= getResetPasswordUrl(user);

        String content="Please click on below link to set your password \n"+url;

        emailService.sendEmail(user.getEmail(), "Register Successfully | click to set Password", content);
    }

    private String getResetPasswordUrl(User user) {
        String token = UUID.randomUUID().toString();

        UserTokenMapper tokenMapper=new UserTokenMapper();
        tokenMapper.setToken(token);
        tokenMapper.setUserId(user.getId());
        tokenMapper.setType("RESET PASSWORD");
        tokenMapper.setExpired(false);
        tokenMapper.setExpireAt(LocalDateTime.now().plusHours(1));
        userTokenMapperRepository.save(tokenMapper);
        return baseUrl + path + "?token=" + token;
    }

    @Override
    public UserDTO updateUser(UserDTO user) {

        return null;
    }
}
