package br.pucpr.AlertCity.security;

import lombok.Data;

@Data
public class AuthResponse {
    private String email;
    private String token;
}