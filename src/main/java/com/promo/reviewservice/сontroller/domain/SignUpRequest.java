package com.promo.reviewservice.сontroller.domain;

import lombok.Data;

@Data
public class SignUpRequest {
    private String username;
    private String password;
    private String email;
}