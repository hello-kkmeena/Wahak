package com.wahak.utils;

import com.wahak.dto.UserDTO;
import com.wahak.entity.User;
import org.springframework.stereotype.Component;

/**
 * @author krishna.meena
 */

@Component
public class EntityConverterUtils {


    public static User convertUserDtoToEntity(UserDTO userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setMobile(userDto.getMobile());
        return user;
    }
}
