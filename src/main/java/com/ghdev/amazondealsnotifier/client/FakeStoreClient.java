package com.ghdev.amazondealsnotifier.client;

import com.ghdev.amazondealsnotifier.model.FakeStoreProductDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.List;

@Component
public class FakeStoreClient {

    private final RestTemplate restTemplate = new RestTemplate();

    private static final String API_URL = "https://fakestoreapi.com/products";

    @Cacheable("products")
    public List<FakeStoreProductDTO> getProducts() {

        ResponseEntity<List<FakeStoreProductDTO>> response =
                restTemplate.exchange(
                        API_URL,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<FakeStoreProductDTO>>() {}
                );

        return response.getBody();
    }
}
