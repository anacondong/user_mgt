package com.ecom.user_mgt.excpetion;

public class ServiceUnAvailable extends RuntimeException {

    public ServiceUnAvailable(String message) {
        super(message);
    }
}
