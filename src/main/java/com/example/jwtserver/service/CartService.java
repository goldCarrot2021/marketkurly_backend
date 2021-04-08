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
        System.out.println("장바구니 요청 들어옴");
        System.out.println(uid);
        System.out.println(pid);
        System.out.println(cartRequestDto.getCount());

        /* user 한번 더 확인*/
        User user = userRepository.findById(uid).orElseThrow(
                ()-> new IllegalArgumentException("userError")
        );
        /* 해당 상품이 존재하는지 확인. */
        Product product =productRepository.findById(pid).orElseThrow(
                ()-> new IllegalArgumentException("productError")
        );

        System.out.println(product);
        System.out.println(user);

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
        /* 해당 cart가 존재하는지 확인 */
        try{
            Optional<Cart> cart=cartRepositroy.findById(cid);
        }catch (IllegalArgumentException e){
            return "false";
        }
        cartRepositroy.deleteById(cid);
        return "ture";

    }
}