package com.ghdev.amazondealsnotifier.service;

import com.ghdev.amazondealsnotifier.client.FakeStoreClient;
import com.ghdev.amazondealsnotifier.config.DealConfig;
import com.ghdev.amazondealsnotifier.config.SchedulerConfig;
import com.ghdev.amazondealsnotifier.dto.DealResponseDTO;
import com.ghdev.amazondealsnotifier.mapper.DealMapper;
import com.ghdev.amazondealsnotifier.mapper.ProductMapper;
import com.ghdev.amazondealsnotifier.model.FakeStoreProductDTO;
import com.ghdev.amazondealsnotifier.model.Product;
import com.ghdev.amazondealsnotifier.repository.ProductRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class DealService {

    private final FakeStoreClient fakeStoreClient;
    private final ProductRepository productRepository;
    private final TelegramService telegramService;
    private final DealConfig dealConfig;
    private final SchedulerConfig schedulerConfig;

    public DealService(
            FakeStoreClient fakeStoreClient,
            ProductRepository productRepository,
            DealConfig dealConfig,
            TelegramService telegramService,
            SchedulerConfig SchedulerConfig) {

        this.fakeStoreClient = fakeStoreClient;
        this.productRepository = productRepository;
        this.dealConfig = dealConfig;
        this.telegramService = telegramService;
        this.schedulerConfig = SchedulerConfig;
    }

    @Scheduled(fixedRateString = "${scheduler.interval}")
    public void checkDeals() {
        fetchDeals();
    }

    public List<Product> fetchDeals() {

        log.info("🔎 Buscando produtos da API FakeStore...");

        List<FakeStoreProductDTO> apiProducts = fakeStoreClient.getProducts();

        log.info("📦 Produtos encontrados: {}", apiProducts.size());

        List<Product> deals = apiProducts.stream()
                .map(ProductMapper::toEntity)

                .peek(product -> log.info(
                        "Produto analisado: {} | Preço: {} | Desconto: {}%",
                        product.getTitle(),
                        product.getPrice(),
                        String.format("%.2f", product.getDiscount())))

                .filter(product -> product.getDiscount() >= dealConfig.getMinDiscount())

                .filter(product -> !productRepository.existsByProductUrl(product.getProductUrl()))

                .sorted(Comparator.comparingDouble(Product::getScore).reversed())

                .toList();

        log.info("🔥 Ofertas válidas encontradas: {}", deals.size());

        if (!deals.isEmpty()) {

            productRepository.saveAll(deals);

            log.info("💾 Ofertas salvas no banco.");

            deals.stream()
                    .limit(5) // evita flood no telegram
                    .forEach(product -> sendTelegramMessage(product));

        } else {
            log.info("⚠ Nenhuma nova oferta encontrada.");
        }

        return deals;
    }

    private void sendTelegramMessage(Product product) {

        String message = "🔥 *NOVA OFERTA*\n\n"
                + product.getTitle() + "\n\n"
                + "💰 Preço: $" + product.getPrice() + "\n"
                + "📉 Desconto: " + String.format("%.2f", product.getDiscount()) + "%\n\n"
                + "🛒 " + product.getProductUrl();

        telegramService.sendPhoto(product.getImageUrl(), message);
    }

    public Page<Product> getDeals(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public List<Product> getLatestDeals() {
        return productRepository.findTop10ByOrderByCreatedAtDesc();
    }

    public List<Product> getBestDeals() {
        return productRepository.findTop10ByOrderByDiscountDesc();
    }

    public List<Product> getTopDeals() {
        return productRepository.findTop10ByOrderByScoreDesc();
    }

    public List<DealResponseDTO> getLatestDealsDTO() {

        return productRepository
                .findTop10ByOrderByCreatedAtDesc()
                .stream()
                .map(DealMapper::toDTO)
                .toList();
    }
}