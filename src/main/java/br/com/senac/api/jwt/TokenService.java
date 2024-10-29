package br.com.senac.api.jwt;

import br.com.senac.api.entitys.Usuarios;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${spring.security.secret}")
    private String secret;

    @Value("${spring.security.expiration_time}")
    private Long expirarionTime;

    private String emissor = "exemplo-logback-api";

    public String gerarToken(Usuarios usuario){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String token = JWT.create()
                    .withIssuer(emissor)
                    .withSubject(usuario.getLogin())
                    .withExpiresAt(this.gerarDataExpiracao())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException e){
            e.printStackTrace();
            throw e;
        }
    }

    public String validarToken(String token){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        try {
            return JWT.require(algorithm)
                    .withIssuer(emissor)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (Exception e){
            return null;
        }
    }

    private Instant gerarDataExpiracao(){
        return LocalDateTime.now()
                .plusMinutes(expirarionTime)
                .toInstant(ZoneOffset.of("-03:00"));
    }
}
