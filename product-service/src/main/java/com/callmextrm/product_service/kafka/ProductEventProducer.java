package com.callmextrm.product_service.kafka;

import org.callmextrm.events.ProductCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProductEventProducer {
    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;
    private static final Logger log = LoggerFactory.getLogger(ProductEventProducer.class);
    public ProductEventProducer(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishProductCreated(ProductCreatedEvent event) {
        kafkaTemplate.send("product_created", event.productId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Kafka publish failed" + ex.getMessage());
                    } else {
                        log.info("Published product_created for product_id = " + event.productId());
                    }


                });
    }


}
