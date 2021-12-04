package io.fahmikudo.wallet.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.fahmikudo.wallet.domain.User;
import io.fahmikudo.wallet.exception.HttpException;
import io.fahmikudo.wallet.model.request.OrderTopUpRequest;
import io.fahmikudo.wallet.model.response.OrderTopUpResponse;

public interface OrderServiceTopUpBalance extends OrderRouterService {

    OrderTopUpResponse orderTopUp(User user, OrderTopUpRequest orderTopUpRequest) throws HttpException, JsonProcessingException;

}
