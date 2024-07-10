package br.com.fiap.mslogin.service;

import br.com.fiap.mslogin.enums.UserRole;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class JwtService {

    public static final String SECRET = "66e48fcca777ed0975ff8a7f51198db678aea9661298bcd34adace1ecefa2cce";

    public void validateToken(final String token) {
        Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
    }


    public String generateToken(String email, UserRole userRole, Long id) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userRole);

        if (userRole == UserRole.CUSTOMER) {
            claims.put("cartId", UUID.randomUUID().toString());
            claims.put("userId", id);
        }

        return createToken(claims, email);
    }

    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .setIssuer("API Comercio")
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }


    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
