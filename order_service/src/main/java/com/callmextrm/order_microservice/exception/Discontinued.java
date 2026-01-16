package com.callmextrm.order_microservice.exception;

public class Discontinued extends RuntimeException {
    public Discontinued(String message) {
        super(message);
    }

}