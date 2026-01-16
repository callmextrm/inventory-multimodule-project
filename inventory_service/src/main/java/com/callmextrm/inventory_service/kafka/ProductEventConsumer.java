package com.callmextrm.inventory_service.kafka;

import com.callmextrm.inventory_service.Inventory;
import com.callmextrm.inventory_service.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.callmextrm.events.ProductCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(ProductEventConsumer.class);
    private final InventoryRepository inventoryDao;


    @KafkaListener(topics = "product_created", groupId = "inventory_service")
    public void onProductCreated(ProductCreatedEvent event){
        log.info("Received ProductCreatedEvent: ProductId={}, quantity={}",event.productId(),event.initialQuantity());
        if (inventoryDao.findByProductId(event.productId()).isPresent()) {
            log.warn("Inventory already exists for productId={}",event.productId());
            return;}
        Inventory inventory = new Inventory();
        inventory.setProductId(event.productId());
        inventory.setQuantity(event.initialQuantity() == null ? 0 : event.initialQuantity());
        inventoryDao.save(inventory);
        log.info("Inventory created for productId={}, with the quantity={}",event.productId(),event.initialQuantity());
    }





}
