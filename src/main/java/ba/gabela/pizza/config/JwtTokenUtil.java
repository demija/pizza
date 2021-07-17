package ba.gabela.pizza.config;

import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import static ba.gabela.pizza.config.security.SecurityConstants.EXPIRATION_TIME;
import static ba.gabela.pizza.config.security.SecurityConstants.ISSUER;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
public final class JwtTokenUtil {
    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(String id) {
        return JWT.create()
                .withIssuer(ISSUER)
                .withSubject(id)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(secret.getBytes()));
    }
}
