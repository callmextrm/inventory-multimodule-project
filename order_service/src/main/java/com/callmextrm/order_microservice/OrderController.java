package com.callmextrm.order_microservice;

import com.callmextrm.order_microservice.DTO.CreateOrderRequest;
import com.callmextrm.order_microservice.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
    @RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;


    @GetMapping("/hi")
    public String hi(){
        log.info("Saying hi");
        return "hi";
    }
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> create(@RequestBody CreateOrderRequest request) {
        Order saved = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

}
