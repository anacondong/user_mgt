package com.ecom.user_mgt.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class Orders {

    private Long id;

    private Long userId;

    private Long itemId;

    private String orderDesc;

    private LocalDateTime orderDate;

}
