package io.fahmikudo.wallet.security;

import com.sun.istack.NotNull;
import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.security.jwt.JwtConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Slf4j
public class TokenVerifier extends OncePerRequestFilter {

    private final JwtConfig jwtConfig;
    private final SecurityUtils securityUtils;

    public TokenVerifier(JwtConfig jwtConfig, SecurityUtils securityUtils) {
        this.jwtConfig = jwtConfig;
        this.securityUtils = securityUtils;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        User userActive;
        try {
            userActive = securityUtils.getUserFromServlet(request);
            if (!userActive.getIsActive()) {
                String authorizationHeader = request.getHeader(jwtConfig.getAuthorizationHeader());
                String token = authorizationHeader.replace(jwtConfig.getTokenPrefix(), "");
                throw new IllegalStateException(String.format("Token: %s cannot be trusted", token));
            } else {
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userActive,
                        null,
                        Collections.emptyList());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            log.error("Error: " + e.getLocalizedMessage());
        }

        filterChain.doFilter(request, response);
    }
}
