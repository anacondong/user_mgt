package com.ecom.user_mgt.utils;

import com.ecom.user_mgt.excpetion.ServiceUnAvailable;
import com.ecom.user_mgt.model.dto.Orders;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RestUtilsTest {

    public static final List<Orders> ORDERS = Collections.singletonList(new Orders(1L, 1L, 1L, "Order 1", LocalDateTime.now()));

    @Test
    public void safeExecuteForListResponse() {
        Supplier<List<Orders>> supplier = () -> ORDERS;
        List<Orders> orders = RestUtils.safeExecuteForListResponse(supplier);
        assertEquals(1, orders.size());
    }

    @Test
    public void showThrowServiceUnAvailableException() {
        assertThrows(ServiceUnAvailable.class, () -> RestUtils.safeExecuteForListResponse(() -> {
            throw new RuntimeException("Exception");
        }));
    }
}
