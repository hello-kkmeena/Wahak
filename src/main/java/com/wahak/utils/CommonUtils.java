package com.wahak.utils;

/**
 * @author krishna.meena
 */
public class CommonUtils {

    public static final String MOBILE_REGEX = "(0/91)?[7-9][0-9]{9}";

    public static String generateOrderAssignmentNumber() {
        return "ORD_ASSIGN" + System.currentTimeMillis();
    }
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }


    public static boolean isValidMobileNumber(String mobile) {
        if (mobile.matches(MOBILE_REGEX)) {
            return true;
        }
        return false;
    }
}
