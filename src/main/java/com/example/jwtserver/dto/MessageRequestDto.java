package com.example.jwtserver.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MessageRequestDto {
    private String message;

    public MessageRequestDto(String message) {
        this.message = message;
    }
}

