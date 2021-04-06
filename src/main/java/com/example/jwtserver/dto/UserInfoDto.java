package com.example.jwtserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDto {
    private Long uid;
    private String name;
    private String address;

    public UserInfoDto(Long uid, String name,String address) {
        this.uid = uid;
        this.name = name;
        this.address = address;
    }
}
