package com.callmextrm.order_microservice;

import com.callmextrm.order_microservice.DTO.CreateOrderRequest;
import com.callmextrm.order_microservice.DTO.OrderItemRequest;
import com.callmextrm.order_microservice.entity.Order;
import com.callmextrm.order_microservice.entity.OrderItem;
import com.callmextrm.order_microservice.entity.Status;
import com.callmextrm.order_microservice.kafka.OrderEventProducer;
import com.callmextrm.order_microservice.productClient.ProductClient;
import com.callmextrm.order_microservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.callmextrm.events.OrderCreatedEvent;
import org.callmextrm.events.OrderLines;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderDao;
    private final OrderEventProducer producer;
    private final ProductClient productClient;


    public Order createOrder(CreateOrderRequest request) {
        // LATER: replace with real values from JWT / SecurityContext

        Long userId = 1L;
        String username = "ahmed";
        String roles = "ADMIN";
        log.info("Creating order for user={}, with roles={}", username, roles);


        // 1) Create order
        Order order = Order.builder()
                .userId(userId)
                .username(username)
                .roles(roles).createdAt(Instant.now())
                .status(Status.CREATED).build();


        // 2) Add items (using helper method so FK is set)

        for (OrderItemRequest itemRequest : request.items()) {
            productClient.assertProductExists(itemRequest.productId());
        }
        // 3) Save once (cascade saves items)

        Order saved = orderDao.save(order);
        log.info("Order saved with id={}", saved.getId());

        // 4) Publish event to Kafka
        List<OrderLines> lines = saved.getItems().stream()
                .map(i -> new OrderLines(i.getProductId(), i.getProductName(), i.getQuantity()))
                .toList();

        producer.publishOrderCreated(new OrderCreatedEvent(saved.getId(), saved.getUserId(), saved.getUsername(), saved.getRoles(), lines));
        log.info("OrderCreatedEvent sent to Kafka for orderId={}", saved.getId());
        return saved;
    }

}
