package com.ecom.user_mgt.gateways;

import com.ecom.user_mgt.model.dto.Orders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "OrderClient", url = "${services.order-mgt.url}")
public interface OrderClient {

    @GetMapping("/api/orders/user/{userId}")
    List<Orders> getOrdersByUserId(@PathVariable("userId") Long userId);

}
