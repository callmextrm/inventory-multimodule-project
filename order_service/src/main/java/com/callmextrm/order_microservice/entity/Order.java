package com.callmextrm.order_microservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String roles;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    private Instant createdAt;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @Builder.Default
    @JsonManagedReference
    private List<OrderItem> items = new ArrayList<>();



    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
    public void removeItem(OrderItem item) {
        items.remove(item);
        item.setOrder(null);
    }


}
