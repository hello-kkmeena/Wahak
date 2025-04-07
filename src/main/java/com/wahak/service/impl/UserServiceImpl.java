package com.wahak.service.impl;

import com.wahak.dto.AddressDto;
import com.wahak.dto.UserDTO;
import com.wahak.entity.Address;
import com.wahak.entity.User;
import com.wahak.entity.UserTokenMapper;
import com.wahak.repository.AddressRepository;
import com.wahak.repository.UserRepository;
import com.wahak.repository.UserTokenMapperRepository;
import com.wahak.service.EmailService;
import com.wahak.service.UserService;
import com.wahak.utils.AuthenticatedUserUtil;
import com.wahak.utils.EntityConverterUtils;
import io.micrometer.common.util.StringUtils;
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
    private final AddressRepository addressRepository;

    @Value("${passowrd.base.url}")
    private String baseUrl;

    @Value("${password.set.path}")
    private String path;



    public UserServiceImpl(UserRepository userRepository, EmailService emailService, UserTokenMapperRepository userTokenMapperRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.userTokenMapperRepository = userTokenMapperRepository;
        this.addressRepository = addressRepository;
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

    @Override
    public AddressDto addAddress(AddressDto addressDto) {

        String chalakId= AuthenticatedUserUtil.getAuthenticatedUserId();
        Integer userId= StringUtils.isBlank(chalakId) ? 1 : Integer.parseInt(chalakId);

        Address address=new Address();
        address.setUserId(userId);
        address.setAddress(addressDto.getAddress());
        address.setCity(addressDto.getCity());
        address.setLandmark(addressDto.getLandmark());
        address.setPincode(addressDto.getPincode());
        address.setLang(addressDto.getLang());
        address.setLat(addressDto.getLat());
        addressRepository.save(address);

        addressDto.setId(address.getId());
        return addressDto;
    }
}
