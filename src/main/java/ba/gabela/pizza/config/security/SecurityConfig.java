package ba.gabela.pizza.config.security;

import ba.gabela.pizza.config.JwtTokenUtil;
import ba.gabela.pizza.database.repository.PersonRepository;
import ba.gabela.pizza.service.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static ba.gabela.pizza.config.security.SecurityConstants.ITEM_URL;
import static ba.gabela.pizza.config.security.SecurityConstants.MENU_URL;
import static ba.gabela.pizza.config.security.SecurityConstants.SIGN_UP_URL;

@EnableWebSecurity
@SuppressWarnings({"AnnotationUseStyle", "DesignForExtension"})
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.secret}")
    private String secret;

    private final PersonDetailsService personDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(PersonDetailsService personDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.personDetailsService = personDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**"
            // other public endpoints of your API may be appended to this array
    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                .and()
                .csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.POST, SIGN_UP_URL).permitAll()
                .antMatchers(HttpMethod.GET, MENU_URL).permitAll()
                .antMatchers(HttpMethod.GET, ITEM_URL).permitAll()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtTokenUtil, personRepository))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), secret))
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }
}
