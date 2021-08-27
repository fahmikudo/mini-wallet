package io.fahmikudo.wallet.service.impl;

import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.exception.HttpException;
import io.fahmikudo.wallet.model.request.LoginRequest;
import io.fahmikudo.wallet.model.response.AuthResponse;
import io.fahmikudo.wallet.repository.UserRepository;
import io.fahmikudo.wallet.security.jwt.JwtConfig;
import io.fahmikudo.wallet.security.jwt.JwtUtil;
import io.fahmikudo.wallet.service.AuthService;
import io.fahmikudo.wallet.validator.AuthValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final JwtUtil jwtUtil;
    private final JwtConfig jwtConfig;
    private final AuthValidator authValidator;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(JwtUtil jwtUtil, JwtConfig jwtConfig, AuthValidator authValidator, UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.jwtUtil = jwtUtil;
        this.jwtConfig = jwtConfig;
        this.authValidator = authValidator;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthResponse auth(User user) {
        long expAccessToken = getExpRefreshToken(jwtConfig.getAccessTokenExpiration());
        long expRefreshToken = getExpRefreshToken(jwtConfig.getRefreshTokenExpiration());

        String token = jwtUtil.generateToken(user, jwtConfig.accessKey(), expAccessToken);
        String refreshToken = jwtUtil.generateToken(user, jwtConfig.refreshKey(), expRefreshToken);

        return new AuthResponse(token, refreshToken);
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest loginRequest) throws HttpException {
        String validation = authValidator.loginValidator(loginRequest);
        if (validation != null) {
            throw new HttpException(validation, HttpStatus.BAD_REQUEST);
        }
        Optional<User> user = userRepository.findByEmailAndIsDeleted(loginRequest.getEmail(), false);
        String inputPassword = passwordEncoder.encode(loginRequest.getPassword());
        log.info("Input Password : " +  inputPassword);
        if (user.isEmpty()) {
            throw new HttpException("User not found", HttpStatus.NOT_FOUND);
        }
        if (inputPassword.matches(user.get().getPassword())) {
            throw new HttpException("Password does not match", HttpStatus.BAD_REQUEST);
        }
        var authResponse = auth(user.get());
        return authResponse;
    }

    private long getExpRefreshToken(Long day) {
        LocalDate from = LocalDate.now();
        LocalDate to = from.plusDays(day);
        long days = ChronoUnit.DAYS.between(from,to);
        return days * 86400000;
    }

}
