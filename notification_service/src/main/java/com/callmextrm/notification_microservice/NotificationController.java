package com.callmextrm.notification_microservice;


import com.callmextrm.notification_microservice.entity.Notification;
import com.callmextrm.notification_microservice.sse.NotificationSseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/  notifications")
public class NotificationController {
    private final NotificationService notificationService;
    private final NotificationSseService notificationSseService;
    public NotificationController(NotificationService notificationService, NotificationSseService notificationSseService) {
        this.notificationService = notificationService;
        this.notificationSseService = notificationSseService;
    }

    @GetMapping
    public ResponseEntity<List<Notification>> getAllNotifications(){
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @GetMapping("/unread")
    public ResponseEntity<List<Notification>> getUnreadNotifications(){
        return ResponseEntity.ok(notificationService.getUnreadNotifications());
    }


    @GetMapping("/read")
    public ResponseEntity<List<Notification>> getReadNotifications(){
        return ResponseEntity.ok(notificationService.getReadNotifications());
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Notification> readNotification(@PathVariable Long id){
        Notification notification = notificationService.readNotification(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(notification);
    }


    @GetMapping(value = "/stream", produces = "text/event-stream")
    public SseEmitter stream(){
        return notificationSseService.subscribe();
    }
}
