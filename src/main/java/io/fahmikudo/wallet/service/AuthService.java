package io.fahmikudo.wallet.service;

import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.model.response.AuthResponse;

public interface AuthService {
    AuthResponse auth(User user);
}
