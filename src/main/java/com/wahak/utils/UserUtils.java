package com.wahak.utils;

import com.wahak.entity.User;

import java.util.Map;

/**
 * @author krishna.meena
 */
public class UserUtils {
    public static boolean validateUserForLogin(User user, Map<String, Object> response) {

        if(user.isBlocked()) {
            response.put("message", "User is Blocked");
            response.put("status",false);
            return false;
        }
        if(user.isDeleted()) {
            response.put("message", "User is Deleted");
            response.put("status",false);
            return false;
        }
        response.put("message", "Done");
        response.put("status",true);
        return true;
    }
}
