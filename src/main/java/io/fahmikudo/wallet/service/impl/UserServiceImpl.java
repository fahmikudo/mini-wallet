package io.fahmikudo.wallet.service.impl;

import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.exception.HttpException;
import io.fahmikudo.wallet.model.request.UserRegistrationRequest;
import io.fahmikudo.wallet.model.response.UserRegistrationResponse;
import io.fahmikudo.wallet.repository.UserRepository;
import io.fahmikudo.wallet.service.AuthService;
import io.fahmikudo.wallet.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AuthService authService;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, AuthService authService, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserRegistrationResponse userRegistration(UserRegistrationRequest req) throws NoSuchAlgorithmException, HttpException {
        var user = User.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .address(req.getAddress())
                .password(passwordEncoder.encode(req.getPassword()))
                .isActive(true)
                .status(User.ACTIVE)
                .build();
        userRepository.save(user);
        var authResponse = authService.auth(user);
        return new UserRegistrationResponse(authResponse.getToken(), authResponse.getRefreshToken());
    }
}