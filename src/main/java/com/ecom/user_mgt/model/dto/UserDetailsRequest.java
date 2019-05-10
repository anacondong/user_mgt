package com.ecom.user_mgt.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsRequest {

    private Boolean fullSync;

    private List<Long> userIds;
}
