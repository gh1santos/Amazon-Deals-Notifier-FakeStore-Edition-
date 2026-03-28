package com.ghdev.amazondealsnotifier.controller;

import com.ghdev.amazondealsnotifier.dto.DealResponseDTO;
import com.ghdev.amazondealsnotifier.model.Product;
import com.ghdev.amazondealsnotifier.service.DealService;

import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

@RestController
@RequestMapping("/deals")
@Tag(name = "Deals", description = "API de monitoramento de ofertas")
public class DealController {

    private final DealService dealService;

    public DealController(DealService dealService) {
        this.dealService = dealService;
    }

    @GetMapping
    @Operation(summary = "Listar ofertas paginadas")
    public List<Product> getDeals(Pageable pageable) {
        return dealService.getDeals(pageable).getContent();
    }

    @GetMapping("/latest")
    @Operation(summary = "Listar últimas ofertas encontradas")
    public List<DealResponseDTO> getLatestDeals() {
        return dealService.getLatestDealsDTO();
    }

    @GetMapping("/top")
    @Operation(summary = "Listar melhores ofertas com base no score")
    public List<Product> getTopDeals() {
        return dealService.getTopDeals();
    }

    @GetMapping("/error-test")
    @Operation(summary = "Endpoint para testar tratamento de erro")
    public String errorTest() {
        throw new RuntimeException("Erro de teste");
    }

    @PostMapping("/fetch")
    @Operation(summary = "Forçar busca de novas ofertas na API externa")
    public List<Product> fetchDeals() {
        return dealService.fetchDeals();
    }
}

