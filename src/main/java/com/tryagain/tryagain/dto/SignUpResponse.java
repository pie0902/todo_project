package com.tryagain.tryagain.dto;

import lombok.Getter;

@Getter
public class SignUpResponse {
    private Long userId;
    private String token;

    public SignUpResponse(Long userId, String token) {
        this.userId = userId;
        this.token = token;
    }

}