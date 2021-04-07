package com.example.jwtserver.repository;

import com.example.jwtserver.model.Cart;
import com.example.jwtserver.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepositroy extends JpaRepository<Cart,Long> {
    List<Cart> findByUser(User user);

}
