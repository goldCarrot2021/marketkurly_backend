package com.example.jwtserver.service;

import com.example.jwtserver.auth.PrincipalDetails;
import com.example.jwtserver.dto.CartRequestDto;
import com.example.jwtserver.model.Cart;
import com.example.jwtserver.model.Product;
import com.example.jwtserver.model.User;
import com.example.jwtserver.repository.CartRepositroy;
import com.example.jwtserver.repository.ProductRepository;
import com.example.jwtserver.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    private final CartRepositroy cartRepositroy;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public CartService(CartRepositroy cartRepositroy,UserRepository userRepository,ProductRepository productRepository) {
        this.cartRepositroy = cartRepositroy;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public Cart createCart(CartRequestDto cartRequestDto,Long uid,Long pid){
        User user = userRepository.findById(uid).orElseThrow(
                ()-> new IllegalArgumentException("userError")
        );
        Product product =productRepository.findById(pid).orElseThrow(
                ()-> new IllegalArgumentException("productError")
        );

        Cart cart = new Cart(product,user,cartRequestDto);
        cartRepositroy.save(cart);
        return cart;
    }

    public List<Cart> getCartList(Long uid){
        User user = userRepository.findById(uid).orElseThrow(
                ()-> new IllegalArgumentException("userError")
        );
        return cartRepositroy.findByUser(user);
    }

    @Transactional
    public Cart updateCart(Long cid,CartRequestDto cartRequestDto){
        Cart cart=cartRepositroy.findById(cid).orElseThrow(
                ()-> new IllegalArgumentException("Carterror")
        );
        cart.update(cartRequestDto);
        return cart;
     }

     public String deleteCart(Long cid){
        Optional<Cart> cart=cartRepositroy.findById(cid);

        if(cart.isPresent()){
            cartRepositroy.deleteById(cid);
            return "ture";
        }else{
            return "false";
        }

    }
}