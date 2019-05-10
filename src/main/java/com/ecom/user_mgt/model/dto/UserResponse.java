package com.ecom.user_mgt.model.dto;

import com.ecom.user_mgt.model.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserResponse extends Users {

    private List<Orders> orders;

    public UserResponse(Users users, List<Orders> orders) {
        super(users.getId(), users.getFirstName(), users.getLastName(), users.getCreatedAt());
        this.orders = orders;
    }
}
