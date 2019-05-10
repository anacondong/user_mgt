package com.ecom.user_mgt.controller;

import com.ecom.user_mgt.model.dto.UserDetailsRequest;
import com.ecom.user_mgt.model.dto.UserRequest;
import com.ecom.user_mgt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity users(@RequestBody List<UserRequest> userRequest) {
        return ResponseEntity.ok(userService.saveUser(userRequest));
    }

    @PostMapping("/userDetails")
    public ResponseEntity userDetails(@RequestBody UserDetailsRequest userDetailsRequest) {
        return ResponseEntity.ok(userService.getAllUsers(userDetailsRequest));
    }

    @GetMapping("/user/{id}")
    public ResponseEntity userById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

}
