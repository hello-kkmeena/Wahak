package com.wahak.utils.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

/**
 * @author krishna.meena
 */
@Component
public class JwtUtil {

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

    public static String generateToken(String subject,String id,Date expiredDate) {
        return Jwts.builder()
                .subject(subject)
                .setId(StringUtils.isEmpty(id) ? "Wahak" : id)
                .issuedAt(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()))
                .expiration(expiredDate) // 1 hour expiration
                .signWith(key)
                .compact();
    }


    public static Claims parseToken(String token) {
        Jwt<?, ?> jwt = Jwts.parser()
                .verifyWith(key)
                .build()
                .parse(token);

        if (jwt.getPayload() instanceof Claims) {
            Claims claims = (Claims) jwt.getPayload();
            return claims;
        } else {
            System.out.println("Invalid token payload");
        }

        return null;
    }
}
