package com.callmextrm.notification_microservice;

import com.callmextrm.notification_microservice.entity.Notification;
import com.callmextrm.notification_microservice.exception.ResourceNotFound;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class NotificationService {
    private final NotificationRepository notificationDao;
    public NotificationService(NotificationRepository notificationDao) {
        this.notificationDao = notificationDao;
    }

    //Get all notifications
    public List<Notification> getAllNotifications(){
        return notificationDao.findAllByOrderByCreatedAtDesc();
    }

    //Get unread notifications
    public List<Notification> getUnreadNotifications(){
        return notificationDao.findByReadFalseOrderByCreatedAtDesc();
    }

    //Get read notifications
    public List<Notification> getReadNotifications(){
        return notificationDao.findByReadTrueOrderByCreatedAtDesc();
    }

    //Read notification
    public Notification readNotification(Long id){
        Notification notification = notificationDao.findById(id).orElseThrow(
                ()-> new ResourceNotFound("Notification not found"));
        notification.setRead(true);
        notification.setReadAt(Instant.now());
        return notificationDao.save(notification);
    }
}
