package com.wahak.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Date;

/**
 * @author krishna.meena
 */

@Component
public class RiderJwtUtils {

    private static final String SECRET_KEY = "rider-secret-key-must-be-32-characters-long";
    private static final long EXPIRATION_TIME = 1000 * 60 * 120; // 2 hours

    private static final SecretKey key;
    static {
        try {
            key = Keys.hmacShaKeyFor(generate256BitKey("krishna"));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] generate256BitKey(String value) throws NoSuchAlgorithmException {
        MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
        byte[] key = sha256.digest(value.getBytes(StandardCharsets.UTF_8));
        return Arrays.copyOf(key, 32); // Use only the first 256 bits (32 bytes)
    }

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String generateToken(String riderId) {
        return Jwts.builder()
                .subject(riderId)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String getRiderId(String token) {
        return extractClaims(token).getSubject();
    }


}
