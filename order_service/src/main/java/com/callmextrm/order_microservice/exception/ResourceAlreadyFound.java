package com.callmextrm.order_microservice.exception;

public class ResourceAlreadyFound extends RuntimeException {
    public ResourceAlreadyFound(String message) {
        super(message);
    }

}
