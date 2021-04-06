package com.example.jwtserver.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
public class Cart {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long cid;

    @Column(nullable = false)
    private Long pid;

    @Column(nullable = false)
    private Long uid;

    @Column(nullable = false)
    private int price;

}
