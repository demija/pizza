package ba.gabela.pizza.config.security;

import ba.gabela.pizza.config.JwtTokenUtil;
import ba.gabela.pizza.database.model.Person;
import ba.gabela.pizza.database.repository.PersonRepository;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import static ba.gabela.pizza.config.security.SecurityConstants.EXPIRATION_TIME;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final PersonRepository personRepository;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil, PersonRepository personRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.personRepository = personRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {

        String[] authorizations = decode(req.getHeader("authorization").substring("Basic".length()).trim());

        return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authorizations[0],
                        authorizations[1],
                        new ArrayList<>())
        );
    }

    private String[] decode(String encoded) {
        final byte[] decodedBytes = Base64.decodeBase64(encoded.getBytes());
        final String pair = new String(decodedBytes);
        return pair.split(":", 2);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException {

        String username = ((User) auth.getPrincipal()).getUsername();
        Person person = personRepository.findByUsernameIgnoreCase(username);
        String token = jwtTokenUtil.generateToken(person.getUsername());

        JSONObject authResponse = new JSONObject();
        authResponse.put(SecurityConstants.ACCESS_TOKEN, token);
        authResponse.put(SecurityConstants.TOKEN_TYPE, SecurityConstants.TOKEN_PREFIX);
        authResponse.put(SecurityConstants.EXPIRATION_LABEL, EXPIRATION_TIME);

        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        res.getWriter().write(authResponse.toString());
    }
}
