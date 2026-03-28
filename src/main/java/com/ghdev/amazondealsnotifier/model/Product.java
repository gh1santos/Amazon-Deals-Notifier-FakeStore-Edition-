package com.ghdev.amazondealsnotifier.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private Double price;

    private Double originalPrice;

    private Double discount;

    private String productUrl;

    private LocalDateTime createdAt;

    public String getImageUrl() {
        return "";
    }
    private double score;
    private String imageUrl;
}
