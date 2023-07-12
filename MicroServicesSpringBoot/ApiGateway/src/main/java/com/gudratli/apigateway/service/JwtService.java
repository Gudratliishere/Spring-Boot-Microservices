package com.gudratli.apigateway.service;

import org.springframework.stereotype.Service;

@Service
public class JwtService
{
    @SuppressWarnings("SpellCheckingInspection")
    private static final String secret = "00A5B9D47537B007E7EEF3734ED37B960826FADCE45C66900B6B5A37EC0E7231B3";

    public Boolean isAccessTokenValid (String token)
    {
        return token.equals("valid");
    }
}
