package com.phoenix.blog.service;

import com.phoenix.blog.model.JwtPair;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Service
public class JwtService {

    private final SecretKey key;

    public JwtService(
            @Value("${jwt.secret}") String base64) {
        byte[] bytes = java.util.Base64.getDecoder().decode(base64);
        this.key = new SecretKeySpec(bytes, SignatureAlgorithm.HS512.getJcaName());
    }

    public JwtPair issueTokens(Authentication auth) {
        return new JwtPair(
                buildToken(auth.getName(), 15 * 60),
                buildToken(auth.getName(), 7 * 24 * 3600));
    }

    private String buildToken(String sub, long ttl) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(sub)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(ttl)))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}
