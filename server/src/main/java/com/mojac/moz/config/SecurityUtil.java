package com.mojac.moz.config;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class SecurityUtil {

    @Value("${app.auth.token-secret}") private String secret;
    public final String ACCESS_TOKEN_COOKIE_KEY = "MOZ_TOKEN";

    public String createJwt(String subject, Date expiration) {
        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secret);

        jwtBuilder.setExpiration(expiration);

        return jwtBuilder.compact();
    }

    public String extractSubject(String jwt) {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }

    public ResponseCookie generateAccessTokenCookie(Long memberId) {
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 60 * 24);
        String jwt = createJwt(memberId.toString(), expiration);
        return ResponseCookie.from(ACCESS_TOKEN_COOKIE_KEY, jwt)
                .httpOnly(true)
                .path("/")
                .build();
    }
}
