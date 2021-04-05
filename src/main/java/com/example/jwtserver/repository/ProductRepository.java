package com.example.jwtserver.repository;

import com.example.jwtserver.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    /* 내림 차순 */
    List<Product> findAllByOrderByPriceDesc();

    /* 오름 차순 */
    List<Product> findAllByOrderByPriceAsc();
}