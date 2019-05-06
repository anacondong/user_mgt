package com.ecom.user_mgt.service;

import com.ecom.user_mgt.excpetion.UserNotAvailable;
import com.ecom.user_mgt.gateways.OrderClient;
import com.ecom.user_mgt.model.entity.Users;
import com.ecom.user_mgt.model.dto.Orders;
import com.ecom.user_mgt.model.dto.UserRequest;
import com.ecom.user_mgt.model.dto.UserResponse;
import com.ecom.user_mgt.repo.UserRepository;
import com.ecom.user_mgt.utils.AppConstants;
import com.ecom.user_mgt.utils.RestUtils;
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

    @Autowired
    private OrderClient orderClient;

    @Autowired
    private ChannelGateway channelGateway;

    @Value("${app.name}")
    private String appName;

    public List<Users> saveUser(List<UserRequest> userRequest) {
        log.info(String.format("[%s] - Saving Users", appName));
        List<Users> users = userRepository.saveAll(userRequest.stream()
                .map(Users::new)
                .collect(Collectors.toList()));
        channelGateway.publishToService(AppConstants.USER_CREATED);
        return users;
    }

    public UserResponse getUserById(Long id) {
        log.info(String.format("[%s] Getting User By Id", appName));

        Users users = userRepository.findById(id).orElseThrow(() -> new UserNotAvailable("Requested user is not available"));
        List<Orders> orders = RestUtils.safeExecuteForListResponse(() -> orderClient.getOrdersByUserId(id));

        return new UserResponse(users, orders);
    }

    public List<Users> getAllUsers() {
        log.info(String.format("[%s] Getting all Users", appName));
        return userRepository.findAll();
    }
}
