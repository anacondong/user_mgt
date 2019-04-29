package com.ecom.user_mgt.excpetion;

public class UserNotAvailable extends RuntimeException {

    public UserNotAvailable(String message) {
        super(message);
    }

}
