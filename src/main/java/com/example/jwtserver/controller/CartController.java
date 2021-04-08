package com.example.jwtserver.controller;

import com.example.jwtserver.dto.CartRequestDto;
import com.example.jwtserver.model.Cart;
import com.example.jwtserver.service.CartService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping(value = {"/api/v1/cart/{uid}/{pid}"})
    public String createCart(@PathVariable Long uid,
                             @PathVariable Long pid,
                             @RequestBody CartRequestDto cartRequestDto){
        System.out.println("장바구니 요청 controller");
        Cart cart = cartService.createCart(cartRequestDto,uid,pid);
        return "success";
    }

    @GetMapping("/api/v1/cart/{uid}")
    public List<Cart> getCartList(@PathVariable Long uid){
        System.out.println("cart list check");
        return cartService.getCartList(uid);
    }

    @PutMapping("/api/v1/cart/{cid}")
    public Cart updateCart(@PathVariable Long cid, @RequestBody CartRequestDto requestDto){
        System.out.println(requestDto.getCount());
        return cartService.updateCart(cid,requestDto);
    }

    @DeleteMapping("/api/v1/cart/{cid}")
    public String deleteCart(@PathVariable Long cid){
        return cartService.deleteCart(cid);
    }

}
