package io.fahmikudo.wallet.service;

import io.fahmikudo.wallet.exception.HttpException;
import io.fahmikudo.wallet.model.request.UserRegistrationRequest;
import io.fahmikudo.wallet.model.response.UserRegistrationResponse;

import java.security.NoSuchAlgorithmException;

public interface UserService {

    UserRegistrationResponse userRegistration(UserRegistrationRequest req) throws HttpException;

}
