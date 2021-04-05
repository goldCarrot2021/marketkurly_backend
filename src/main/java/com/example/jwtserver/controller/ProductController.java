package com.example.jwtserver.controller;

import com.example.jwtserver.model.Product;
import com.example.jwtserver.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@ResponseBody
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /* 프로덕트 리스트 */
    @GetMapping("/api/v1/products/{sort}")
    public List<Product> getPorductList(@PathVariable String sort){
        return productService.getProductList(sort);
    }
}
