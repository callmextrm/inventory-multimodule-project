package com.callmextrm.notification_microservice.entity;


import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table (name = "notification")
public class Notification {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false, unique = true)
    private Long orderId;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String roles;

    private Instant createdAt;

    @Column(name = "is_read", nullable = false)
    private Boolean read = false;

    private Instant readAt;






}
