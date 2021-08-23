package io.fahmikudo.wallet.service.impl;

import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.model.response.AuthResponse;
import io.fahmikudo.wallet.security.jwt.JwtConfig;
import io.fahmikudo.wallet.security.jwt.JwtUtil;
import io.fahmikudo.wallet.service.AuthService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final JwtConfig jwtConfig;

    public AuthServiceImpl(JwtUtil jwtUtil, JwtConfig jwtConfig){
        this.jwtUtil = jwtUtil;
        this.jwtConfig = jwtConfig;
    }

    @Override
    public AuthResponse auth(User user) {
        long expAccessToken = getExpRefreshToken(jwtConfig.getAccessTokenExpiration());
        long expRefreshToken = getExpRefreshToken(jwtConfig.getRefreshTokenExpiration());

        String token = jwtUtil.generateToken(user, jwtConfig.accessKey(), expAccessToken);
        String refreshToken = jwtUtil.generateToken(user, jwtConfig.refreshKey(), expRefreshToken);

        return new AuthResponse(token, refreshToken);
    }

    private long getExpRefreshToken(Long day) {
        LocalDate from = LocalDate.now();
        LocalDate to = from.plusDays(day);
        long days = ChronoUnit.DAYS.between(from,to);
        return days * 86400000;
    }

}
