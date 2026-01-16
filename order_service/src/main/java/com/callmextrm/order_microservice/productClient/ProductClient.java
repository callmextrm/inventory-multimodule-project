package com.callmextrm.order_microservice.productClient;

import com.callmextrm.order_microservice.exception.ResourceNotFound;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
@Slf4j
@Service
public class ProductClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;


    public ProductClient(RestTemplate restTemplate, @Value("${product.service.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

        public void assertProductExists (Long productId){
            try{
                restTemplate.getForEntity(baseUrl + "/products/"+productId, String.class);}
            catch (HttpClientErrorException.NotFound e){
                log.error("Product not found with product name: {}",productId);
                throw new ResourceNotFound("Product not found"+ productId);
            }
}}