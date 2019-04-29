package com.ecom.user_mgt.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ErrorResponse {

    private Integer status;

    private String message;

}
