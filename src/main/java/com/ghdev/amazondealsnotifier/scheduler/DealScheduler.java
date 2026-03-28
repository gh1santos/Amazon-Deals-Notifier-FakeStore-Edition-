package com.ghdev.amazondealsnotifier.scheduler;

import com.ghdev.amazondealsnotifier.service.DealService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DealScheduler {

    private final DealService dealService;

    public DealScheduler(DealService dealService) {
        this.dealService = dealService;
    }

    @Scheduled(fixedRateString = "${scheduler.interval}")
    public void runDealSearch() {
        dealService.fetchDeals();
    }
}