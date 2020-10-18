package com.prageeth.service.impl;

import com.prageeth.entity.UserInfo;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTokenService implements Serializable {

    private static final long serialVersionUID = 5701001619083465041L;

    public static final long JWT_TOKEN_VALIDITY = 300000; // 5 minutes

    private static final String SECRET = "secret";

    public String getUserNameFromToken(String token) {
        final Claims claims = getClaims(token);
        return claims.getSubject();
    }

    public String generateToken(UserInfo userInfo) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userInfo.getUserName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    public Boolean validateToken(String token, UserInfo userInfo) {
        final String username = getUserNameFromToken(token);
        return (username.equals(userInfo.getUserName()) && !isTokenExpired(token));
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getClaims(token).getExpiration();
        return expiration.before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody();
    }

}
