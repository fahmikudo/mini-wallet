package io.fahmikudo.wallet.service;

import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.exception.HttpException;
import io.fahmikudo.wallet.model.request.LoginRequest;
import io.fahmikudo.wallet.model.response.AuthResponse;

public interface AuthService {
    AuthResponse auth(User user);
    AuthResponse login(LoginRequest loginRequest) throws HttpException;
}
