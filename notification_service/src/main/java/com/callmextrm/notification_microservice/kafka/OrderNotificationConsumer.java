package com.callmextrm.notification_microservice.kafka;

import com.callmextrm.notification_microservice.NotificationRepository;
import com.callmextrm.notification_microservice.entity.Notification;
import com.callmextrm.notification_microservice.entity.Type;
import com.callmextrm.notification_microservice.sse.NotificationSseService;
import lombok.extern.slf4j.Slf4j;
import org.callmextrm.events.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Slf4j
@Service
public class OrderNotificationConsumer {

    private final NotificationRepository notificationDao;
    private final NotificationSseService notificationSseService;

    public OrderNotificationConsumer(NotificationRepository notificationDao, NotificationSseService notificationSseService) {
        this.notificationDao = notificationDao;
        this.notificationSseService = notificationSseService;
    }

    @KafkaListener(topics = "order_created", groupId = "notification_service")
    public void onOrderCreated(OrderCreatedEvent event){
        log.info("ADMIN NOTIFICATION: New order {} created by {} roles={}",
                event.orderId(), event.username(), event.roles());

        if (notificationDao.findByOrderId(event.orderId()).isPresent()){
            log.warn("Order already exists with the orderId:{}",event.orderId());
            return;
        }
        Notification notification = new Notification();
        notification.setType(Type.ORDER_CREATED);
        notification.setMessage
                ("Order number "+event.orderId()+" created by: "+ event.username()+ " ("+ event.roles()+")");
        notification.setOrderId(event.orderId());
        notification.setUsername(event.username());
        notification.setRoles(event.roles());
        notification.setCreatedAt(Instant.now());
        Notification saved = notificationDao.save(notification);
        notificationSseService.push(saved);
        log.info("Notification saved + pushed to SSE");

    }



}
