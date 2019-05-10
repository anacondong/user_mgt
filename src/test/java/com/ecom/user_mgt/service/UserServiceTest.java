package com.ecom.user_mgt.service;

import com.ecom.user_mgt.excpetion.UserNotAvailable;
import com.ecom.user_mgt.gateways.OrderClient;
import com.ecom.user_mgt.model.dto.Orders;
import com.ecom.user_mgt.model.dto.UserDetailsRequest;
import com.ecom.user_mgt.model.dto.UserRequest;
import com.ecom.user_mgt.model.dto.UserResponse;
import com.ecom.user_mgt.model.entity.Users;
import com.ecom.user_mgt.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Spy
    private UserRepository userRepository;

    @Spy
    private OrderClient orderClient;

    @Spy
    private ChannelGateway channelGateway;

    private Long userId = 1L;

    private Long userIdTwo = 1L;


    @Test
    public void saveUser() {
        UserRequest userRequest = new UserRequest("Avi", "U");
        Users user = new Users(userId, "Avi", "U", LocalDateTime.now());
        doReturn(Arrays.asList(user)).when(userRepository).saveAll(any(List.class));
        doNothing().when(channelGateway).publishToService(anyMap());
        List<Users> usersList = userService.saveUser(Collections.singletonList(userRequest));
        assertEquals(1, usersList.size());
        assertEquals("Avi", usersList.get(0).getFirstName());
    }

    @Test
    public void getUserById() {
        Users user = new Users(userId, "Avi", "U", LocalDateTime.now());
        Orders orders = new Orders(1L, 1L, 1L, "Ordering Apple", LocalDateTime.now());
        doReturn(Optional.of(user)).when(userRepository).findById(userId);
        doReturn(Arrays.asList(orders)).when(orderClient).getOrdersByUserId(userId);
        UserResponse userById = userService.getUserById(userId);
        assertEquals(userId, userById.getId());
        assertEquals("Avi", userById.getFirstName());
        assertEquals("U", userById.getLastName());
        assertEquals(1, userById.getOrders().size());
        assertEquals("Ordering Apple", userById.getOrders().get(0).getOrderDesc());

    }

    @Test
    public void shouldReturnNotFoundExceptionUserById() {
        doReturn(Optional.empty()).when(userRepository).findById(userId);
        assertThrows(UserNotAvailable.class, () -> userService.getUserById(userId));
    }

    @Test
    public void getAllUsers() {
        Users user = new Users(userId, "Avi", "U", LocalDateTime.now());
        Users user2 = new Users(userIdTwo, "Dad", "G", LocalDateTime.now());

        doReturn(Arrays.asList(user, user2)).when(userRepository).findAll();

        UserDetailsRequest fullSyncObject = new UserDetailsRequest(true, null);
        List<Users> allUsers = userService.getAllUsers(fullSyncObject);
        assertEquals(2, allUsers.size());
        assertEquals(user.getId(), allUsers.get(0).getId());
        assertEquals(user.getFirstName(), allUsers.get(0).getFirstName());

        List<Long> userIds = Collections.singletonList(userId);
        UserDetailsRequest specificUserRequest = new UserDetailsRequest(false, userIds);

        doReturn(Arrays.asList(user)).when(userRepository).findAllById(userIds);
        List<Users> specificUsers = userService.getAllUsers(specificUserRequest);

        assertEquals(1, specificUsers.size());
        assertEquals(user.getId(), specificUsers.get(0).getId());

    }
}
