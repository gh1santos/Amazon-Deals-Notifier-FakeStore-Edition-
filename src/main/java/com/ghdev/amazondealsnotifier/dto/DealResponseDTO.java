package com.ghdev.amazondealsnotifier.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DealResponseDTO {

    private String title;

    private double price;

    private double originalPrice;

    private double discount;

    private String imageUrl;

    private String productUrl;
}
