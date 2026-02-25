package com.thread.app.jwtUtil;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.inject.Singleton;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Singleton
public class JwtUtil {
    private static final String SECRET_KEY = "a-string-secret-at-least-256-bits-long";
    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));

    public String generate(String username)
    {
        return Jwts.builder().subject(username)
                .signWith(KEY)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                .compact();
    }
    public void validate(String token){
        Jwts.parser()
                .verifyWith(KEY)
                .build().parseSignedClaims(token)
                .getPayload();
    }
    public String validateAndExtract(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }
}
