package com.gudratli.authservice.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService
{
    @SuppressWarnings("SpellCheckingInspection")
    private static final String secret = "00A5B9D47537B007E7EEF3734ED37B960826FADCE45C66900B6B5A37EC0E7231B3";

    public String extractUsername (String token)
    {
        return extractClaim(token, Claims::getSubject);
    }

    public String generateAccessToken (UserDetails userDetails)
    {
        return generateAccessToken(new HashMap<>(), userDetails);
    }

    public String generateAccessToken (Map<String, Object> extraClaims, UserDetails userDetails)
    {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 4541254))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Boolean isAccessTokenValid (String token, UserDetails userDetails)
    {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    //helper functions

    private boolean isTokenExpired (String token)
    {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration (String token)
    {
        return extractClaim(token, Claims::getExpiration);
    }

    private <T> T extractClaim (String token, Function<Claims, T> claimsResolver)
    {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims (String token)
    {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey ()
    {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
