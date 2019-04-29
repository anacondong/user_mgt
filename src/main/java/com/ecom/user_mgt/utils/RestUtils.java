package com.ecom.user_mgt.utils;

import com.ecom.user_mgt.excpetion.ServiceUnAvailable;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.function.Supplier;

@Slf4j
public class RestUtils {

    public static <T> List<T> safeExecuteForListResponse(Supplier<List<T>> apiGateway) {
        try {
            return apiGateway.get();
        } catch (Exception ex) {
            throw new ServiceUnAvailable("Service unavailable");
        }
    }
}
