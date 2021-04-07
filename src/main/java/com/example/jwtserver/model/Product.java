package com.example.jwtserver.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="product")
public class Product {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long pid;

    @Column(updatable = false)
    private String title;

    @Column(updatable = false)
    private int price;

    @Column(updatable = false)
    private String subtext;

    @Column(updatable = false)
    private String img;

}
