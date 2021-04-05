package com.example.jwtserver.service;

import com.example.jwtserver.model.Product;
import com.example.jwtserver.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    /* sort에 따라서 productList 들고 오기 */
    public List<Product> getProductList(String sort) {
        List<Product> prductList = null;

        if (sort.equals("asc")) {
            prductList = productRepository.findAllByOrderByPriceAsc();
        } else if (sort.equals("desc")) {
            prductList = productRepository.findAllByOrderByPriceDesc();
        }

        return prductList;
    }

}
