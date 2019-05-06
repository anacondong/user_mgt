package com.ecom.user_mgt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;

@SpringBootApplication
@EnableFeignClients
@EnableBinding(Source.class)
public class UserMgtApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserMgtApplication.class, args);
    }

}
