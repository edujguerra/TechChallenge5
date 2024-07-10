package br.com.fiap.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class TokenService {

    //@Value("${api.security.token.secret}")
    private String secret = "66e48fcca777ed0975ff8a7f51198db678aea9661298bcd34adace1ecefa2cce";

    public String getSubject(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(Base64.getDecoder().decode(secret));
            return JWT.require(algoritmo)
                    .withIssuer("API Comercio")
                    .build()
                    .verify(tokenJWT)
                    .getSubject();

        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado! " + exception.getMessage());
        }
    }

    public String getClains(String tokenJWT) {
        try {
            var algoritmo = Algorithm.HMAC256(Base64.getDecoder().decode(secret));
            return JWT.require(algoritmo)
                    .withIssuer("API Comercio")
                    .build()
                    .verify(tokenJWT)
                    .getClaims().toString();

        } catch (JWTVerificationException exception) {
            throw new RuntimeException("Token JWT inválido ou expirado! " + exception.getMessage());
        }
    }
}
