package com.ghdev.amazondealsnotifier.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "deal")
public class DealConfig {

    private double minDiscount;

    public double getMinDiscount() {
        return minDiscount;
    }

    public void setMinDiscount(double minDiscount) {
        this.minDiscount = minDiscount;
    }
}