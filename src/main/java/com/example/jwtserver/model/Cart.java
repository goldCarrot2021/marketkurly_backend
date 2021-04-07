package com.example.jwtserver.model;


import com.example.jwtserver.dto.CartRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name ="cart")
public class Cart {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long cid;

    @ManyToOne
    @JoinColumn(name ="product_pid")
    private Product produt;

    @ManyToOne
    @JoinColumn(name = "user_uid")
    private User user;

    @Column(nullable = false)
    private int count;

    public Cart(Product product, User user, CartRequestDto cartRequestDto) {
        this.produt = product;
        this.user = user;
        this.count = cartRequestDto.getCount();
    }

    public void update(CartRequestDto cartRequestDto){
        this.count = cartRequestDto.getCount();
    }
}
