package com.ghdev.amazondealsnotifier.mapper;

import com.ghdev.amazondealsnotifier.dto.DealResponseDTO;
import com.ghdev.amazondealsnotifier.model.Product;

public class DealMapper {

    public static DealResponseDTO toDTO(Product product) {

        return DealResponseDTO.builder()
                .title(product.getTitle())
                .price(product.getPrice())
                .originalPrice(product.getOriginalPrice())
                .discount(product.getDiscount())
                .imageUrl(product.getImageUrl())
                .productUrl(product.getProductUrl())
                .build();
    }
}
