package com.ecom.user_mgt.controller;

import com.ecom.user_mgt.excpetion.ServiceUnAvailable;
import com.ecom.user_mgt.excpetion.UserNotAvailable;
import com.ecom.user_mgt.model.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class BaseController {

    @ExceptionHandler(UserNotAvailable.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundException(Exception ex) {
        log.error("Exception has Occurred", ex);
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage());
    }

    @ExceptionHandler(ServiceUnAvailable.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    @ResponseBody
    public ErrorResponse handleServiceUnAvailableException(Exception ex) {
        log.error("Error Occurred while communicating with server", ex);
        return new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE.value(), ex.getMessage());
    }
}
