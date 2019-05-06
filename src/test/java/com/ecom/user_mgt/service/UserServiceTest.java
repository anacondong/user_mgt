package com.ecom.user_mgt.service;

import com.ecom.user_mgt.excpetion.UserNotAvailable;
import com.ecom.user_mgt.gateways.OrderClient;
import com.ecom.user_mgt.model.entity.Users;
import com.ecom.user_mgt.model.dto.Orders;
import com.ecom.user_mgt.model.dto.UserResponse;
import com.ecom.user_mgt.repo.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Spy
    private UserRepository userRepository;

    @Spy
    private OrderClient orderClient;

    private Long userId = 1L;

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
        doReturn(Arrays.asList(user)).when(userRepository).findAll();
        List<Users> allUsers = userService.getAllUsers();
        assertEquals(1, allUsers.size());
        assertEquals(user.getId(), allUsers.get(0).getId());
        assertEquals(user.getFirstName(), allUsers.get(0).getFirstName());
    }
}
