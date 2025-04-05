package com.chencorp.ufit.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}