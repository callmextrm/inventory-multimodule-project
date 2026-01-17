package com.callmextrm.order_microservice.productClient;

import com.callmextrm.order_microservice.exception.ResourceNotFound;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
@Slf4j
@Service
public class ProductClient {
    private final RestTemplate restTemplate;
    private final String baseUrl;
    private final HttpServletRequest request;


    public ProductClient(RestTemplate restTemplate, @Value("${product.service.url}") String baseUrl, HttpServletRequest request) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
        this.request = request;
    }

        public void assertProductExists (Long productId){
        String bearerToken = request.getHeader("Authorization");
            if (bearerToken == null || !bearerToken.startsWith("Bearer ")) {
                throw new RuntimeException("Missing or invalid Authorization header");
            }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",bearerToken);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<String> resp = restTemplate.exchange
                        (baseUrl + "/products/" + productId, HttpMethod.GET, entity, String.class);
        if (!resp.getStatusCode().is2xxSuccessful()){
            log.error("Product not found with product name: {}",productId);
            throw new ResourceNotFound("Product not found"+ productId);}

}}