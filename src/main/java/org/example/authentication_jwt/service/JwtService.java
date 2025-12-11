package org.example.authentication_jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {
    private final Key key;
    private final long expirationMs;
    public JwtService(@Value("${app.jwt.secret}") String secret, @Value("${app.jwt.expiration-ms}") long expirationMs) {
        this.expirationMs = expirationMs;
        this.key= Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username, Map<String, Object> claims) {
        Date now = new Date();
        Date exp =  new Date(now.getTime() + expirationMs);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(key)
                .setSubject(username)
                .compact();
    }

    private Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public String extractUserName(String token) {
        return parse(token).getBody().getSubject();
    }

    private boolean isExpired(String token) {
        return parse(token).getBody().getExpiration().before(new Date());
    }

    public boolean isValid(String token, String username) {
        return extractUserName(token).equals(username) && ! isExpired(token);
    }
}
