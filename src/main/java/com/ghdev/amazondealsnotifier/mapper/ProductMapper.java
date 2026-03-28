package com.ghdev.amazondealsnotifier.mapper;

import com.ghdev.amazondealsnotifier.model.FakeStoreProductDTO;
import com.ghdev.amazondealsnotifier.model.Product;

import java.time.LocalDateTime;

public class ProductMapper {

    public static Product toEntity(FakeStoreProductDTO dto) {

        double price = dto.getPrice();

        double originalPrice = price * (1.1 + Math.random() * 0.4);

        double discount = ((originalPrice - price) / originalPrice) * 100;

        double rating = dto.getRating() != null ? dto.getRating().getRate() : 0;
        int reviews = dto.getRating() != null ? dto.getRating().getCount() : 0;

        double score =
                (discount * 0.5) +
                        ((100 / price) * 0.2) +
                        (rating * 5) +
                        Math.log(reviews + 1);

        return Product.builder()
                .title(dto.getTitle())
                .price(price)
                .originalPrice(originalPrice)
                .discount(discount)
                .score(score)
                .imageUrl(dto.getImage())
                .productUrl("https://fakestoreapi.com/products/" + dto.getId())
                .createdAt(LocalDateTime.now())
                .build();
    }
}