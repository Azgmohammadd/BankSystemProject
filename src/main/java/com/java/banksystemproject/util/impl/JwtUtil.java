package com.java.banksystemproject.util.impl;

import io.jsonwebtoken.Claims;
import com.java.banksystemproject.model.BankUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class JwtUtil {
    private static final String secretKey = "9a4f2c8d3b7a1e6f45c8a0b3f267d8b1d4e6f3c8a9d2b5f8e3a9c8b5f6v8a3d9"; // use application.properties

    private static final long jwtExpiration = 86400;

    private static final long refreshExpiration = 86400;

    private static String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);

        return claimsResolver.apply(claims);
    }

    private static Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private static Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String generateToken(BankUser user) {
        return generateToken(new HashMap<>(), user);
    }

    public static String generateToken(Map<String, Object> extraClaims, BankUser user) {
        return buildToken(extraClaims, user, jwtExpiration);
    }

    public static String generateRefreshToken(BankUser user) {
        return buildToken(new HashMap<>(), user, refreshExpiration);
    }

    private static String buildToken(Map<String, Object> extraClaims, BankUser user, long expiration) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ expiration))
                .signWith(getSigninKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public static boolean isTokenValid(String token, BankUser user) {
        final String username = extractUsername(token);

        return (username.equals(user.getUsername())) && !isTokenExpired(token);
    }

    private static boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private static Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
