package org.callmextrm.events;

import java.util.List;

public record OrderCreatedEvent(
        Long orderId,
        String username,
        String roles,
        List<OrderLines> items
) {
}
