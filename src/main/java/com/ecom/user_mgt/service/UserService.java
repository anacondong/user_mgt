package com.ecom.user_mgt.service;

import com.ecom.user_mgt.excpetion.UserNotAvailable;
import com.ecom.user_mgt.model.dao.Users;
import com.ecom.user_mgt.model.dto.UserRequest;
import com.ecom.user_mgt.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Value("${app.name}")
    private String appName;

    public List<Users> saveUser(List<UserRequest> userRequest) {
        log.info(String.format("[%s] - Saving Users", appName ));
        return userRepository.saveAll(userRequest.stream()
                .map(Users::new)
                .collect(Collectors.toList()));
    }

    public Users getUserById(Long id) {
        log.info(String.format("[%s] Getting User By Id", appName ));
        return userRepository.findById(id).orElseThrow(() -> new UserNotAvailable("Requested user is not available"));
    }

    public List<Users> getAllUsers() {
        log.info(String.format("[%s] Getting all Users", appName));
        return userRepository.findAll();
    }
}
