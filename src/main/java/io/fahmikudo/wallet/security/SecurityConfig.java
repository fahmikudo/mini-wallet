package io.fahmikudo.wallet.security;

import io.fahmikudo.wallet.security.jwt.JwtConfig;
import org.apache.catalina.security.SecurityUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtConfig jwtConfig;
    private final SecurityUtils securityUtils;

    @Bean
    public PasswordEncoder encoder() {
        return new Pbkdf2PasswordEncoder();
    }

    public SecurityConfig(JwtConfig jwtConfig, SecurityUtils securityUtils) {
        this.jwtConfig = jwtConfig;
        this.securityUtils = securityUtils;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new TokenVerifier(jwtConfig, securityUtils), BasicAuthenticationFilter.class);

        http
                .exceptionHandling()
                .authenticationEntryPoint(new CustomEntryPoint());
    }

    @Override
    public void configure(WebSecurity web) {
        web.
                ignoring()
                .antMatchers(
                        "/auth/refresh-token",
                        "/auth/login",
                        "/users/register"
                );
    }

}
