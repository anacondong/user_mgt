package com.ecom.user_mgt.controller;

import com.ecom.user_mgt.model.dto.Orders;
import com.ecom.user_mgt.model.dto.UserDetailsRequest;
import com.ecom.user_mgt.model.dto.UserRequest;
import com.ecom.user_mgt.model.dto.UserResponse;
import com.ecom.user_mgt.model.entity.Users;
import com.ecom.user_mgt.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Spy
    private UserService userService;

    private MockMvc mockMvc;

    private Long userId;

    private Users users;

    private Orders order;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LocalDateTime localDateTime = LocalDateTime.now();
        this.userId = 1L;
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        this.users = new Users(userId, "Avi", "U", localDateTime);
        this.order = new Orders(1L, userId, 1L, "Order 1", localDateTime);
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void users() throws Exception {
        List<UserRequest> userRequests = Arrays.asList(new UserRequest("Avi", "U"));
        List<Users> usersList = Collections.singletonList(users);
        doReturn(usersList).when(userService).saveUser(anyList());
        MvcResult mvcResult = this.mockMvc.perform(
                post("/api/users")
                        .content(objectMapper.writeValueAsString(userRequests))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(objectMapper.writeValueAsString(usersList), mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void userDetails() throws Exception {
        UserDetailsRequest userDetailsRequest = new UserDetailsRequest(false, Collections.singletonList(userId));
        List<Users> usersList = Collections.singletonList(users);
        doReturn(usersList).when(userService).getAllUsers(any());
        MvcResult mvcResult = this.mockMvc.perform(
                post("/api/userDetails")
                        .content(objectMapper.writeValueAsString(userDetailsRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        assertEquals(objectMapper.writeValueAsString(usersList), mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void userById() throws Exception {
        UserResponse toBeReturned = new UserResponse(users, Collections.singletonList(order));
        doReturn(toBeReturned).when(userService).getUserById(userId);
        MvcResult mvcResult = this.mockMvc.perform(get("/api/user/" + userId))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andReturn();
        assertEquals(objectMapper.writeValueAsString(toBeReturned), mvcResult.getResponse().getContentAsString());
    }
}
