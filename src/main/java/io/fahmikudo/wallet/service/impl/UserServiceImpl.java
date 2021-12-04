package io.fahmikudo.wallet.service.impl;

import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.exception.HttpException;
import io.fahmikudo.wallet.model.request.UserRegistrationRequest;
import io.fahmikudo.wallet.model.response.AuthResponse;
import io.fahmikudo.wallet.model.response.UserRegistrationResponse;
import io.fahmikudo.wallet.repository.UserRepository;
import io.fahmikudo.wallet.service.AuthService;
import io.fahmikudo.wallet.service.UserService;
import io.fahmikudo.wallet.validator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final AuthService authService;

    private final PasswordEncoder passwordEncoder;

    private final UserValidator userValidator;

    public UserServiceImpl(UserRepository userRepository, AuthService authService, PasswordEncoder passwordEncoder, UserValidator userValidator){
        this.userRepository = userRepository;
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
        this.userValidator = userValidator;
    }

    @Override
    @Transactional
    public UserRegistrationResponse userRegistration(UserRegistrationRequest req) throws HttpException {
        String validation = userValidator.userRegistrationValidator(req);
        if (validation != null) {
            throw new HttpException(validation, HttpStatus.BAD_REQUEST);
        }
        User user = User.builder()
                .firstName(req.getFirstName())
                .lastName(req.getLastName())
                .email(req.getEmail())
                .phone(req.getPhone())
                .address(req.getAddress())
                .password(passwordEncoder.encode(req.getPassword()))
                .isActive(true)
                .build();
        userRepository.save(user);
        AuthResponse authResponse = authService.auth(user);
        return new UserRegistrationResponse(authResponse.getToken(), authResponse.getRefreshToken());
    }
}
