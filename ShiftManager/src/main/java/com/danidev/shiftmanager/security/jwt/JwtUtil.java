package com.danidev.shiftmanager.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // 🔐 clave segura (mínimo 256 bits)
    private static final String SECRET =
            "my-super-secret-key-my-super-secret-key-123456";

    private static final long EXPIRATION = 1000 * 60 * 60; // 1h

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(getSigningKey())
                .compact();
    }

    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            System.out.println("JWT VALID");
            return true;
        } catch (Exception e) {
            System.out.println("JWT INVALID: " + e.getMessage());
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey()) // Reemplaza setSigningKey
                .build()
                .parseSignedClaims(token)    // Reemplaza parseClaimsJws
                .getPayload();               // Reemplaza getBody
    }
}
