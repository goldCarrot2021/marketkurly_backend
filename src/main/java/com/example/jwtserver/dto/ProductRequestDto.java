package com.example.jwtserver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductRequestDto {
    private String title;
    private int price;
    private String img;
    private String subtext;
}
