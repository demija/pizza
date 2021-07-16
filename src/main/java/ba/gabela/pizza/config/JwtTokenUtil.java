package ba.gabela.pizza.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import java.util.Date;
import static ba.gabela.pizza.config.security.SecurityConstants.EXPIRATION_TIME;
import static ba.gabela.pizza.config.security.SecurityConstants.ISSUER;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

@Service
public class JwtTokenUtil {
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

    public String getSubjectFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(secret.getBytes()))
                .build()
                .verify(token.replace("Bearer ", ""))
                .getSubject();
    }

    public String getAuthorization() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest().getHeader("authorization");
    }
}
