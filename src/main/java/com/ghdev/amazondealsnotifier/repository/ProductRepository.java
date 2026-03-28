package com.ghdev.amazondealsnotifier.repository;

import com.ghdev.amazondealsnotifier.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    boolean existsByProductUrl(String productUrl);

    Page<Product> findAll(Pageable pageable);

    List<Product> findTop10ByOrderByCreatedAtDesc();

    List<Product> findTop10ByOrderByDiscountDesc();

    List<Product> findTop10ByOrderByScoreDesc();

}
